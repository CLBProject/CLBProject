package clb.business;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;

public interface AnalyzerDataService {

	List<AnalyzerRegistryObject> getDataByDay(Date day) throws IOException;
	
	void fillDatabaseData() throws IOException;
	
	void fillDatabaseDataWithMoreThenOneYears();

	List<AnalyzerRegistryObject> getNewValuesToUpdate(Date sinceDate);

	List<Integer> getRegistryYears();

	Object[] getDataByYear(Integer year);
}
