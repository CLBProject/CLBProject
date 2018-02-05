package clb.business;

import java.io.IOException;

import clb.business.objects.ClbObject;

public interface AnalyzerDataService {
    
	public void persistObject(ClbObject userObject);
	
    public void persistScriptBigData() throws IOException;
}
