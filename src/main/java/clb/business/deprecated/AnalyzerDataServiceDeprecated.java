package clb.business.deprecated;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.UsersystemObject;

public interface AnalyzerDataServiceDeprecated {
	
    public void persistDataForUser(String username) throws IOException;

}
