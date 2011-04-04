package com.pmf.web.action.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;

import com.opensymphony.xwork2.ActionSupport;
import com.pmf.commons.Constants;
import com.pmf.web.session.SessionHelper;
import com.pmf.web.util.UserHelper;
import com.pmf.web.util.openid.OpenIDConsumer;

public class Login extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private String login;
	private String openidIdentifier;
	private String password;
	private boolean remember;
	private String simpleMessage;
	
	public String check() throws Exception {
		
		return SUCCESS;
	}
	public String openIDReturnURL() throws Exception {
		
		//boolean authenticated = UserHelper.authenticateUser(request, getLogin(), getPassword());
		
		ParameterList openidResp = new ParameterList(request.getParameterMap());
		DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("discovered");
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0)
		   receivingURL.append("?").append(request.getQueryString());
		ConsumerManager consumerManager = OpenIDConsumer.getInstance();
		VerificationResult verification = consumerManager.verify(receivingURL.toString(), openidResp, discovered);

		Identifier verified = verification.getVerifiedId();

		if (verified != null) {
			
			AuthSuccess authSuccess =
                (AuthSuccess) verification.getAuthResponse();
			String resp = "";
	        if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX))
	        {
	        	 
	            FetchResponse fetchResponse = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
	            resp = fetchResponse.getAttributeAliases().toString();
	            List emails = fetchResponse.getAttributeValues("email");
	            String email = (String) emails.get(0);
	            //TODO
	            // Buscar parametros a guardar en base de datos... Correo, nombre, etc....
	        }

        
        
			this.session = request.getSession(true);
			addActionMessage("Inicio de sesion correctamente " + resp);
//			if (isRemember()) {
//				SessionHelper helper = new SessionHelper(request, response);
//				helper.rememberMe(getLogin());
//				session.setAttribute(Constants.GLOBAL_REMEMBER_SESSION_KEY, true);
//			} else {
//				session.setAttribute(Constants.GLOBAL_REMEMBER_SESSION_KEY, false);
//			}
			session.setMaxInactiveInterval(18000);
			return SUCCESS;
		} else {
			addActionError("Usuario o contras"+Constants.CHAR_UNICODE_TILDE_n+"ena invalidos.");
			return ERROR;
		}
	}
	public String execute() throws Exception {
		setOpenidIdentifier(request.getParameter("openid_identifier") + "");
		if (getOpenidIdentifier() != null && getOpenidIdentifier().length() > 0) {
			try {
				ConsumerManager consumerManager = OpenIDConsumer.getInstance();
				List discoveries = consumerManager.discover(getOpenidIdentifier());
				DiscoveryInformation discovered = consumerManager.associate(discoveries);
				session.setAttribute("discovered", discovered);
				String openIdReturnURL = "http://localhost:8080/PMFWeb/user/ODoLogin.do";
				AuthRequest authReq = consumerManager.authenticate(discovered, openIdReturnURL);
				response.sendRedirect(authReq.getDestinationUrl(true));
			} catch (Exception ex) {
				addActionError(ex.getMessage());
				return ERROR;
			}
			return SUCCESS;
		} else {
			addActionError("Invalido...");
			return ERROR;
		}
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getSimpleMessage() {
		return simpleMessage;
	}

	public void setSimpleMessage(String simpleMessage) {
		this.simpleMessage = simpleMessage;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		this.session = request.getSession();
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	public String getOpenidIdentifier() {
		return openidIdentifier;
	}
	public void setOpenidIdentifier(String openidIdentifier) {
		this.openidIdentifier = openidIdentifier;
	}
	
	
}
