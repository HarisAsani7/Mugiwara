/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Coach;
import rocks.process.acrm.data.repository.CoachRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;

@Service
@Validated
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    Validator validator;

    public void saveCoach(@Valid Coach coach) throws Exception {
        if (coach.getCoachId() == null) {
            if (coachRepository.findByEmail(coach.getEmail()) != null) {
                throw new Exception("Email address " + coach.getEmail() + " already assigned another coach.");
            }
        } else if (coachRepository.findByEmailAndIdNot(coach.getEmail(), coach.getCoachId()) != null) {
            throw new Exception("Email address " + coach.getEmail() + " already assigned another coach.");
        }
        coachRepository.save(coach);
    }

    public Coach getCurrentCoach() {
        String userEmail = "haris@haris.ch";
        return coachRepository.findByEmail(userEmail);
    }

    @PostConstruct
    private void init() throws Exception {
        Coach coach = new Coach();
        coach.setCoachName("Haris");
        coach.setEmail("haris@haris.ch");
        coach.setPassword("realmadrid");
        this.saveCoach(coach);
    }
}
