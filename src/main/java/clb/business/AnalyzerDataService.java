package clb.business;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import clb.business.objects.AnalyzerRegistryObject;

public interface AnalyzerDataService {

	Collection<AnalyzerRegistryObject> getData() throws IOException;
	
	void fillDatabaseData() throws IOException;

	List<AnalyzerRegistryObject> getNewValuesToUpdate(Date sinceDate);
}
