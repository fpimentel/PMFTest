package com.pmf.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.pmf.util.EjbConstants;

@Entity
@Table(name=EjbConstants.TABLE_USER_CONFIRMATION)
@NamedQueries({@NamedQuery(name = "userMailConfirmation.findByEmailNConfirmationCode", query = "SELECT d FROM UserMailConfirmation d where d.eMail=:email and d.confirmationCode=:confirmationcode and d.confirmado=0")})
public class UserMailConfirmation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="confirmationid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int confirmationId;
	@Column(name="email")
	private String eMail;		 	 	 	 	 	 	 
	@Column(name="confirmation_code")
	private String confirmationCode;
	@Column(name="generation_date")
	private String generationDate;
	@Column(name="confirmation_date")
	private String confirmationDate;
	@Column(name="confirmed")
	private int confirmed;
	@OneToOne 
    @JoinColumn(name="userid") 
    private User user;
	
	public UserMailConfirmation(){
		super();
	}
	
	public int getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(int confirmationId) {
		this.confirmationId = confirmationId;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	
	public String getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(String generationDate) {
		this.generationDate = generationDate;
	}

	public String getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(String confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
