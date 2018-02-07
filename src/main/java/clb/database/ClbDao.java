package clb.database;

import java.util.List;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryAverageObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.database.entities.UsersystemEntity;

public interface ClbDao{

	void saveAnalyzer(AnalyzerObject analyzerObject);
	
	void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void saveAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryAverageObject);
	
	void saveDataLogger(DataLoggerObject dataLoggerObject);
	
	void saveBuilding(BuildingObject buildingObject);
	
	void saveUsersystem(UsersystemObject userSystemObject);
	
	void saveUsers(List<UsersystemObject> users);

    UsersystemEntity userCanLogin( String username, String password );

    UsersystemEntity findUserByToken( String token );
}
