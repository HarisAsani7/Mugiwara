package rocks.process.acrm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.process.acrm.business.service.CoachService;
import rocks.process.acrm.business.service.PlayerService;
import rocks.process.acrm.data.domain.Coach;
import rocks.process.acrm.data.domain.Player;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PlayerEndpoint {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private CoachService coachService;

    @PostMapping(path = "/player", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> postPlayer (@RequestBody Player player) {
        try {
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

    @PostMapping(path = "/coach", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Coach> registerCoach(@RequestBody Coach coach){
        try {
            coachService.saveCoach(coach);
        }catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{coachId}")
                .buildAndExpand(coach.getCoachId()).toUri();

        return ResponseEntity.created(location).body(coach);

    }

    @PutMapping(path = "/player/{playerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> putFilm(@RequestBody Player player, @PathVariable(value = "playerId") String playerId) {
        try {
            player.setPlayerId(Long.parseLong(playerId));

            player = playerService.editPlayer(player);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(player);
    }


    // get all players
    @GetMapping(path = "/player" , produces = "application/json")
    public List<Player> getAllPlayers(){
        return playerService.findAllPlayers();
    }

    // get all coaches
    @GetMapping(path = "/coach" , produces = "application/json")
    public List<Coach> getAllCoaches(){
        return coachService.getAllCoaches();
    }

    // get a specific player
    @GetMapping(path = "/player/{playerId}", produces = "application/json")
    public ResponseEntity<Player> getPlayer(@PathVariable(value = "playerId") String playerId){
        Player player = null;
        try {
            player = playerService.findPlayerById(Long.parseLong(playerId));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return ResponseEntity.ok(player);
    }

    //delete a player
    @DeleteMapping (path = "/player" , produces = "application/json")
    public ResponseEntity<Void> deletePlayer (@PathVariable(value = "playerId") String playerId){
        try {
            playerService.deletePlayer(Long.parseLong(playerId));
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }


    /*
    //get a specific player to see his stats
    @GetMapping (path = "/player/{playerId}", produces = "application/json")
    public ResponseEntity<Player> getPlayerStats (@PathVariable(value = "playerId") String playerId){
        Player player = null;
        try {
            player = playerService.getPlayer(Long.parseLong(playerId));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(player);
    }

    */




}
