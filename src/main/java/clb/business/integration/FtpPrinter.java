package clb.business.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import clb.business.AnalyzerDataService;

@Component
public class FtpPrinter {
	
	@Autowired
	AnalyzerDataService analyzerDataService;
	
	
	public void print(Message<?> messageGet) {
		analyzerDataService.setMessageArrived(messageGet);
	}

	
}
