package clb.business;

import java.io.IOException;

import clb.business.objects.ClbObject;

public interface AnalyzerDataService {
    
	public void saveObject(ClbObject userObject);
	
    public void persistScriptBigData() throws IOException;
}
