package com.pmf.web.util;

import com.pmf.ejbs.user.UserBean;
import com.pmf.ejbs.user.UserBeanLocal;
import com.pmf.entities.User;
import com.pmf.exceptions.NotExistsUserException;

public class DatalayerUtil {

	//private static Log logger = LogFactory.getLog(DatalayerUtil.class);
	
	public static User getUser(String login) {
		UserBeanLocal userBean = new UserBean();
		User user= null;
		try {
			user = userBean.getUser(login);
		} catch (NotExistsUserException e) {
			e.printStackTrace();
		}
		return user;
		
	}
}
