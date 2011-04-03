package com.pmf.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.pmf.util.EjbConstants;

@Entity
@Table(name=EjbConstants.TABLE_CIUDAD)
@NamedQueries({@NamedQuery(name = "ciudad.findByCountry", query = "SELECT d FROM Ciudad d where d.pais=:pais and d.status=1")})
public class City implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="city")
	private String city;		 	 	 	 	 	 	 
	@Column(name="status")
	private int status;
	
	@ManyToOne 
    @JoinColumn(name="countryid") 
    private Country country;
	
	public City(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}


}
