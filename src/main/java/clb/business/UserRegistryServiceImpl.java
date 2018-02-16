package clb.business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.exceptions.UserDoesNotExistOnLoginException;
import clb.business.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.business.exceptions.UserExistsOnRegistryException;
import clb.business.exceptions.UserNotFoundByTokenOnCompleteRegistration;
import clb.business.exceptions.UserNotPersistedException;
import clb.business.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.business.exceptions.UserTokenIsNullOnCompleteRegistrationException;
import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.global.PasswordGenerator;

@Service
public class UserRegistryServiceImpl implements UserRegistryService, Serializable{

    /** 
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClbDao clbDao;

    @Autowired 
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    private PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
            .useDigits(true)
            .useLower(true)
            .useUpper(true)
            .build();

    private static final int PASSWORD_GEN_LENGTH = 10;
    
    @Override
    @Transactional
    public void validateUserLogin( String userName , String password) throws UserDoesNotExistOnLoginException, UserDoesNotMatchPasswordLoginException{
        if(userName != null && password != null) {
            UsersystemObject userObject = clbDao.findUserByUserName(userName);

            if (userObject == null)
                throw new UserDoesNotExistOnLoginException();

            if(!passwordEncoder.matches( password , userObject.getPassword())) {
                throw new UserDoesNotMatchPasswordLoginException();
            }
        }

    }

    @Override
    @Transactional
    public void registerUser( UsersystemObject user, int timeOfSession) 
            throws UserExistsOnRegistryException, UserNotPersistedException{

        //Check if user already exists
        if(clbDao.findUserByUserName(user.getUsername()) != null)
            throw new UserExistsOnRegistryException();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, timeOfSession);
        user.setExpiryDate(cal.getTime());

        user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        user.setToken( generateUserToken() );
        
        clbDao.saveUsersystem( user );

        if(user.getUserid() == null)
            throw new UserNotPersistedException();

        String subject = "Registration Complete";
        
        String textMsg = "Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token=" + user.getToken();
        
        eventPublisher.publishEvent(new UserRegistrySendEmailEvent(user.getUsername(), subject, textMsg));
    }

    @Override
    @Transactional
    public UsersystemObject completeUserRegistration(String token) throws UserTokenIsNullOnCompleteRegistrationException,
    UserNotFoundByTokenOnCompleteRegistration, 
    UserTokenHasExpiredOnCompleteRegistration{
        //Token is null
        if(token == null) {
            throw new UserTokenIsNullOnCompleteRegistrationException();
        }

        UsersystemObject userObject = clbDao.findUserByToken(token);

        if (userObject == null || userObject.getToken() == null) {
            throw new UserNotFoundByTokenOnCompleteRegistration();
        }

        Calendar cal = Calendar.getInstance();
        if ((userObject.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new UserTokenHasExpiredOnCompleteRegistration();
        }
        //Persist enabled user
        userObject.setEnabled(true);
        clbDao.saveUsersystem( userObject );

        return userObject;
    }

    @Override
    public void makeNewUserRegistration( String username ) throws UserDoesNotExistOnLoginException{
        
        UsersystemObject user = clbDao.findUserByUserName( username );
        
        if (user == null)
            throw new UserDoesNotExistOnLoginException();
        
        String newPassword = passwordGenerator.generate( PASSWORD_GEN_LENGTH );
        String newToken = generateUserToken();
        
        user.setPassword( passwordEncoder.encode( newPassword ) );
        user.setToken( newToken );
        
        clbDao.saveUsersystem( user );
        
        String subject = "Recover Password";
        
        String textMsg = "A request was made in order to get a new Password. The New Password is: '" + newPassword + "'.\n\n Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token="+newToken;
        
        eventPublisher.publishEvent(new UserRegistrySendEmailEvent(username, subject, textMsg));
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

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher( ApplicationEventPublisher eventPublisher ) {
        this.eventPublisher = eventPublisher;
    }
}
