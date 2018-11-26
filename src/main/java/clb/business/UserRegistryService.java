package clb.business;

import clb.business.objects.UsersystemObject;
import clb.global.exceptions.UserCantResendEmailException;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.global.exceptions.UserExistsOnRegistryException;
import clb.global.exceptions.UserNotPersistedException;
import clb.global.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.global.exceptions.UserTokenIsNullOnCompleteRegistrationException;

public interface UserRegistryService {
    
    UsersystemObject validateUserLogin( String userName , String password ) throws UserDoesNotExistException,UserDoesNotMatchPasswordLoginException;

    void registerUser( UsersystemObject user, int timeOfSession ) 
            throws UserExistsOnRegistryException, UserNotPersistedException;

    UsersystemObject completeUserRegistration(String token) 
            throws UserTokenIsNullOnCompleteRegistrationException,
                   UserTokenHasExpiredOnCompleteRegistration;

    void makeNewUserRegistration( String username, int nrOfMinutesNecessaryToResend ) throws UserDoesNotExistException, UserCantResendEmailException;

    void resendEmail( String username, int nrOfMinutesNecessaryToResend ) throws UserDoesNotExistException, UserCantResendEmailException;
}
