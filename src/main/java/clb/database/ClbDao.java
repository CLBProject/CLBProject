package clb.database;

import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerMeterObject;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;

public interface ClbDao{

	void saveAnalyzer(AnalyzerObject analyzerObject);
	
    void saveAnalyzerRegistries( List<AnalyzerRegistryObject> analyzersRegistries );
	
	void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void saveAnalyzerMeter(AnalyzerMeterObject buildingMeterObject);
	
	void saveBuilding(BuildingObject buildingObject);
	
	void saveUsersystem(UsersystemObject userSystemObject);
	
	void saveDivision(DivisionObject divisionObject);
	
	void saveUsers(List<UsersystemObject> users);
	
	List<UsersystemObject> getAllUsers();
	
	List<BuildingObject> getAllBuildings();
	
	List<AnalyzerObject> getAllAnalyzers();

    UsersystemObject findUserByToken( String token );

    UsersystemObject findUserByUserName( String userName );

    List<BuildingObject> findUserBuildings( String userName );

    List<AnalyzerRegistryObject> getDayHourRegistriesFromAnalyzer( String analyzerId, Date from, Date to );
    
	List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);

	List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);
	
	Date getLowestAnalyzerRegistryDate();

	String[] getDatesAvailable();

	Long getLatestDateForAnalyzer(String analyzerCodeName);

	BuildingObject getBuildingByName(String buildingName);

	AnalyzerObject getAnalyzerByCodeName(String analyzerCodeName);

	void deleteBuilding(BuildingObject object);

	void deleteDivisionCascade(DivisionObject mainDivision);

	

}
