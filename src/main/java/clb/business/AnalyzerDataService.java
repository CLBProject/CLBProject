package clb.business;

import java.io.IOException;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;
import clb.database.entities.DataLogger;

public interface AnalyzerDataService {

	List<AnalyzerRegistryObject> getAnalyzerGraphicalData() throws IOException;
	
	void createDataLogger(DataLogger dataLogger);
}
