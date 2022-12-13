package com.influx.engine.service;

import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.database.repository.PlayerCharacterRepository;
import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.exceptions.PlayerCharacterException;
import com.influx.engine.service.logs.LogsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());
        when(playerCharacterRepository.save(any(PlayerCharacter.class))).thenReturn(createPlayerCharacter());

        var response =
                cut.saveNewPlayerCharacter(createAddPlayerCharacterDTO());

        assertNotNull(response);
        assertEquals(PLAYER_CHARACTER_NAME, response.getPlayerName());
        //TODO: Check value of initialization

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(playerCharacterRepository).save(any(PlayerCharacter.class));

        verifyNoMoreInteractions(playerCharacterRepository);
        verifyNoInteractions(logsService);
    }

    @Test
    void saveNewPlayerCharacterPlayerIsExistingShouldThrowError() {
        String errorMessage = "Unable to save new player with name player_character_name. Player Name is already existing";
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(createPlayerCharacter()));

        final PlayerCharacterException exception = assertThrows(PlayerCharacterException.class,
                ()-> cut.saveNewPlayerCharacter(createAddPlayerCharacterDTO()));

        assertEquals(errorMessage, exception.getMessage());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(logsService).saveNewErrorLog(errorMessage);

        verifyNoMoreInteractions(playerCharacterRepository);
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
        when(playerCharacterRepository.findAll()).thenReturn(Arrays.asList(playerCharacter, playerCharacter));

        var response = cut.findAllPlayerCharacters();

        assertEquals(2, response.size());

        verify(playerCharacterRepository).findAll();

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
        String errorMessage = "Player with name player_character_name is not existing";
        when(playerCharacterRepository.findByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());

        final PlayerCharacterException exception = assertThrows(PlayerCharacterException.class,
                ()-> cut.updatePlayerCharacter(createAddPlayerCharacterDTO(), PLAYER_CHARACTER_NAME));

        assertEquals(errorMessage, exception.getMessage());

        verify(playerCharacterRepository).findByPlayerName(PLAYER_CHARACTER_NAME);
        verify(logsService).saveNewErrorLog(errorMessage);

        verifyNoMoreInteractions(playerCharacterRepository);
    }

    private AddPlayerCharacterDTO createAddPlayerCharacterDTO() {
        var addPlayerCharacter = new AddPlayerCharacterDTO();
        addPlayerCharacter.setPlayerName(PLAYER_CHARACTER_NAME);
        addPlayerCharacter.setPlayerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME);
        return addPlayerCharacter;
    }

    private PlayerCharacter createPlayerCharacter() {
        return PlayerCharacter
                .builder()
                .playerName(PLAYER_CHARACTER_NAME)
                .battleAttributes(BattleAttributes
                        .builder()
                        .build())
                .build();
    }
}
