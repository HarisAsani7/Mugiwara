package rocks.process.acrm.business.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Player;
import rocks.process.acrm.data.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CoachService coachService;

    public Player editPlayer(@Valid Player player) throws Exception{
        if (player.getPlayerId() == null){
            if (playerRepository.findByPlayerPosition(player.getPlayerPosition()) == null){
                player.setCoach(coachService.getCurrentCoach());
                return playerRepository.save(player);
            }
            throw new Exception("Player position " + player.getPlayerPosition() + "is already assigned.");

        }
        if (playerRepository.findByPlayerFirstNameAndPlayerLastName(player.getPlayerFirstName(), player.getPlayerLastName()) == null){
            if (player.getCoach() == null){
                player.setCoach(coachService.getCurrentCoach());
            }
            return playerRepository.save(player);
        }
        throw  new Exception("Player name " + player.getPlayerFirstName() + " " + player.getPlayerLastName() + "exists already");

    }


    public void deletePlayer(Long playerId) {playerRepository.deleteById(playerId);}

    public void deleteAllPlayers(Long playerId){
        playerRepository.deleteAll();
    }


    public Player findPlayerById(Long playerId) throws Exception{
        List<Player> playerList = playerRepository.findByPlayerIdAndCoach_CoachId(playerId, coachService.getCurrentCoach().getCoachId());
        if (playerList.isEmpty()){
            throw new Exception("No Player with ID " + playerId + "was found.");
        }
        return playerList.get(0);
    }

    public List<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    @PostConstruct
    private void init() throws Exception{
        if (playerRepository.findByPlayerFirstNameAndPlayerLastName("Cristiano", "Ronaldo") == null){
            Player player = new Player();
            player.setPlayerFirstName("Cristiano");
            player.setPlayerLastName("Ronaldo");
            player.setPlayerPosition("Striker");
            this.editPlayer(player);
        }

        if (playerRepository.findByPlayerFirstNameAndPlayerLastName("Sergio", "Ramos") == null){
            Player player = new Player();
            player.setPlayerFirstName("Sergio");
            player.setPlayerLastName("Ramos");
            player.setPlayerPosition("Defender");
            this.editPlayer(player);
        }

        if (playerRepository.findByPlayerFirstNameAndPlayerLastName("Luca", "Modric") == null){
            Player player = new Player();
            player.setPlayerFirstName("Luca");
            player.setPlayerLastName("Modric");
            player.setPlayerPosition("Middlefield");
            this.editPlayer(player);
        }


    }


}
