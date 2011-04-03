package com.pmf.web.util;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.pmf.commons.Constants;
import com.pmf.ejbs.business.BusinessHelper;
import com.pmf.ejbs.business.BusinessHelperLocal;
import com.pmf.entities.Country;
import com.pmf.exceptions.NotExistsException;
import com.pmf.web.util.lists.Ciudad;
import com.pmf.web.util.lists.LabelValueBean;

public class Util {
		private static ArrayList<com.pmf.web.util.lists.Ciudad> ciudades;
		private static Properties props;
		private static ArrayList<LabelValueBean> serviceTimes;
		private static HashMap<String, Integer> holidays;
		
		public static ArrayList<com.pmf.web.util.lists.Ciudad> getCiudades() throws NotExistsException {
			if (ciudades == null || ciudades.size() < 1) {
				BusinessHelperLocal helper = new BusinessHelper();
				Country pais = helper.getCountry(Constants.DEFAULT_COUNTRY);
				List<com.pmf.entities.City> cities = helper.getCiudades(pais);
				ciudades = new ArrayList<com.pmf.web.util.lists.Ciudad>();
				if (cities != null) {
					Iterator<com.pmf.entities.City> it = cities.iterator();
					while(it.hasNext()) {
						com.pmf.entities.City city = it.next();
						ciudades.add(new com.pmf.web.util.lists.Ciudad(city.getId()+"",city.getCity()));
					}
				}
				else {
					ciudades.add(new com.pmf.web.util.lists.Ciudad("1","Santo Domingo"));
				}
			}
			return ciudades;
		}
		public static String getCiudad(String ciudadId) throws NotExistsException {
			if (ciudades == null || ciudades.size() < 1) 
				getCiudades();
			
			String ciudadLabel = "";
			Iterator<Ciudad> it = ciudades.iterator();
			while(it.hasNext()) {
				Ciudad city = it.next();
				if (ciudadId.equalsIgnoreCase(city.getCityCode())) {
					ciudadLabel = city.getCityName();
					break;
				}
			}
			return ciudadLabel;
		}

		public static boolean isNumber(String val) {
			try {
				Integer.parseInt(val);
			} catch (Exception ex) {
				return false;
			}
			return true;
		}
		public static boolean isAnioValid(String anio) {
			if (anio != null && anio.length() > 0 && isNumber(anio) && anio.length() == 4) {
				return true;
			}
			return false;
		}
		
		public static boolean isSectorValid(String sector) {
			if (sector != null && sector.length() > 0 && sector.length() <= 255) {
				return true;
			}
			return false;
		}
		public static boolean isNumeroContacto(String sector) {
			if (sector != null && sector.length() > 0 && sector.length() <= 10) {
				return true;
			}
			return false;
		}
		public static boolean isCalleValid(String calle) {
			if (calle != null && calle.length() > 0 && calle.length() <= 100) {
				return true;
			}
			return false;
		}
		
		public static boolean isAptoValid(String apto) {
			if (apto != null && apto.length() > 0 && apto.length() <= 255) {
				return true;
			}
			return false;
		}
		
		public static boolean isFirstNameValid(String firstName) {
			if (firstName != null && firstName.length() > 0 && firstName.length() <= 60) {
				return true;
			}
			return false;
		}
		public static boolean isLastNameValid(String lasttName) {
			if (lasttName != null && lasttName.length() > 0 && lasttName.length() <= 60) {
				return true;
			}
			return false;
		}
		
		public static boolean isMiddleNameValid(String middleName) {
			if (middleName != null && middleName.length() == 1) {
				return true;
			}
			return false;
		}
		
		public static boolean isSalutationValid(String salutation) {
			if (salutation != null && salutation.length() <= 5) {
				return true;
			}
			return false;
		}
		
		public static boolean isValidDateStr(String date) {
			try {
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); // YYYY-MM-DD
					df.setLenient(false);   // this is important!
					df.parse(date);
			}
			catch (ParseException e) {
				return false;
			}
			catch (IllegalArgumentException e) {
				return false;
			}
			return true;
		}

		public static boolean isBirthDateValid(Date fecha) {
			long purgeTime = System.currentTimeMillis() - (Constants.MAX_AGE_IN_DAYS_BACK * 24 * 60 * 60 * 1000);
			return fecha.before(new Date(purgeTime));
		}
		public static boolean isBirthDateValid(String fecha) {
			if (fecha != null && fecha.length() > 0 && isValidDateStr(fecha)) {
				long purgeTime = System.currentTimeMillis() - (Constants.MAX_AGE_IN_DAYS_BACK * 24 * 60 * 60 * 1000);
				try {
					return DateFormat.getDateInstance(DateFormat.SHORT).parse(fecha).before(new Date(purgeTime));
				} catch (ParseException e) {
					return false;
				}
			}
			return false;
		}
		public static boolean isPasswordValid(String password, String confirmation) {
			if (password != null && confirmation != null && password.equalsIgnoreCase(confirmation)) {
				return true;
			}
			return false;
		}
		
		public static boolean isPasswordValid(String password) {
			if (password != null && password.length() <= Constants.TAMANO_MAXIMO_PASSWORD && password.length() >= Constants.TAMANO_MINIMO_PASSWORD) {
				return true;
			}
			return false;
		}
		
		public static boolean isReferalValid(String referak) {
			if (referak != null && referak.length() <= 255) {
				return true;
			}
			return false;
		}
		
		public static boolean isTelefonoValid(String phoneNumber){  
			boolean isValid = false;  
			/* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn 
			    ^\\(? : May start with an option "(" . 
			    (\\d{3}): Followed by 3 digits. 
			    \\)? : May have an optional ")" 
			    [- ]? : May have an optional "-" after the first 3 digits or after optional ) character. 
			    (\\d{3}) : Followed by 3 digits. 
			     [- ]? : May have another optional "-" after numeric digits. 
			     (\\d{4})$ : ends with four digits. 
			 
			         Examples: Matches following <a href="http://mylife.com">phone numbers</a>: 
			         (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890 
			 
			 */  
			 String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";  
			 CharSequence inputStr = phoneNumber;  
			 Pattern pattern = Pattern.compile(expression);  
			 Matcher matcher = pattern.matcher(inputStr);  
			 if(matcher.matches()){  
				 isValid = true;  
			 }  
			 return isValid;  
		}  
	  public static boolean isValidEmailAddress(String aEmailAddress){
		    if (aEmailAddress == null) return false;
		    boolean result = true;
		    try {
		      new InternetAddress(aEmailAddress);
		      if (!hasNameAndDomain(aEmailAddress) ) {
		        result = false;
		      }
		    }
		    catch (AddressException ex){
		      result = false;
		    }
		    return result;
		  }

		  private static boolean hasNameAndDomain(String aEmailAddress){
		    String[] tokens = aEmailAddress.split("@");
		    return tokens.length == 2 && (tokens[0] != null && tokens[0].length() > 0) && (tokens[1] != null && tokens[1].length() > 0) ;
		  }
		  
		public static boolean isNumeric(String number){  
			boolean isValid = false;  
			   /*Number: A numeric value will have following format: 
			            ^[-+]?: Starts with an optional "+" or "-" sign. 
			        [0-9]*: May have one or more digits. 
			       \\.? : May contain an optional "." (decimal point) character. 
			       [0-9]+$ : ends with numeric digit. 
			   */  
			     
			   //Initialize reg ex for numeric data.  
		   String expression = "^[-+]?[0-9]*\\.?[0-9]+$";  
		   CharSequence inputStr = number;  
		   Pattern pattern = Pattern.compile(expression);  
		   Matcher matcher = pattern.matcher(inputStr);  
		   if(matcher.matches()){  
			   isValid = true;  
		   }  
		   return isValid;  
	  }

		public static Properties getProps() {
			return props;
		}

		public static void setProps(Properties props) {
			Util.props = props;
		}
		public static String getProperty(String name) {
			if (getProps() == null) {
			    try {
			    	setProps(new Properties());
			        URL url = ClassLoader.getSystemResource("auto.properties");
			        props.load(url.openStream());

			    } catch (IOException e) {
			    }
			}
			return props.getProperty(name);
		}

		public static int[] convertStringArraytoIntArray(String[] sarray) throws Exception {
			if (sarray != null) {
				int intarray[] = new int[sarray.length];
				for (int i = 0; i < sarray.length; i++) {
					intarray[i] = Integer.parseInt(sarray[i]);
				}
				return intarray;
			}
			return null;
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//rounding a double number up to 2 decimal places
		public double RoundDoubleTo2DecimalPlaces(double Rval, int Rpl){
			
			double p = (double)Math.pow(10,Rpl);
			Rval = Rval * p;
			  double tmp = Math.round(Rval);
			  double roundedValue=(double)tmp/p;	
			
			return roundedValue;
		}
		
		
		//change string date to Date  
		public  Date changeStringtoDate(String dob) {
			Date fdob = null;
			try {
				fdob = sdf.parse(dob);
			} catch (ParseException e) {
			}
			return fdob;
		}
		//change  Date  to string date
		public  String changeDatetoString(Date dob) {
			String fdob = null;
			try {
				fdob = sdf.format(dob);
			} catch (Exception e) {
			}
			return fdob;
		}

}
