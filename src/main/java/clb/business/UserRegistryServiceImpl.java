package clb.business;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.global.PasswordGenerator;
import clb.global.exceptions.UserCantResendEmailException;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.global.exceptions.UserExistsOnRegistryException;
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
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
            .useDigits(true)
            .useLower(true)
            .useUpper(true)
            .build();


    @Override
    @Transactional
    public UsersystemObject validateUserLogin( String userName , String password) throws UserDoesNotExistException, UserDoesNotMatchPasswordLoginException{
        
        UsersystemObject userObject = null;
        
        if(userName != null && password != null) {
            userObject = clbDao.findUserByUserName(userName);

            if (userObject == null)
                throw new UserDoesNotExistException();

            if(!passwordEncoder.matches( password , userObject.getPassword())) {
                throw new UserDoesNotMatchPasswordLoginException();
            }
        }
        
        return userObject;
    }

    @Override
    @Transactional
    public void registerUser( UsersystemObject user, int timeOfSession) 
            throws UserExistsOnRegistryException, UserNotPersistedException{

        //Check if user already exists
        if(clbDao.findUserByUserName(user.getUsername()) != null)
            throw new UserExistsOnRegistryException();

        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.add(Calendar.MINUTE, timeOfSession);
        user.setExpiryDate(cal.getTime());
        
        user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        user.setToken( generateUserToken() );
        user.setFtpPassword( passwordEncoder.encode( passwordGenerator.generate()));
        
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
        clbDao.saveUsersystem( userObject );

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
        
        clbDao.saveUsersystem( user );

        String subject = "Recover Password";

        String textMsg = "A request was made in order to get a new Password. The New Password is: '" + newPassword + "'.\n\n Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token="+newToken;

        eventPublisher.publishEvent(new UserRegistrySendEmailEvent(username, subject, textMsg));
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
        
        user.setLastSentEmail( new Date() );
        clbDao.saveUsersystem( user );
        
        
        String subject = "Registration Complete";

        String textMsg = "Access Link @ http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                "/pages/registerComplete.xhtml?token=" + user.getToken();
        
        eventPublisher.publishEvent(new UserRegistrySendEmailEvent(user.getUsername(), subject, textMsg));
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
