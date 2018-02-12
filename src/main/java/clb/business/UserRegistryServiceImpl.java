package clb.business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

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
    public BCryptPasswordEncoder passwordEncoder;

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
    public void registerUser( UsersystemObject user, int timeOfSession, Locale requestLocale, String requestContextPath ) 
            throws UserExistsOnRegistryException, UserNotPersistedException{

        //Check if user already exists
        if(clbDao.findUserByUserName(user.getUsername()) != null)
            throw new UserExistsOnRegistryException();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, timeOfSession);
        user.setExpiryDate(cal.getTime());

        user.setPassword( passwordEncoder.encode( user.getPassword() ) );

        clbDao.saveUsersystem( user );

        if(user.getUserid() == null)
            throw new UserNotPersistedException();

        eventPublisher.publishEvent(new UserRegistryOnCompleteEvent(user, requestLocale, requestContextPath));
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
