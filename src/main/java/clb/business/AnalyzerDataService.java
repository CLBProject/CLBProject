package clb.business;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.UsersystemObject;

public interface AnalyzerDataService {
	
    public void persistDataForUser(String username) throws IOException;
    
    public UsersystemObject getUserData(String username);
    
    public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId , Date timeFrame);
    
    public List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId, Date timeFrame );
}
