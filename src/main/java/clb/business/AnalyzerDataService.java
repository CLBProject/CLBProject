package clb.business;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.constants.Month;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.MonthAverageObject;
import clb.business.objects.UsersystemObject;

public interface AnalyzerDataService {
    
    public void persistScriptBigData() throws IOException;
	
	List<AnalyzerRegistryObject> getDataByDayAndHours(Date day, String hour) throws IOException;

	List<AnalyzerRegistryObject> getDataByDay(Date day) throws IOException;

	List<Integer> getRegistryYears();

	List<MonthAverageObject> getDataByYear(Integer year);

    List<MonthAverageObject> getDataByYearAndMonths( Integer yearSelected, Month monthSelected );
    
    public UsersystemObject userCanLogin(String userName, String password);
}
