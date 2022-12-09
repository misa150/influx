package com.influx.engine.controller;

import com.influx.engine.dto.AddPlayerCharacterDTO;
import com.influx.engine.dto.PlayerCharacterDTO;
import com.influx.engine.service.PlayerCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("player-character")
@RequiredArgsConstructor
public class PlayerCharacterController {

    private final PlayerCharacterService playerCharacterService;

    @PostMapping(value = {"/add-player-character"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCharacterDTO> addPlayer (@RequestBody AddPlayerCharacterDTO addNewPlayer) {
        return ResponseEntity.ok()
                .body(playerCharacterService.saveNewPlayerCharacter(addNewPlayer));
    }

    @GetMapping(value = {"/find-player-character/{playerName}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCharacterDTO> findPlayerByName (@PathVariable("playerName") String playerName) {
        return playerCharacterService.findPlayerCharacterByPlayerName(playerName)
                .map(playerCharacterDTO -> ResponseEntity.ok(playerCharacterDTO))
                .orElse(ResponseEntity.notFound().build());
    }
}
