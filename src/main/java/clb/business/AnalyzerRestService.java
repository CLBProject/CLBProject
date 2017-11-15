package clb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzerRestService {
	
	@Autowired
	private AnalyzerDataService analyzerDataService;
	
	@RequestMapping("/test")
	public String testing(){
		System.out.println("Arrived Index");
		return "index.xhtml";
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}
	
	
}
