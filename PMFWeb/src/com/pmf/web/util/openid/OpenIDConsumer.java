package com.pmf.web.util.openid;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;

public class OpenIDConsumer {
	public static ConsumerManager manager = null;
	
	   public static ConsumerManager getInstance() throws ConsumerException
	    {
		   if (manager == null)
			   manager = new ConsumerManager();
		   
		   return manager;
	    }
}
