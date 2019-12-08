/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.process.acrm.business.service.PlayerService;
import rocks.process.acrm.data.domain.Player;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PlayerEndpoint {
    @Autowired
    private PlayerService playerService;

	//TODO uncomment
    @PostMapping(path = "/player", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> postPlayer(@RequestBody Player player) {
        try {
			// TODO create player
            player = playerService.editPlayer(player);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{playerId}")
                .buildAndExpand(player.getPlayerId()).toUri();

        return ResponseEntity.created(location).body(player);
    }

	//TODO path = "/player"
    @GetMapping(path = "/player", produces = "application/json")
    public List<Player> getPlayer() {
		//TODO get all players
        return playerService.findAllPlayers();
    }

	//TODO uncomment
    @GetMapping(path = "/player/{playerId}", produces = "application/json")
    public ResponseEntity<Player> getPlayer(@PathVariable(value = "playerId") String playerId) {
        Player player = null;
        try {
			//TODO find player by id
            player = playerService.findPlayerById(Long.parseLong(playerId));
        } catch (Exception e) {
			//TODO HTTP Status: not found

        }
        return ResponseEntity.ok(player);
    }

	//TODO uncomment
    @PutMapping(path = "/player/{playerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> putPlayer(@RequestBody Player player, @PathVariable(value = "playerId") String playerId) {
        try {
            player.setPlayerId(Long.parseLong(playerId));
			//TODO edit player
            player = playerService.editPlayer(player);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(player);
    }

	//TODO delete mapping
    @DeleteMapping (path = "/player/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable(value = "playerId") String playerId) {
        try {
			//TODO delete customer
            playerService.deletePlayer(Long.parseLong(playerId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }
}