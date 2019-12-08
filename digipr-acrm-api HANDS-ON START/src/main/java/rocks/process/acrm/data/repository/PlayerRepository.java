/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Player;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByPlayerNumber(String playerNumber);
	Player findByPlayerNumberAndPlayerId(String playerNumber, Long coachId);
	List<Player> findByCoachId(Long coachId);
	List<Player> findByIdAndCoachId(Long playerId, Long coachId);
}