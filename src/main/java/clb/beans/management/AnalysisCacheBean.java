package clb.beans.management;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import clb.business.AnalyzerDataService;

@ManagedBean
public class AnalysisCacheBean {
	
	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;
	
	public void testBean() {
		
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}
	
	
}
