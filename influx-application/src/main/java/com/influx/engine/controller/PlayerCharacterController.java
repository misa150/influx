package com.influx.engine.controller;

import com.influx.engine.dto.AddPlayerCharacterDTO;
import com.influx.engine.dto.PlayerCharacterDTO;
import com.influx.engine.service.PlayerCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("player-character")
@RequiredArgsConstructor
public class PlayerCharacterController {

    private final PlayerCharacterService playerCharacterService;

    @RequestMapping(method = {RequestMethod.POST}, value = {"/add-player"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerCharacterDTO> addPlayer (@RequestBody AddPlayerCharacterDTO addNewPlayer) {
        return ResponseEntity.ok()
                .body(playerCharacterService.saveNewPlayerCharacter(addNewPlayer));
    }
}
