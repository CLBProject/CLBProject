package clb.business;

import java.io.IOException;

import clb.business.objects.UsersystemObject;

public interface AnalyzerDataService {
	
    public void persistDataForUser(String username) throws IOException;
    
    public UsersystemObject getUserData(String username);
}
