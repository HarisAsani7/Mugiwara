package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Coach;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    Coach findByCoachId (Long coachId);
    Coach findByTeamName (String teamName);
    Coach findByCoachIdAndCoachName (Long coachId, String coachName);
    Coach findByCoachName (String coachName);

    @Override
    List<Coach> findAll();
}
