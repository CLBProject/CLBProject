package clb.database;

import java.util.List;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryAverageObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;

public interface ClbDao{

	void saveAnalyzer(AnalyzerObject analyzerObject);
	
	void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void saveAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryAverageObject);
	
	void saveDataLogger(DataLoggerObject dataLoggerObject);
	
	void saveBuilding(BuildingObject buildingObject);
	
	void saveUsersystem(UsersystemObject userSystemObject);
	
	void saveUsers(List<UsersystemObject> users);

    UsersystemObject findUserByToken( String token );

    UsersystemObject findUserByUserName( String userName );

    List<BuildingObject> findUserBuildings( String userName );
}
