package com.influx.engine.repository;

import com.influx.engine.entity.BattleAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleAttributesRepository extends JpaRepository<BattleAttributes, Long> {
}
