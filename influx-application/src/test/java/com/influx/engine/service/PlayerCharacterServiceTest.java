package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.database.repository.PlayerCharacterRepository;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.dto.addplayer.UpdateBattleAttributesDTO;
import com.influx.engine.exceptions.PlayerCharacterException;
import com.influx.engine.service.logs.LogsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static com.influx.engine.util.literals.basevalues.BaseValuesLiterals.FIND_ALL_PLAYERS_SORT_VARIABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PlayerCharacterServiceTest extends PlayerCharacterLiterals {

    private PlayerCharacterService cut;

    @Mock
    private PlayerCharacterRepository playerCharacterRepository;

    @Mock
    private LogsService logsService;

    @Mock
    private BattleAttributeService battleAttributeService;

    @BeforeEach
    public void setup() {
        cut = new PlayerCharacterService(playerCharacterRepository, logsService, battleAttributeService);
    }

    @Test
    void saveNewPlayerCharacterShouldReturnOk() {
        var addPlayerCharacter = createAddPlayerCharacterDTO();
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());
        when(playerCharacterRepository.save(any(PlayerCharacter.class))).thenReturn(createPlayerCharacter());

        var response =
                cut.saveNewPlayerCharacter(addPlayerCharacter);

        assertNotNull(response);
        assertEquals(PLAYER_CHARACTER_NAME, response.getPlayerName());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(playerCharacterRepository).save(any(PlayerCharacter.class));
        verify(battleAttributeService).initializeBattleAttributes(addPlayerCharacter);

        verifyNoMoreInteractions(playerCharacterRepository);
        verifyNoMoreInteractions(battleAttributeService);
        verifyNoInteractions(logsService);
    }

    @Test
    void saveNewPlayerCharacterPlayerIsExistingShouldThrowError() {
        var playerCharacterDto = createAddPlayerCharacterDTO();
        String errorMessage = "Unable to save new player with name player_character_name. Player Name is already existing";
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(createPlayerCharacter()));

        final PlayerCharacterException exception = assertThrows(PlayerCharacterException.class,
                ()-> cut.saveNewPlayerCharacter(playerCharacterDto));

        assertEquals(errorMessage, exception.getMessage());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(logsService).saveNewErrorLog(errorMessage);

        verifyNoMoreInteractions(playerCharacterRepository);
        verifyNoInteractions(battleAttributeService);
    }

    @Test
    void findPlayerCharacterByPlayerNameShouldReturnOk() {
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(createPlayerCharacter()));

        var response = cut.findPlayerCharacterByPlayerName(PLAYER_CHARACTER_NAME);

        assertTrue(response.isPresent());
        assertEquals(PLAYER_CHARACTER_NAME, response.get().getPlayerName());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    @Test
    void findPlayerCharacterByPlayerNameNotFoundShouldReturnEmpty() {
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());

        var response = cut.findPlayerCharacterByPlayerName(PLAYER_CHARACTER_NAME);

        assertFalse(response.isPresent());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    @Test
    void findAllPlayerCharactersShouldReturnOk() {
        var playerCharacter = createPlayerCharacter();
        var pageable = PageRequest.of(
                PAGEABLE_OFFSET, PAGEABLE_LIMIT, Sort.by(Sort.Direction.ASC, FIND_ALL_PLAYERS_SORT_VARIABLE));
        when(playerCharacterRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(playerCharacter, playerCharacter)));

        var response = cut.findAllPlayerCharacters(PAGEABLE_LIMIT, PAGEABLE_OFFSET);

        assertEquals(2, response.size());

        verify(playerCharacterRepository).findAll(pageable);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    @Test
    void updatePlayerCharacterShouldReturnOk() {
        var playerCharacterFromDb = createPlayerCharacter();
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(playerCharacterFromDb));
        when(playerCharacterRepository.save(any(PlayerCharacter.class))).thenReturn(playerCharacterFromDb);

        var response = cut.updatePlayerCharacter(createAddPlayerCharacterDTO(), PLAYER_CHARACTER_NAME);

        assertNotNull(response);

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(playerCharacterRepository).save(any(PlayerCharacter.class));

        verifyNoMoreInteractions(playerCharacterRepository);
        verifyNoInteractions(logsService);
    }

    @Test
    void updatePlayerCharacterShouldNotExistingShouldThrowError() {
        var playerCharacterDto = createAddPlayerCharacterDTO();
        String errorMessage = "Unable to update player with name player_character_name. Player is not existing";
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());

        final PlayerCharacterException exception = assertThrows(PlayerCharacterException.class,
                ()-> cut.updatePlayerCharacter(playerCharacterDto, PLAYER_CHARACTER_NAME));

        assertEquals(errorMessage, exception.getMessage());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(logsService).saveNewErrorLog(errorMessage);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    @Test
    void deletePlayerCharacterByNamePlayerExisting() {
        var playerCharacter = createPlayerCharacter();

        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(playerCharacter));

        cut.deletePlayerCharacterByName(PLAYER_CHARACTER_NAME);

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(playerCharacterRepository).delete(playerCharacter);

        verifyNoInteractions(logsService);
        verifyNoMoreInteractions(playerCharacterRepository);
    }

    @Test
    void deletePlayerCharacterByNamePlayerNotExistingShouldThrowError() {
        String errorMessage = "Unable to delete player with name player_character_name. Player is not existing";
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());

        final PlayerCharacterException exception = assertThrows(PlayerCharacterException.class,
                ()-> cut.deletePlayerCharacterByName(PLAYER_CHARACTER_NAME));

        assertEquals(errorMessage, exception.getMessage());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(logsService).saveNewErrorLog(errorMessage);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    private AddPlayerCharacterDTO createAddPlayerCharacterDTO() {
        var battleAttributes = new UpdateBattleAttributesDTO();

        battleAttributes.setBaseLevel(LEVEL);
        battleAttributes.setExperience(EXP);
        battleAttributes.setHitPoints(HIT_POINTS);
        battleAttributes.setMana(MANA);
        battleAttributes.setAttackPower(ATTACK_POWER);
        battleAttributes.setMoveSpeed(MOVE_SPEED);

        var addPlayerCharacter = new AddPlayerCharacterDTO();

        addPlayerCharacter.setPlayerName(PLAYER_CHARACTER_NAME);
        addPlayerCharacter.setPlayerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME);
        addPlayerCharacter.setBattleAttributes(battleAttributes);

        return addPlayerCharacter;
    }

    private PlayerCharacter createPlayerCharacter() {
        return PlayerCharacter
                .builder()
                .playerName(PLAYER_CHARACTER_NAME)
                .battleAttributes(createBattleAttribute())
                .build();
    }

    private BattleAttributes createBattleAttribute() {
        return BattleAttributes
                .builder()
                .baseLevel(LEVEL)
                .experience(EXP)
                .hitPoints(HIT_POINTS)
                .mana(MANA)
                .attackPower(ATTACK_POWER)
                .moveSpeed(MOVE_SPEED)
                .playerHealthStatus(PLAYER_HEALTH_STATUS)
                .build();
    }
}
