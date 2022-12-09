package com.influx.engine.controller;

import com.influx.engine.dto.AddPlayerCharacterDTO;
import com.influx.engine.dto.PlayerCharacterDTO;
import com.influx.engine.service.PlayerCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("player-character")
@RequiredArgsConstructor
public class PlayerCharacterController {

    private final PlayerCharacterService playerCharacterService;

    @PostMapping(value = {"/add-player-character"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCharacterDTO> addPlayerCharacter(
            @RequestBody AddPlayerCharacterDTO addNewPlayer) {
        return ResponseEntity.ok()
                .body(playerCharacterService.saveNewPlayerCharacter(addNewPlayer));
    }

    @GetMapping(value = {"/find-player-character/{playerCharacterName}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCharacterDTO> findPlayerCharacterByName(
            @PathVariable("playerCharacterName") String playerCharacterName) {
        return playerCharacterService.findPlayerCharacterByPlayerName(playerCharacterName)
                .map(playerCharacterDTO -> ResponseEntity.ok(playerCharacterDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = {"/find-all-player-character"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerCharacterDTO>> findAllPlayerCharacters() {
        return ResponseEntity.ok().body(playerCharacterService.findAllPlayerCharacters());
    }

    @DeleteMapping(value = {"/delete-player-character/{playerCharacterName}"})
    public ResponseEntity<Void> deletePlayerCharacterByName(
            @PathVariable("playerCharacterName") String playerCharacterName) {
        playerCharacterService.deletePlayerCharacterByName(playerCharacterName);
        return ResponseEntity.noContent().build();
    }
}
