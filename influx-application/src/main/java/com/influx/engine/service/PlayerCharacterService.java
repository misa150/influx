package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.database.repository.PlayerCharacterRepository;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.dto.playercharacter.PlayerCharacterDTO;
import com.influx.engine.exceptions.PlayerCharacterException;
import com.influx.engine.service.logs.LogsService;
import com.influx.engine.util.mapper.PlayerCharacterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.influx.engine.util.literals.basevalues.BaseValuesLiterals.*;
import static com.influx.engine.util.literals.logs.ErrorLiterals.ADD_PLAYER_EXISTING_ERROR;
import static com.influx.engine.util.literals.logs.ErrorLiterals.UPDATE_PLAYER_NOT_EXISTING_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerCharacterService {

    private final PlayerCharacterRepository playerCharacterRepository;
    private final LogsService logsService;
    private final BattleAttributeService battleAttributeService;

    //TODO: IF PRESENT OR ELSE
    public PlayerCharacterDTO saveNewPlayerCharacter(AddPlayerCharacterDTO addNewPlayerCharacter) {
        if (playerCharacterRepository.findByPlayerName(addNewPlayerCharacter.getPlayerName()).isPresent()) {
            var errorMessage = String.format(ADD_PLAYER_EXISTING_ERROR, addNewPlayerCharacter.getPlayerName());
            saveErrorLog(errorMessage);
            throw new PlayerCharacterException(errorMessage);
        } else {
            var savedPlayer = playerCharacterRepository.save(initializeNewPlayerCharacter(addNewPlayerCharacter));
            return mapPlayerCharacter(savedPlayer);
        }
    }

    //TODO: IF PRESENT OR ELSE
    public Optional<PlayerCharacterDTO> findPlayerCharacterByPlayerName(String playerCharacterName) {
        var playerCharacter = playerCharacterRepository.findByPlayerName(playerCharacterName).orElse(null);
        return playerCharacter != null ?  Optional.of(mapPlayerCharacter(playerCharacter)) :  Optional.empty();
    }

    //TODO: PAGEABLE
    public List<PlayerCharacterDTO> findAllPlayerCharacters() {
        return playerCharacterRepository.findAll().stream()
                .map(this::mapPlayerCharacter)
                .toList();
    }

    //TODO: IF PRESENT OR ELSE:: IMPROVE, RETURN ERROR IF CHARACTER IS NOT EXISTING?
    //TODO: CREATE UNIT TEST
    public void deletePlayerCharacterByName(String playerCharacterName) {
        playerCharacterRepository.findByPlayerName(playerCharacterName)
                .ifPresent(playerCharacterRepository::delete);
    }

    //TODO: IF PRESENT OR ELSE
    public PlayerCharacterDTO updatePlayerCharacter(
            AddPlayerCharacterDTO addNewPlayerCharacter, String playerCharacterName) {
        var playerCharacterFromDb = playerCharacterRepository.findByPlayerName(playerCharacterName);
        if (playerCharacterFromDb.isPresent()) {
            var playerCharacter = playerCharacterFromDb.get();
            updatePlayerCharacter(playerCharacter, addNewPlayerCharacter);
            battleAttributeService.updateBattleAttributes(playerCharacter, addNewPlayerCharacter);
            return mapPlayerCharacter(playerCharacterRepository.save(playerCharacter));
        } else {
            var errorMessage = String.format(UPDATE_PLAYER_NOT_EXISTING_ERROR, addNewPlayerCharacter.getPlayerName());
            saveErrorLog(errorMessage);
            throw new PlayerCharacterException(errorMessage);
        }
    }

    private PlayerCharacterDTO mapPlayerCharacter(PlayerCharacter playerCharacter) {
        return PlayerCharacterMapper.map(playerCharacter);
    }

    private void updatePlayerCharacter(PlayerCharacter existingPlayer, AddPlayerCharacterDTO updatePlayer) {
        existingPlayer.setPlayerDisplayName(updatePlayer.getPlayerDisplayName());
        existingPlayer.setLastUpdatedDate(LocalDateTime.now());
    }

    //TODO: SET HP and Other values to be from AddPlayerCharacterDTO
    private PlayerCharacter initializeNewPlayerCharacter(AddPlayerCharacterDTO addNewPlayer) {
        var dateNow = LocalDateTime.now();
        return PlayerCharacter
                .builder()
                .playerName(addNewPlayer.getPlayerName())
                .playerDisplayName(addNewPlayer.getPlayerDisplayName())
                .battleAttributes(BattleAttributes
                        .builder()
                        .hitPoints(addNewPlayer.getBattleAttributes().getHitPoints())
                        .mana(addNewPlayer.getBattleAttributes().getMana())
                        .experience(BASE_EXP)
                        .baseLevel(BASE_LEVEL)
                        .attackPower(addNewPlayer.getBattleAttributes().getAttackPower())
                        .moveSpeed(addNewPlayer.getBattleAttributes().getMoveSpeed())
                        .playerHealthStatus(INITIAL_PLAYER_HEALTH_STATUS)
                        .build())
                .playerOnlineStatus(INITIAL_PLAYER_ONLINE_STATUS)
                .creationDate(dateNow)
                .lastUpdatedDate(dateNow)
                .build();
    }

    private void saveErrorLog(String errorMessage) {
        logsService.saveNewErrorLog(errorMessage);
    }
}
