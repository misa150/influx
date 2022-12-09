package com.influx.database.repository;

import com.influx.database.entity.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Long> {

    Optional<PlayerCharacter> findByPlayerName(String playerName);
}
