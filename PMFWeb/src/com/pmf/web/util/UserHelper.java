package com.pmf.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmf.commons.Constants;
import com.pmf.ejbs.user.UserBean;
import com.pmf.ejbs.user.UserBeanLocal;
import com.pmf.entities.User;
import com.pmf.exceptions.AutoException;
import com.pmf.exceptions.NotAuthorizedException;
import com.pmf.exceptions.NotExistsException;
import com.pmf.web.session.SessionHelper;
import com.pmf.web.session.UserSession;

public class UserHelper {

	public static boolean authenticateUser(HttpServletRequest request,
			String login, String password) {
		boolean authenticated =  false;
		try {
			UserBeanLocal userBean = new UserBean();
			if (userBean.isValidUserAndPassword(login, password)) {
				User user = userBean.getUser(login);
				request.getSession().setAttribute(Constants.GLOBAL_USER_SESSION_KEY, getUserSession(user, request));
				authenticated = true;
			}
		} catch (Exception ex) {
			authenticated = false;
		}

		return authenticated;
	}
	
	public static boolean authenticateUser(HttpServletRequest request,String login) {
		boolean retorno = false;
		try {
			UserBeanLocal userBean = new UserBean();
			User user = userBean.getUser(login);
			if (user != null && user.getStatus() == Constants.USER_STATUS_ACTIVE) {
				request.getSession().setAttribute(Constants.GLOBAL_USER_SESSION_KEY, getUserSession(user, request));
				retorno = true;
			}
		} catch (Exception ex) {
			retorno = false;
		}

		return retorno;
	}
	
	private static UserSession getUserSession(User user, HttpServletRequest request) {
		UserSession userSession = new UserSession();
		userSession.setAdmin(Constants.GLOBAL_YES_RESPONSE.equalsIgnoreCase(user.getUserType().getAdmin() + ""));
		userSession.setEmail(user.getEmail());
		userSession.setFirstName(user.getFirstName());
		userSession.setLastName(user.getLastName());
		userSession.setLogin(user.getLogin());
		userSession.setSalutation(user.getSalutation());
		userSession.setSessionId(request.getSession().getId());
		return userSession;
	}
	
	public static boolean isUserAuthenticated(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
	    HttpSession session =  request.getSession ();

		//Object request = invocation.getInvocationContext().;
		if(session.getAttribute(Constants.GLOBAL_USER_SESSION_KEY) == null) {
			try {
				SessionHelper helper = new SessionHelper(request, response);
				if(!helper.imRemembered()) {
					return false;
				} else {
					try { 
						helper.logUserRemembered();
					} catch (Exception ex) {
						throw new NotAuthorizedException("No existe ningun usuario.");
					}
				}
			} catch (NotExistsException ex) {
				throw new NotAuthorizedException("No existe ningun usuario.");
			}
		}
		return true;
		
	}
}
