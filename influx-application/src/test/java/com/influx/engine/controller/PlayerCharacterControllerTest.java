package com.influx.engine.controller;


import com.influx.engine.dto.addplayer.AddPlayerCharacterDTO;
import com.influx.engine.dto.playercharacter.PlayerCharacterDTO;
import com.influx.engine.service.PlayerCharacterLiterals;
import com.influx.engine.service.PlayerCharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerCharacterControllerTest extends PlayerCharacterLiterals {

    private PlayerCharacterController cut;

    @Mock
    private PlayerCharacterService playerCharacterService;

    @BeforeEach
    private void setup() {
        cut = new PlayerCharacterController(playerCharacterService);
    }

    @Test
    void addPlayerCharacterShouldBeOk() {
        var addPlayer = createAddPlayerCharacterDTO();
        var playerCharacter = createPlayerCharacterDTO();

        when(playerCharacterService.saveNewPlayerCharacter(addPlayer))
                .thenReturn(playerCharacter);

        var response = cut.addPlayerCharacter(addPlayer);

        basicResponseCheck(response, HttpStatus.OK);
    }

    @Test
    void findPlayerCharacterByNamePlayerIsFound() {
        when(playerCharacterService.findPlayerCharacterByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.of(createPlayerCharacterDTO()));

        var response = cut.findPlayerCharacterByName(PLAYER_CHARACTER_NAME);

        basicResponseCheck(response, HttpStatus.OK);
    }

    @Test
    void findPlayerCharacterByNamePlayerIsNotFound() {
        when(playerCharacterService.findPlayerCharacterByPlayerName(PLAYER_CHARACTER_NAME))
                .thenReturn(Optional.empty());

        var response = cut.findPlayerCharacterByName(PLAYER_CHARACTER_NAME);

        basicResponseCheck(response, HttpStatus.NOT_FOUND);
    }

    @Test
    void findAllPlayerCharactersShouldFindPlayers() {
        when(playerCharacterService.findAllPlayerCharacters())
                .thenReturn(Arrays.asList(
                        createPlayerCharacterDTO(), createPlayerCharacterDTO()));

        var response = cut.findAllPlayerCharacters();

        basicResponseCheck(response, HttpStatus.OK);
        assertEquals(2, response.getBody().size());
    }

    @Test
    void findAllPlayerCharactersShouldFindNoPlayers() {
        when(playerCharacterService.findAllPlayerCharacters())
                .thenReturn(new ArrayList<>());

        var response = cut.findAllPlayerCharacters();

        basicResponseCheck(response, HttpStatus.OK);
        assertEquals(0, response.getBody().size());
    }

    @Test
    void deletePlayerCharacterByCharacterNameShouldBeOk() {
        var response = cut.deletePlayerCharacterByCharacterName(PLAYER_CHARACTER_NAME);

        basicResponseCheck(response, HttpStatus.NO_CONTENT);
    }

    @Test
    void updatePlayerCharacterShouldBeOk() {
        var addPlayer = createAddPlayerCharacterDTO();
        var playerCharacter = createPlayerCharacterDTO();

        when(playerCharacterService.updatePlayerCharacter(addPlayer, PLAYER_CHARACTER_NAME))
                .thenReturn(playerCharacter);

        var response = cut.updatePlayerCharacter(addPlayer, PLAYER_CHARACTER_NAME);

        basicResponseCheck(response, HttpStatus.OK);
    }

    private void basicResponseCheck(ResponseEntity response, HttpStatus httpStatus) {
        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(httpStatus, response.getStatusCode());
    }

    private AddPlayerCharacterDTO createAddPlayerCharacterDTO() {
        var addPlayer = new AddPlayerCharacterDTO();
        return addPlayer;
    }

    private PlayerCharacterDTO createPlayerCharacterDTO() {
        return PlayerCharacterDTO.builder()
                .playerName(PLAYER_CHARACTER_NAME)
                .playerDisplayName(PLAYER_CHARACTER_DISPLAY_NAME)
                .build();
    }

}
