package clb.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.UsersystemObject;
import clb.ui.beans.objects.UserSystemGui;

public interface AnalyzerDataService {
    
    public UsersystemObject getUserData(String username);
    
    public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId , Date timeFrame);
    
    public List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId, Date timeFrame );

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, int year);
	
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzerWithWeekShift(String analyzerId, 
			int week, int month, int year, int weekShift);
	
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, int month, int year);
	
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, int month, int year,
			int monthShift);
	
	public Date getLowestAnalyzerRegistryDate();

	public Map<String,List<String>> getYearsAndMonthsAvailable();

	public void fillUserWithAllBuildings(String username);

	public UsersystemObject saveUsersystem(UsersystemObject userUiPojo);
	
	public BuildingObject saveBuilding(BuildingObject building);
}
