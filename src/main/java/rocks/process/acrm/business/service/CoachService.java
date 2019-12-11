package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Coach;
import rocks.process.acrm.data.repository.CoachRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;


@Service
@Validated
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    Validator validator;


    public void saveCoach(@Valid Coach coach) throws Exception{
        if (coach.getCoachId() == null){
            if (coachRepository.findByTeamName(coach.getTeamName()) != null){
             throw new Exception("Team: " + coach.getCoachName() + "already exist, choose a new Team.");
            }
            else if (coachRepository.findByCoachIdAndCoachName(coach.getCoachId(), coach.getCoachName()) != null){
                throw new Exception("Coach ID: " + coach.getCoachName() + "already exist.");
            }
        }
        coachRepository.save(coach);
    }


    public Coach getCoach (Long coachId){
        return coachRepository.findByCoachId(coachId);
    }

    public Coach getCurrentCoach(){
        String userName = "Haris";
        return coachRepository.findByCoachName(userName);
    }

    public List<Coach> getAllCoaches(){
        return coachRepository.findAll();
    }

    @PostConstruct
    private void init() throws Exception{
            Coach coach = new Coach();
            coach.setCoachName("Haris");
            coach.setTeamName("Langenbruck CF");
            coach.setRemember("standard");
            this.saveCoach(coach);
    }


}
