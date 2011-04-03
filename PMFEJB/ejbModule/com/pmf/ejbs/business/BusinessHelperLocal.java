package com.pmf.ejbs.business;

import java.util.List;

import javax.ejb.Local;

import com.pmf.entities.City;
import com.pmf.entities.MailTemplate;
import com.pmf.entities.Country;
import com.pmf.entities.UserMailConfirmation;
import com.pmf.exceptions.AutoException;
import com.pmf.exceptions.NotExistsException;

@Local
public interface BusinessHelperLocal {
    public MailTemplate getMailTemplate(String name) throws NotExistsException;
    public void createMailConfirmation(UserMailConfirmation mailConfirmation) throws AutoException;
    public UserMailConfirmation getMailConfirmation(String email, String confirmationCode) throws NotExistsException;
    public void updateMailConfirmation(UserMailConfirmation mailConfirmation) throws AutoException;
    public List<Country> getCountries() throws NotExistsException;
    public Country getCountry(int paisid) throws NotExistsException;
    public List<City> getCiudades(Country pais) throws NotExistsException;
}
