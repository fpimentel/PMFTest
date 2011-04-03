package com.pmf.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.pmf.util.EjbConstants;

@Entity
@Table(name=EjbConstants.TABLE_USER_TYPES)
@NamedQueries({@NamedQuery(name = "userType.findAll", query = "SELECT d FROM UserType d where d.status=1"),
			   @NamedQuery(name = "findByCriteria",   query = "select d from UserType d where d.admin=:admin AND d.status=:status")})
public class UserType implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="user_type")
	private int type;
	@Column(name="description")
	private String desc;		 	 	 	 	 	 	 
	@Column(name="admin")
	private int admin;		 	 	 	 	 	 	 
	@Column(name="status")
	private int status;
	
	public UserType(){
		super();
	}
	public UserType(String desc, int type) {
		this.desc = desc;
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean isAdmin() {
		return (admin == 1);
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}		 	 	 	 
	
	@Override
	public String toString() {
		return "UserType:" + getType() + " [desc: " + getDesc() + "\nadmin: "
				+ getAdmin() + "\nstatus: " + getStatus()+ "]";
	}
}
