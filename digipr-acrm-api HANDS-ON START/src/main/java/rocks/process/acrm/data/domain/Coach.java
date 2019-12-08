/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Coach {

	@Id
	@GeneratedValue
	private Long coachId;
	@NotEmpty(message = "Please provide a coach name.")
	private String coachName;
	@Email(message = "Please provide a valid e-mail.")
	@NotEmpty(message = "Please provide an e-mail.")
	private String email;
	@org.springframework.data.annotation.Transient //will not be serialized
	private String password;
	@javax.persistence.Transient // will not be stored in DB
	private String remember;
	@OneToMany(mappedBy = "coach")
	private List<Player> players;

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		String transientPassword = this.password;
		this.password = null;
		return transientPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}
}
