package clb.business;

import java.io.IOException;
import java.util.Locale;

import clb.business.exceptions.UserExistsException;
import clb.business.exceptions.UserNotPersistedException;
import clb.business.objects.ClbObject;
import clb.business.objects.UsersystemObject;

public interface AnalyzerDataService {
    
	public void saveObject(ClbObject userObject);
	
    public void persistScriptBigData() throws IOException;
    
    public UsersystemObject userCanLogin(String userName, String password);
    
    public UsersystemObject getUsersystemByToken( String token );

    public void publishEvent( UsersystemObject object, Locale requestLocale, String requestContextPath );

    public void registerUser( UsersystemObject object ) throws UserExistsException, UserNotPersistedException;
}
