package clb.deprecated;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;

public interface AnalyzerDataService {
	
    public void persistScriptBigData() throws IOException;
	
	List<AnalyzerRegistryObject> getDataByDayAndHours(Date day, String hour) throws IOException;

	List<AnalyzerRegistryObject> getDataByDay(Date day) throws IOException;

	List<Integer> getRegistryYears();

	List<MonthAverageObject> getDataByYear(Integer year);

    List<MonthAverageObject> getDataByYearAndMonths( Integer yearSelected, Month monthSelected );
}
