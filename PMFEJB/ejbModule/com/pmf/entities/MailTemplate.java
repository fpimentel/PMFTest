package com.pmf.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.pmf.util.EjbConstants;

@Entity
@Table(name=EjbConstants.TABLE_MAIL_TEMPLATES)
@NamedQueries({@NamedQuery(name = "MailTemplate.findByName", query = "SELECT d FROM MailTemplate d where d.name=:name")})
public class MailTemplate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="name", length=20)
	private String name;		 	 	 	 	 	 	 
	@Column(name="template")
	private String template;
	@Column(name="subject")
	private String subject;
	
	public MailTemplate(){
		super();
	}
	
	
	
	@Override
	public String toString() {
		return "MailTemplate [id=" + id + ", name=" + name + ", template="
				+ template + ", subject=" + subject + "]";
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
