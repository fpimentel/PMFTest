package com.pmf.ejbs.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.pmf.entities.City;
import com.pmf.entities.MailTemplate;
import com.pmf.entities.Country;
import com.pmf.entities.UserMailConfirmation;
import com.pmf.entities.UserSession;
import com.pmf.exceptions.AutoException;
import com.pmf.exceptions.NotExistsException;
import com.pmf.util.EjbConstants;
import com.pmf.util.JPAUtil;

/**
 * Session Bean implementation class UserBean
 * @author Garis M. Suero
 * 06/05/2010
 */
@Stateless(name="BussinesHelper", mappedName="ejb/BussinesHelperJNDI")
public class BusinessHelper implements BusinessHelperLocal {

	@PersistenceUnit(name="myAutoService")
	public EntityManager em;

    public BusinessHelper() {
    	em = new JPAUtil().getEMF().createEntityManager();
    }
    
    public MailTemplate getMailTemplate(String name) throws NotExistsException
    {
    	MailTemplate template = null;
		try {
			Query query = em.createNamedQuery("MailTemplate.findByName");
			query.setParameter("name", name);
			List<MailTemplate> templateList = query.getResultList();
			if (templateList.size() > 0) {
	    		template = templateList.get(0);
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(MailTemplate.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS + " " + name + " " + ex.getMessage());
		}
    	return template;
    }
    
    public void createMailConfirmation(UserMailConfirmation mailConfirmation) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        em.persist(mailConfirmation);
	        em.flush();
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
    public UserMailConfirmation getMailConfirmation(String email, String confirmationCode) throws NotExistsException
    {
    	UserMailConfirmation confirmation = null;
		try {
			Query query = em.createNamedQuery("userMailConfirmation.findByEmailNConfirmationCode");
			query.setParameter("email", email);
			query.setParameter("confirmationcode", confirmationCode);
			List<UserMailConfirmation> confirmationList = query.getResultList();
			if (confirmationList.size() > 0) {
	    		confirmation = confirmationList.get(0);
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(MailTemplate.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS + " " + email + " " + ex.getMessage());
		}
    	return confirmation;
    }
    
    public void updateMailConfirmation(UserMailConfirmation mailConfirmation) throws AutoException {
    	try {
	        em.getTransaction().begin();
	        em.persist(mailConfirmation);
	        em.flush();
	        em.merge(mailConfirmation);
	        em.getTransaction().commit();
    	}  catch (NullPointerException ex) {
    		throw new AutoException ("Algo anda mal, no hay entity manager");   		
    	} catch (Exception ex) {
    		throw new AutoException ("Algo anda mal : " + ex.getMessage());
    	}
    }
    
    public List<Country> getCountries() throws NotExistsException
    {
		try {
			Query query = em.createNamedQuery("Pais.findAll");
			List<Country> paisList = query.getResultList();
			if (paisList.size() > 0) {
	    		return paisList;
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(Country.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS);
		}
    }
    
    public Country getCountry(int paisid) throws NotExistsException
    {
    	Country pais = null;
		try {
			Query query = em.createNamedQuery("Pais.findByCode");
			query.setParameter("paisid", paisid);
			List<Country> paisList = query.getResultList();
			if (paisList.size() > 0) {
	    		pais = paisList.get(0);
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(Country.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS + " " +ex.getMessage());
		}
		return pais;
    }
    
    public List<City> getCiudades(Country pais) throws NotExistsException
    {
		try {
			Query query = em.createNamedQuery("ciudad.findByCountry");
			query.setParameter("pais", pais);
			List<City> ciudadList = query.getResultList();
			if (ciudadList.size() > 0) {
				return ciudadList;
			} else {
				throw new Exception(" no existen ");
			}
		} catch (Exception ex) {
			throw new NotExistsException(City.class.getName() + " " + EjbConstants.MESSAGE_NOT_EXISTS);
		}
    }
}
