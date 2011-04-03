package com.pmf.ejbs.user;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import com.pmf.commons.Constants;
import com.pmf.commons.security.PasswordBusiness;
import com.pmf.entities.Contact;
import com.pmf.entities.MailTemplate;
import com.pmf.entities.User;
import com.pmf.entities.UserSession;
import com.pmf.entities.UserType;
import com.pmf.exceptions.AutoException;
import com.pmf.exceptions.BadEmailException;
import com.pmf.exceptions.InvalidUserAndPasswordException;
import com.pmf.exceptions.NotExistsDepartmentException;
import com.pmf.exceptions.NotExistsException;
import com.pmf.exceptions.NotExistsUserException;
import com.pmf.util.EjbConstants;
import com.pmf.util.JPAUtil;

/**
 * Session Bean implementation class UserBean
 * @author Garis M. Suero
 * 06/05/2010
 * AutoPiezas01
 */
@Stateless(name="UserBean", mappedName="ejb/UserBeanJNDI")
public class UserBean implements UserBeanLocal {
	@PersistenceUnit(name="myAutoService")
	public EntityManager em;

    public UserBean() {
    	em = new JPAUtil().getEMF().createEntityManager();
    }
    
    public String sayHello(String name) { 	
        return "Hello " + name + "!"; 	
    } 

    @SuppressWarnings("unchecked")
    public User getUser(String login) throws NotExistsUserException
    {
    	User user = null;
		try {
			em.getTransaction().begin();
			Query query = em.createNamedQuery("User.findByLogin");
			query.setParameter("login", login);
			List<User> userList = query.getResultList();
			if (userList.size() > 0) {
	    		user = (User)userList.get(0);
			} else {
				throw new Exception();
			}
			em.flush();
    		em.getTransaction().commit();
		} catch (Exception ex) {
			throw new NotExistsUserException(EjbConstants.MESSAGE_NO_USER_EXISTS);
		}
    	return user;
    }
    
    @SuppressWarnings("unchecked")
    public User getUser(int userid) throws NotExistsUserException
    {
    	User user = null;
		try {
			Query query = em.createNamedQuery("User.findByUserId");
			query.setParameter("userId", userid);
			List<User> userList = query.getResultList();
			if (userList.size() > 0) {
	    		user = (User)userList.get(0);
			} else {
				user = null; 
			}
		} catch (Exception ex) {
			throw new NotExistsUserException(EjbConstants.MESSAGE_NO_USER_EXISTS  + "PRUEBA : "  + ex.getMessage());
		}
    	return user;
    }
    
    @SuppressWarnings("unchecked")
    public boolean changePassword(String login, String currentPassword, String newPassword) throws Exception
    {
    	boolean result = true;
    	try {
    		em.getTransaction().begin();
    		String currentPasswordHash = new PasswordBusiness(currentPassword).getMD5();
    		Query query = em.createNamedQuery("User.findByLoginNPassword");
    		query.setParameter("login", login);
    		query.setParameter("password",currentPasswordHash);
    		try {
    			List<User> userList = query.getResultList();
    			if (userList.size() > 0) {
    	    		User user = userList.get(0);
    				user.setPassword(new PasswordBusiness(newPassword).getMD5());
    				em.persist(user);
    			} else {
    				result = false; 
    			}
    		} catch (IndexOutOfBoundsException ex) {
    			result = false;
    		}
    		em.getTransaction().commit();
    	} catch (Exception er) {
    		throw er;
    	}
    	return result;
    }
    
    public int createUser(int userType, String login, String salutation, String firstName, 
			char middleName, String lastName, Date birthDate, String password, String email, String referal, String habitualPit) throws NotExistsDepartmentException {
        User e = new User();
    	
        try {
	        e.setUserType(getUserType(userType));
	        e.setLogin(login);
	        e.setSalutation(salutation);
	        e.setName(firstName);
	        e.setBirthdate(Constants.BIRTH_DATE_FORMAT.format(birthDate));
	        e.setPassword(password);
	        e.setEmail(email);
	        e.setLastLogin(Constants.COMMON_FULL_DATE_FORMAT.format(new Date()));
	        e.setReferal(referal);
        } catch (NotExistsDepartmentException ex) { 
    		throw ex;
    	}
        return createUser(e);
    }
    public int createUser(User user) {
    	System.out.println("CREANDO USUARIO : " + user.toString());
    	int userId = -1;
    	try {
	        em.getTransaction().begin();
	        em.persist(user);
	        em.flush();
	        em.getTransaction().commit();
	        userId = getUser(user.getLogin()).getId();
    	}  catch (NullPointerException ex) {
    		userId = -1;    		
    	} catch (Exception ex) {
    		System.out.println("[EXCEPCION]!\n" + ex.getMessage());
    		ex.printStackTrace();
    	}
        return userId;
    }
    
    public boolean updateUser(String login, String salutation, String firstName, char middleName, String lastName, Date birthdate, String password, String email, String habitualPit) throws NotExistsUserException, BadEmailException {
    	boolean result = true;
    	try {
   	    	User user = getUser(login);
    		em.getTransaction().begin();
   	    	if (password != null && password.length() > 0){
   	   	    	String newPassword = new PasswordBusiness(password).getMD5();
   	   	    	if (!newPassword.equals(user.getPassword())) {
   	   	    		user.setPassword(newPassword);
   	   	    	}
   	    	}
   	    	if (salutation!= null && salutation.length() > 0 && !salutation.equals(user.getSalutation())){
   	    		user.setSalutation(salutation);
   	    	}
   	    	
   	    	if (firstName!= null && firstName.length() > 0 && !firstName.equals(user.getName()))  {
   	    		user.setName(firstName);
   	    	}
   	    	  	    	
   	    	if (email!= null && email.length() > 0 && !email.equals(user.getEmail())) {
   	    		user.setEmail(email);
   	    	}
   	    	if (birthdate!= null) {
   	    		user.setBirthdate(Constants.BIRTH_DATE_FORMAT.format(birthdate));
   	    	}
   	    	em.flush();
    		em.getTransaction().commit();
    	} catch (NotExistsUserException er) {
    		throw er;
    	}catch (BadEmailException er) {
    		throw er;
    	} catch (Exception e) {
    		System.out.println("EXCEPCION!\n" + e.getMessage());
    		e.printStackTrace();
    		result = false;
		}
    	return result;
    }
    public void updateUser(User user) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        //em.persist(user);
	        
	        em.merge(user);
	        em.flush();
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
    
    @SuppressWarnings("unchecked")
	public boolean isValidUserAndPassword(String login, String currentPassword) throws InvalidUserAndPasswordException
    {
    	boolean result = false;
    	try {
    		em.getTransaction().begin();
    		String currentPasswordHash = new PasswordBusiness(currentPassword).getMD5();
    		Query query = em.createNamedQuery("User.findByLoginNPassword");
    		query.setParameter("login", login);
    		query.setParameter("password",currentPasswordHash);
   			List<User> userList = query.getResultList();
   			if (userList.size() > 0) {
   				result = true;
   			} else {
   				throw new InvalidUserAndPasswordException(EjbConstants.MESSAGE_NOT_VALID_USER_AND_PASSWORD); 
   			}
    		em.getTransaction().commit();
    	} catch (InvalidUserAndPasswordException er) {
    		throw er;
    	} catch (NoSuchAlgorithmException er) {
    		throw new InvalidUserAndPasswordException(EjbConstants.MESSAGE_APPLICATION_ERROR  + " Mensaje: " + er.getMessage());
    	}catch (Exception er) {
    		er.printStackTrace();
    		throw new InvalidUserAndPasswordException(EjbConstants.MESSAGE_UNKNOWN_ERROR + " : " + er.getMessage());
    	}
    	return result;
    }
    @SuppressWarnings("unchecked")
    public List<UserType> getAllUserTypes() {
    	List<UserType> userTypes = new ArrayList<UserType>();
    	Query query = em.createNamedQuery("userType.findAll");
    	userTypes = query.getResultList();
    	return userTypes;
    }
    @SuppressWarnings("unchecked")
    public List<UserType> getUserTypes(boolean admin, int status) {
    	List<UserType> userTypes = new ArrayList<UserType>();
    	Query query = em.createNamedQuery("userType.findByCriteria");
		query.setParameter("admin", (admin ? "S" : "N"));
		query.setParameter("status", (status == 1 ? "1" : "0"));
		userTypes = query.getResultList();
    	return userTypes;
    }
    public UserType getUserType(int type) throws NotExistsDepartmentException {
    	UserType userType = null;
    	try{
	    	userType = (UserType) em.find(UserType.class,type);
    	}catch (Exception ex){
    		userType = null;
    	}
    	if (userType == null) 
    		throw new NotExistsDepartmentException(EjbConstants.MESSAGE_NOT_EXISTS);
    	
    	return userType;
    }
       
    public boolean updateContact(String login, int pais, int ciudad, String sector, String calle, String numero, String apto, String telefono, String celular) {
    	try {
    		User user = null;
			em.getTransaction().begin();
			Query query = em.createNamedQuery("User.findByLogin");
			query.setParameter("login", login);
			List<User> userList = query.getResultList();
			if (userList.size() > 0) {
	    		user = (User)userList.get(0);
	    		Contact contact = user.getContact();
	    		if (!(pais+"").equals(contact.getCountry())){
	 	        	contact.setCountry(pais+"");
	 	        }
	 	        if (!(ciudad+"").equals(contact.getCity())){
	 	        	contact.setCity(ciudad+"");
	 	        }
	 	        if (sector != null && !sector.equals(contact.getSector())){
	 	        	contact.setSector(sector);
	 	        }
	 	        if (calle != null && !calle.equals(contact.getStreet())){
	 	        	contact.setStreet(calle);
	 	        }
	 	        if (numero != null && !numero.equals(contact.getNumber())){
	 	        	contact.setNumber(numero);
	 	        }
	 	        if (apto != null && !apto.equals(contact.getApto())){
	 	        	contact.setApto(apto);
	 	        }
	 	        if (telefono != null && !telefono.equals(contact.getTelephone())){
	 	        	contact.setTelephone(telefono);
	 	        }
	 	        if (celular != null && !celular.equals(contact.getCell())){
	 	        	contact.setCell(celular);
	 	        }
			} else {
				throw new Exception();
			}
			em.flush();
    		em.getTransaction().commit();
	        
    	} catch (Exception ex) {
    		System.out.println("[EXCEPCION]!\n" + ex.getMessage());
    		ex.printStackTrace();
    		return false;
    	}
        return true;
    }
    
    private Contact getContact(int userId) throws NotExistsUserException
    {
    	Contact contact = null;
		try {
			contact = getUser(userId).getContact();
		} catch (Exception ex) {
			throw new NotExistsUserException(EjbConstants.MESSAGE_NO_USER_EXISTS);
		}
    	return contact;
    }
//  public boolean createContact(User user, String pais, String ciudad, String sector, String calle, String numero, String apto, String telefono, String celular) {
//	try {
//        em.getTransaction().begin();
//        Contact e = new Contact();
//        
//        e.setUserId(user.getUserId());
//        e.setPais(pais);
//        e.setCiudad(ciudad);
//        e.setSector(sector);
//        e.setCalle(calle);
//        e.setNumero(numero);
//        e.setApto(apto);
//        e.setTelefono(telefono);
//        e.setCelular(celular);
//        
//        user.setContact(e);
//        
//        em.persist(user);
//        em.flush();
//        em.getTransaction().commit();
//	} catch (Exception ex) {
//		System.out.println("[EXCEPCION]!\n" + ex.getMessage());
//		ex.printStackTrace();
//		return false;
//	}
//    return true;
//}
    
    
    public void createUserSession(UserSession userSession) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        em.persist(userSession);
	        em.flush();
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
    public UserSession getUserSession(String serie, String token) throws NotExistsException
    {
    	UserSession userSession = null;
		try {
			Query query = em.createNamedQuery("UserSession.findByAll");
		//	query.setParameter("username", username);
			query.setParameter("serie", serie);
			query.setParameter("token", token);
			List<UserSession> confirmationList = query.getResultList();
			if (confirmationList.size() > 0) {
	    		userSession = confirmationList.get(0);
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(MailTemplate.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS + " " + ex.getMessage());
		}
    	return userSession;
    }
    
    public void updateUserSession(UserSession userSession) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        //em.persist(userSession);
	        //em.flush();
	        em.merge(userSession);
	        em.flush();
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
    
    public UserSession getUserSessionByUserId(String username) throws NotExistsException
    {
    	UserSession userSession = null;
		try {
			Query query = em.createNamedQuery("UserSession.findBySerie");
			query.setParameter("serie", new PasswordBusiness(username).getMD5());
			List<UserSession> confirmationList = query.getResultList();
			if (confirmationList.size() > 0) {
	    		userSession = confirmationList.get(0);
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(MailTemplate.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS + " " + ex.getMessage());
		}
    	return userSession;
    }
    
    public void deleteUserSession(UserSession userSession) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        em.remove(userSession);
	        em.flush();
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
}
