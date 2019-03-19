package clb.database;

import java.util.Date;
import java.util.List;
import java.util.Set;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.ClbObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;

public interface ClbDao{
	
	void saveClbObject(ClbObject clbObj);
	
	void saveClbObjects(Set<ClbObject> users);
	
    void saveAnalyzerRegistries( Set<AnalyzerRegistryObject> analyzersRegistries );
	
	void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject);
	
	void deleteClbObject(ClbObject object);

	Set<UsersystemObject> getAllUsers();
	
	Set<BuildingObject> getAllBuildings();
	
	Set<AnalyzerObject> getAllAnalyzers();

    UsersystemObject findUserByToken( String token );

    UsersystemObject findUserByUserName( String userName );

	BuildingObject findBuildingById(String buildingId);

	DivisionObject findDivisionById(String parentId);
	
    List<AnalyzerRegistryObject> getDayHourRegistriesFromAnalyzer( String analyzerId, Date from, Date to );
    
	List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);

	List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay);
	
	Date getLowestAnalyzerRegistryDate();

	String[] getDatesAvailable();

	Long getLatestDateForAnalyzer(String analyzerCodeName);

	void deleteDivisionCascade(DivisionObject mainDivision);

}
