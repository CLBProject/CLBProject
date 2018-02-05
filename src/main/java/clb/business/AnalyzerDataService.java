package clb.business;

import java.io.IOException;

import clb.business.objects.ClbObject;
import clb.business.objects.UsersystemObject;

public interface AnalyzerDataService {
    
	public void persistObject(ClbObject userObject);
	
    public void persistScriptBigData() throws IOException;
    
    public UsersystemObject userCanLogin(String userName, String password);
}
