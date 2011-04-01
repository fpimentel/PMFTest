package com.pmf.web.action.user;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;
import com.pmf.commons.Constants;
import com.pmf.exceptions.AutoException;
import com.pmf.exceptions.NotExistsException;
import com.pmf.web.util.UserConfirmationHelper;

public class ConfirmEmail extends ActionSupport implements ServletRequestAware  {
	private static final long serialVersionUID = 1L;
	//private HashMap<String, String[]> parameters;
	private HttpServletRequest request;
	private String confirmationCode;
	private String emailAddress;

	public String execute() throws Exception {
		
		try {
			String confirmationCodeRequested = getRequest().getParameter("confirmationCode");
			String emailAddressRequested = getRequest().getParameter("email");
			
			if (confirmationCodeRequested != null && confirmationCodeRequested.length() > 10 
						&& emailAddressRequested != null && emailAddressRequested.length() > 0) {
				UserConfirmationHelper mailConfirmation = new UserConfirmationHelper(confirmationCodeRequested, emailAddressRequested);
				mailConfirmation.confirmMail();
				setConfirmationCode(confirmationCodeRequested);
				setEmailAddress(emailAddressRequested);
			} else {
				throw new AutoException();
			}
			
		} catch(NotExistsException ex) {
			addActionError("Confirmaci"+Constants.CHAR_UNICODE_ACUTE_o+"n inv"+Constants.CHAR_UNICODE_ACUTE_a+"lida.");
			return ERROR;
		} catch(AutoException ex) {
			addActionError(ex.getMessage());
			return ERROR;
		} catch (Exception ex){
			addActionError("Ocurrieron errores, validando su correo." + ex.getMessage());
			return ERROR;			
		}
		addActionMessage("Confirmacion exitosa");
		return SUCCESS;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request =  request;
		
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
