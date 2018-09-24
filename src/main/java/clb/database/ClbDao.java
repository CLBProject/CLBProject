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
	
	List<UsersystemObject> getAllUsers();
	
	List<BuildingObject> getAllBuildings();

	List<DataLoggerObject> getAllDataLoggers();
	
	List<AnalyzerObject> getAllAnalyzers();

    UsersystemObject findUserByToken( String token );

    UsersystemObject findUserByUserName( String userName );

    List<BuildingObject> findUserBuildings( String userName );

    List<AnalyzerRegistryObject> getDayHourRegistriesFromAnalyzer( String analyzerId, Date from, Date to );
    
	List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);

	List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);
	
	Date getLowestAnalyzerRegistryDate();

	String[] getYearsAvailable();

	Long getLatestDateForAnalyzer(String analyzerCodeName);

	BuildingObject getBuildingByName(String buildingName);

	

}
