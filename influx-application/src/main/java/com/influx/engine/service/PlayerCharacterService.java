package com.influx.engine.service;

import com.influx.database.entity.PlayerCharacter;
import com.influx.database.repository.PlayerCharacterRepository;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.dto.playercharacter.PlayerCharacterDTO;
import com.influx.engine.exceptions.PlayerCharacterException;
import com.influx.engine.service.logs.LogsService;
import com.influx.engine.util.mapper.PlayerCharacterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.influx.engine.util.literals.basevalues.BaseValuesLiterals.*;
import static com.influx.engine.util.literals.logs.ErrorLiterals.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerCharacterService {

    private final PlayerCharacterRepository playerCharacterRepository;
    private final LogsService logsService;
    private final BattleAttributeService battleAttributeService;

    public PlayerCharacterDTO saveNewPlayerCharacter(AddPlayerCharacterDTO addNewPlayerCharacter) {
        return (PlayerCharacterDTO) playerCharacterRepository.findByPlayerName(addNewPlayerCharacter.getPlayerName())
                .map(playerCharacter -> {
                    throw new PlayerCharacterException(
                        String.format(ADD_PLAYER_EXISTING_ERROR, playerCharacter.getPlayerName()), logsService);})
                .orElse(savePlayerCharacter(addNewPlayerCharacter));
    }

    private PlayerCharacterDTO savePlayerCharacter(AddPlayerCharacterDTO addNewPlayerCharacter) {
        var savedPlayer = playerCharacterRepository.save(initializeNewPlayerCharacter(addNewPlayerCharacter));
        return mapPlayerCharacter(savedPlayer);
    }

    public Optional<PlayerCharacterDTO> findPlayerCharacterByPlayerName(String playerCharacterName) {
        return playerCharacterRepository.findByPlayerName(playerCharacterName)
                .map(playerCharacter -> Optional.of(mapPlayerCharacter(playerCharacter)))
                .orElse(Optional.empty());
    }

    public List<PlayerCharacterDTO> findAllPlayerCharacters(Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, FIND_ALL_SORT));

        return playerCharacterRepository.findAll(pageable)
                .stream()
                .map(this::mapPlayerCharacter)
                .toList();
    }

    public void deletePlayerCharacterByName(String playerCharacterName) {
        playerCharacterRepository.findByPlayerName(playerCharacterName).ifPresentOrElse(
                this::deletePlayer,
                () -> {
                    throw new PlayerCharacterException(
                        String.format(DELETE_PLAYER_NOT_EXISTING_ERROR, playerCharacterName), logsService);
                }
        );
    }

    public PlayerCharacterDTO updatePlayerCharacter(
            AddPlayerCharacterDTO addNewPlayerCharacter, String playerCharacterName) {
        return playerCharacterRepository.findByPlayerName(playerCharacterName)
                .map(playerCharacter -> updateExistingPlayerCharacter(addNewPlayerCharacter, playerCharacter))
                .orElseThrow(() -> new PlayerCharacterException(
                        String.format(UPDATE_PLAYER_NOT_EXISTING_ERROR, playerCharacterName), logsService));
    }

    private PlayerCharacterDTO updateExistingPlayerCharacter(
            AddPlayerCharacterDTO addNewPlayerCharacter, PlayerCharacter playerCharacter) {
        updatePlayerCharacter(playerCharacter, addNewPlayerCharacter);
        battleAttributeService.updateBattleAttributes(playerCharacter, addNewPlayerCharacter);
        return mapPlayerCharacter(playerCharacterRepository.save(playerCharacter));
    }

    private void deletePlayer(PlayerCharacter playerCharacter) {
        playerCharacterRepository.delete(playerCharacter);
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
                .battleAttributes(battleAttributeService.initializeBattleAttributes(addNewPlayer))
                .playerOnlineStatus(INITIAL_PLAYER_ONLINE_STATUS)
                .creationDate(dateNow)
                .lastUpdatedDate(dateNow)
                .build();
    }
}
