package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.database.entity.enums.PlayerHealthStatus;
import com.influx.database.entity.enums.PlayerOnlineStatus;
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

import static com.influx.engine.util.literals.ErrorLiterals.ADD_PLAYER_EXISTING_ERROR;
import static com.influx.engine.util.literals.ErrorLiterals.UPDATE_PLAYER_NOT_EXISTING_ERROR;

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

    private PlayerCharacter initializeNewPlayerCharacter(AddPlayerCharacterDTO addNewPlayer) {
        var dateNow = LocalDateTime.now();
        return PlayerCharacter
                .builder()
                .playerName(addNewPlayer.getPlayerName())
                .playerDisplayName(addNewPlayer.getPlayerDisplayName())
                .battleAttributes(BattleAttributes
                        .builder()
                        .attackPower(1)
                        .experience(0L)
                        .baseLevel(1)
                        .mana(BigDecimal.valueOf(10))
                        .hitPoints(BigDecimal.valueOf(50))
                        .moveSpeed(2)
                        .playerHealthStatus(PlayerHealthStatus.ALIVE)
                        .build())
                .playerOnlineStatus(PlayerOnlineStatus.OFFLINE)
                .creationDate(dateNow)
                .lastUpdatedDate(dateNow)
                .build();
    }

    private void saveErrorLog(String errorMessage) {
        logsService.saveNewErrorLog(errorMessage);
    }
}
