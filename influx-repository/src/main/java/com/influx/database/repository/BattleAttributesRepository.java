package com.influx.database.repository;

import com.influx.database.entity.BattleAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleAttributesRepository extends JpaRepository<BattleAttributes, Long> {
}
