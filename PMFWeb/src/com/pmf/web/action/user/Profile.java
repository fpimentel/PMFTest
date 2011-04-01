package com.pmf.web.action.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.pmf.commons.Constants;
import com.pmf.ejbs.user.UserBean;
import com.pmf.entities.Contact;
import com.pmf.entities.User;
import com.pmf.web.session.UserSession;
import com.pmf.web.util.Util;
import com.pmf.web.util.lists.Ciudad;

public class Profile extends ActionSupport implements ServletRequestAware, ServletContextAware  {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;
	private String userid;
	private String login;
	private ArrayList<String> salutationList; 
	private String salutation;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date birthDate;
	private String password;
	private String passwordConfirmation;
	private String eMail;
	private String referal;
	private String habitualPit;
	// contacts
	private Contact contact;
	private String pais;
	private String ciudad;
	private String ciudadLabel;
	private ArrayList<Ciudad> ciudadList;
	private String sector;
	private String calle;
	private String numero;
	private String apto;
	private String telefono;
	private String celular;
	
	// vehicles
	private List<Boolean> car;
	private List<String> descripcion;
	private List<String> chassis;
	private List<String> marca;
	private List<String> modelo;
	private List<String> anio;
	
	public String modify() throws Exception {
		return SUCCESS;
	}
	public String execute() throws Exception {
		this.salutationList = new ArrayList<String>();
		salutationList.add("Sr.");
		salutationList.add("Sra.");
		salutationList.add("Srta.");
		
		ciudadList = Util.getCiudades();
		try {
			UserSession userSession = (UserSession) request.getSession().getAttribute(Constants.GLOBAL_USER_SESSION_KEY);
			String login = userSession.getLogin();
			UserBean userBean = new UserBean();
			User user = userBean.getUser(login);
			if (user != null) {
				Contact contact = user.getContact();
				setContact(contact);
				setLogin(login);
				setUserid(user.getUserId()+"");
				setSalutation(user.getSalutation());
				setFirstName(user.getFirstName());
				setMiddleName(user.getMiddleName() + "");
				setLastName(user.getLastName());
				setBirthDate(Constants.BIRTH_DATE_FORMAT.parse(user.getBirthdate()));
				setEMail(user.getEmail());
				setReferal(user.getReferal());
				setHabitualPit(user.getHabitualPit());
				// contacts
				if (contact != null) {
					setPais(contact.getPais());
					setCiudad(contact.getCiudad());
					setSector(contact.getSector());
					setCalle(contact.getCalle());
					setNumero(contact.getNumero());
					setApto(contact.getApto());
					setTelefono(contact.getTelefono());
					setCelular(contact.getCelular());
					setCiudadLabel(Util.getCiudad(contact.getCiudad()));
				}
			}
		} catch (Exception ex){
			addActionError("Ocurrieron errores " + ex.getMessage());
			throw ex;
		}
		//addActionMessage("Confirmacion exitosa");
		return SUCCESS;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public ArrayList<String> getSalutationList() {
		return salutationList;
	}
	public void setSalutationList(ArrayList<String> salutationList) {
		this.salutationList = salutationList;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public String getEMail() {
		return eMail;
	}
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	public String getReferal() {
		return referal;
	}
	public void setReferal(String referal) {
		this.referal = referal;
	}
	public String getHabitualPit() {
		return habitualPit;
	}
	public void setHabitualPit(String habitualPit) {
		this.habitualPit = habitualPit;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCiudadLabel() {
		return ciudadLabel;
	}
	public void setCiudadLabel(String ciudadLabel) {
		this.ciudadLabel = ciudadLabel;
	}
	public ArrayList<Ciudad> getCiudadList() {
		return ciudadList;
	}
	public void setCiudadList(ArrayList<Ciudad> ciudadList) {
		this.ciudadList = ciudadList;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getApto() {
		return apto;
	}
	public void setApto(String apto) {
		this.apto = apto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public List<Boolean> getCar() {
		return car;
	}
	public void setCar(List<Boolean> car) {
		this.car = car;
	}
	public List<String> getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(List<String> descripcion) {
		this.descripcion = descripcion;
	}
	public List<String> getChassis() {
		return chassis;
	}
	public void setChassis(List<String> chassis) {
		this.chassis = chassis;
	}
	public List<String> getMarca() {
		return marca;
	}
	public void setMarca(List<String> marca) {
		this.marca = marca;
	}
	public List<String> getModelo() {
		return modelo;
	}
	public void setModelo(List<String> modelo) {
		this.modelo = modelo;
	}
	public List<String> getAnio() {
		return anio;
	}
	public void setAnio(List<String> anio) {
		this.anio = anio;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;		
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.context = arg0;
		
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
