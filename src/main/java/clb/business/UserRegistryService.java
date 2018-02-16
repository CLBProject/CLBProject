package clb.business;

import clb.business.exceptions.UserDoesNotExistOnLoginException;
import clb.business.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.business.exceptions.UserExistsOnRegistryException;
import clb.business.exceptions.UserNotFoundByTokenOnCompleteRegistration;
import clb.business.exceptions.UserNotPersistedException;
import clb.business.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.business.exceptions.UserTokenIsNullOnCompleteRegistrationException;
import clb.business.objects.UsersystemObject;

public interface UserRegistryService {
    
    void validateUserLogin( String userName , String password ) throws UserDoesNotExistOnLoginException,UserDoesNotMatchPasswordLoginException;

    void registerUser( UsersystemObject user, int timeOfSession ) 
            throws UserExistsOnRegistryException, UserNotPersistedException;

    UsersystemObject completeUserRegistration(String token) 
            throws UserTokenIsNullOnCompleteRegistrationException,
                   UserNotFoundByTokenOnCompleteRegistration, 
                   UserTokenHasExpiredOnCompleteRegistration;

    void makeNewUserRegistration( String username ) throws UserDoesNotExistOnLoginException;
}
