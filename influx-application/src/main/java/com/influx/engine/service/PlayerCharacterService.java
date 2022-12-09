package com.influx.engine.service;

import com.influx.engine.dto.AddPlayerCharacterDTO;
import com.influx.engine.dto.PlayerCharacterDTO;
import com.influx.database.entity.BattleAttributes;
import com.influx.database.entity.PlayerCharacter;
import com.influx.database.entity.enums.PlayerHealthStatus;
import com.influx.database.entity.enums.PlayerOnlineStatus;
import com.influx.database.repository.PlayerCharacterRepository;
import com.influx.engine.util.mapper.PlayerCharacterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerCharacterService {

    private final PlayerCharacterRepository playerCharacterRepository;

    public PlayerCharacterDTO saveNewPlayerCharacter (AddPlayerCharacterDTO addNewPlayer) {
        if (playerCharacterRepository.findByPlayerName(addNewPlayer.getPlayerName()).isPresent()) {
            throw new RuntimeException("Player is already existing");
        } else {
            var savedPlayer = playerCharacterRepository.save(initializeNewPlayerCharacter(addNewPlayer));
            return PlayerCharacterMapper.map(savedPlayer);
        }
    }

    private PlayerCharacter initializeNewPlayerCharacter (AddPlayerCharacterDTO addNewPlayer) {
        return PlayerCharacter
                .builder()
                .playerName(addNewPlayer.getPlayerName())
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
                .creationDate(LocalDateTime.now())
                .build();
    }
}
