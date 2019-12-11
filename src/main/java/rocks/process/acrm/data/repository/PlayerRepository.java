package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Coach;
import rocks.process.acrm.data.domain.Player;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByPlayerId (Long playerId);
    Player findByPlayerFirstNameAndPlayerLastName (String playerFirstName, String playerLastName);
    Player findByPlayerPosition (String playerPosition);
    List<Player> findByPlayerIdAndCoach_CoachId (Long playerId , Long coachId);

    @Override
    List<Player> findAll();
}
