/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Player;
import rocks.process.acrm.data.repository.PlayerRepository;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private CoachService coachService;

	public Player editPlayer(@Valid Player player) throws Exception {
		if (player.getPlayerId() == null) {
			if (playerRepository.findByPlayerNumber(player.getPlayerNumber()) == null) {
				player.setCoach(coachService.getCurrentCoach());
				return playerRepository.save(player);
			}
			throw new Exception("Player number " + player.getPlayerNumber() + " already assigned to a player.");
		}
		if (playerRepository.findByPlayerNumberAndPlayerId(player.getPlayerNumber(), player.getPlayerId()) == null) {
			if (player.getCoach() == null) {
				player.setCoach(coachService.getCurrentCoach());
			}
			return playerRepository.save(player);
		}
		throw new Exception("Player number " + player.getPlayerNumber() + " already assigned to a player.");
	}

	public void deletePlayer(Long playerId)
	{
		playerRepository.deleteById(playerId);
	}
	
	public Player findPlayerById(Long playerId) throws Exception {
		List<Player> playerList = playerRepository.findByIdAndCoachId(playerId, coachService.getCurrentCoach().getCoachId());
		if(playerList.isEmpty()){
			throw new Exception("No player with ID "+playerId+" found.");
		}
		return playerList.get(0);
	}

	public List<Player> findAllPlayers() {
		return playerRepository.findByCoachId(coachService.getCurrentCoach().getCoachId());
	}
	
}
