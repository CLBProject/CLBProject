package clb.business;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.MonthAverageObject;

public interface AnalyzerDataService {

	List<AnalyzerRegistryObject> getDataByDay(Date day) throws IOException;
	
	void fillDatabaseData() throws IOException;
	
	void fillDatabaseDataWithMoreThenOneYears();

	List<AnalyzerRegistryObject> getNewValuesToUpdate(Date sinceDate);

	List<Integer> getRegistryYears();

	List<MonthAverageObject> getDataByYear(Integer year);
}
