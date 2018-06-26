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

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, int year);
	
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzerWithWeekShift(String analyzerId, 
			int week, int month, int year, int weekShift);
	
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, int month, int year);
	
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, int month, int year,
			int monthShift);
	
	public Date getLowestAnalyzerRegistryDate();

	public String[] getYearsAvailable();


}
