package clb.business;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.integration.FtpGateway;
import clb.business.integration.FtpGatewayPut;
import clb.business.objects.UserEvent;
import clb.business.objects.UsersystemObject;
import clb.business.utils.PasswordGenerator;
import clb.database.ClbDao;
import clb.global.exceptions.UserCantResendEmailException;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.global.exceptions.UserExistsOnRegistryException;
import clb.global.exceptions.UserIsNotEnabledYet;
import clb.global.exceptions.UserNotPersistedException;
import clb.global.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.global.exceptions.UserTokenIsNullOnCompleteRegistrationException;

@Service
public class UserRegistryServiceImpl implements UserRegistryService, Serializable{

    /** 
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClbDao clbDao;


    @Autowired
    UserRegistryServicePublisherEvent userEventPublisher;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	FtpGatewayPut ftpGatewayPut;

    private PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
            .useDigits(true)
            .useLower(true)
            .useUpper(true)
            .build();


    @Override
    @Transactional
    public UsersystemObject validateUserLogin( String userName , String password) throws UserDoesNotExistException, UserDoesNotMatchPasswordLoginException, UserIsNotEnabledYet{
        
        UsersystemObject userObject = null;
        
        if(userName != null && password != null) {
            userObject = clbDao.findUserByUserName(userName);

            if (userObject == null)
                throw new UserDoesNotExistException();

            if(!passwordEncoder.matches( password , userObject.getPassword())) {
                throw new UserDoesNotMatchPasswordLoginException();
            }
            
            if(!userObject.isEnabled()) {
            	throw new UserIsNotEnabledYet();
            }
        }
        
        return userObject;
    }

    @Override
    @Transactional
    public void registerUser( String name, String userName, String address, String password, int timeOfSession) 
            throws UserExistsOnRegistryException, UserNotPersistedException{
    	
        //Check if user already exists
        if(clbDao.findUserByUserName(userName) != null)
            throw new UserExistsOnRegistryException();

        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.add(Calendar.MINUTE, timeOfSession);
        
        UsersystemObject user = new UsersystemObject();
        user.setExpiryDate(cal.getTime());
        
        user.setName( name );
        user.setId( userName );
        user.setAddress( address );
        user.setPassword( passwordEncoder.encode( password ) );
        user.setToken( generateUserToken() );
        
        clbDao.saveClbObject( user );

        if(user.getId() == null)
            throw new UserNotPersistedException();

        ftpGatewayPut.upload(userName, "",  "");
        
        String subject = "Registration Complete";

        String textMsg = "Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token=" + user.getToken();
        
        userEventPublisher.publishEvent(new UserEvent(user.getId(), subject, textMsg));
        
        
    }

    @Override
    @Transactional
    public UsersystemObject completeUserRegistration(String token) throws UserTokenIsNullOnCompleteRegistrationException,
    UserTokenHasExpiredOnCompleteRegistration{
        //Token is null
        if(token == null) {
            throw new UserTokenIsNullOnCompleteRegistrationException();
        }

        UsersystemObject userObject = clbDao.findUserByToken( token );

        if (userObject == null || userObject.getToken() == null || userObject.hasExpiredDate(new Date())) {
            throw new UserTokenHasExpiredOnCompleteRegistration();
        }

        //Persist enabled user
        userObject.setEnabled(true);
        clbDao.saveClbObject( userObject );

        return userObject;
    }

    @Override
    public void makeNewUserRegistration( String username, int nrOfMinutesNecessaryToResend) throws UserDoesNotExistException, UserCantResendEmailException{

        UsersystemObject user = clbDao.findUserByUserName( username );

        if (user == null)
            throw new UserDoesNotExistException();
        
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.MINUTE, - nrOfMinutesNecessaryToResend );
        
        if(user.emailSentItHasntAlreadyPassedEnoughTimeSince( cal.getTime() )) {
            throw new UserCantResendEmailException();
        }

        String newPassword = passwordGenerator.generate( );
        String newToken = generateUserToken();

        user.setPassword( passwordEncoder.encode( newPassword ) );
        user.setToken( newToken );
        user.setLastSentEmail( new Date() );
        
        clbDao.saveClbObject( user );

        String subject = "Recover Password";

        String textMsg = "A request was made in order to get a new Password. The New Password is: '" + newPassword + "'.\n\n Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token="+newToken;

        userEventPublisher.publishEvent(new UserEvent(username, subject, textMsg));
    }

	@Override
    public void resendEmail( String username , int nrOfMinutesNecessaryToResend) throws UserDoesNotExistException, UserCantResendEmailException {
        UsersystemObject user = clbDao.findUserByUserName( username );

        if (user == null) {
            throw new UserDoesNotExistException();
        }
        
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.MINUTE, - nrOfMinutesNecessaryToResend );
        
        
        if(user.emailSentItHasntAlreadyPassedEnoughTimeSince( cal.getTime() )) {
            throw new UserCantResendEmailException();
        }
        
        String newToken = generateUserToken();
        user.setToken( newToken );
        user.setLastSentEmail( new Date() );
        clbDao.saveClbObject( user );
        
        
        String subject = "Registration Complete";

        String textMsg = "Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token=" + user.getToken();
        
        userEventPublisher.publishEvent(new UserEvent(user.getId(), subject, textMsg));
    }
	
    private String generateUserToken() {
        return UUID.randomUUID().toString();
    }

    public ClbDao getClbDao() {
        return clbDao;
    }

    public void setClbDao(ClbDao clbDao) {
        this.clbDao = clbDao;
    }

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordGenerator getPasswordGenerator() {
		return passwordGenerator;
	}

	public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	public UserRegistryServicePublisherEvent getUserEventPublisher() {
		return userEventPublisher;
	}

	public void setUserEventPublisher(UserRegistryServicePublisherEvent userEventPublisher) {
		this.userEventPublisher = userEventPublisher;
	}
	
}
