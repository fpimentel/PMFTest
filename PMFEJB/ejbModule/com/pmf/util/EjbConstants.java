package com.pmf.util;

import com.pmf.commons.Constants;

public interface EjbConstants {

	// tables
	public final String TABLE_USER_TYPES = "USER_TYPES";
	public final String TABLE_CONTACTS = "CONTACTS";
	public final String TABLE_USERS = "USERS";
	public final String TABLE_USER_CONFIRMATION = "user_confirmations";
	public final String TABLE_MAIL_TEMPLATES = "mail_templates";
	public final String TABLE_USER_SESSION = "user_session";
	public final String TABLE_PAIS = "countries";
	public final String TABLE_CIUDAD = "cities";

	
	// messages
	public final String MESSAGE_NO_DATA_FOUND = "No se encontraron datos";
	public final String MESSAGE_NO_USER_EXISTS = "Usuario inexistente";
	public final String MESSAGE_NOT_EXISTS = " inexistente";
	public final String MESSAGE_NOT_VALID_USER_AND_PASSWORD = "Usuario o contrase"+Constants.CHAR_UNICODE_TILDE_n+"a inv"+Constants.CHAR_UNICODE_ACUTE_a+"lida";
	public final String MESSAGE_UNKNOWN_ERROR = "Error desconocido";
	public final String MESSAGE_APPLICATION_ERROR = "Error en la aplicacion: ";
	
	
}
