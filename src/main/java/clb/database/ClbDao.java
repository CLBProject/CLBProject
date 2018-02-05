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

	void insertAnalyzer(AnalyzerObject analyzerObject);
	
	void insertAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void insertAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryAverageObject);
	
	void insertDataLogger(DataLoggerObject dataLoggerObject);
	
	void insertBuilding(BuildingObject buildingObject);
	
	void insertUsersystem(UsersystemObject userSystemObject);
	
	void insertUsers(List<UsersystemObject> users);

    UsersystemEntity userCanLogin( String username, String password );
}
