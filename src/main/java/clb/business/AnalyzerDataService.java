package clb.business;

import java.io.IOException;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;

public interface AnalyzerDataService {

	List<AnalyzerRegistryObject> getAnalyzerGraphicalData() throws IOException;
	
	void fillDatabaseData() throws IOException;
}
