package clb.database;

import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;

public interface ClbDao{

	void saveAnalyzer(AnalyzerObject analyzerObject);
	
    void saveAnalyzerRegistries( List<AnalyzerRegistryObject> analyzersRegistries );
	
	void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void saveDataLogger(DataLoggerObject dataLoggerObject);
	
	void saveBuilding(BuildingObject buildingObject);
	
	void saveBuildingMeter(BuildingMeterObject buildingMeterObject);
	
	void saveUsersystem(UsersystemObject userSystemObject);
	
	void saveUsers(List<UsersystemObject> users);

    UsersystemObject findUserByToken( String token );

    UsersystemObject findUserByUserName( String userName );

    List<BuildingObject> findUserBuildings( String userName );

    List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId , Date timeFrame);
    
    List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId, Date timeFrame );

}
