package clb.business.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import clb.business.AnalyzerDataService;

@Component
public class FtpPrinter {
	
	@Autowired
	AnalyzerDataService analyzerDataService;
	
	
	public void print(Message<?> messageGet) {
		analyzerDataService.setAnalyzersFtp((List<String>)messageGet.getPayload());
	}

	public void printPut(Message<?> messageGet) {
		System.out.println("Put:" + messageGet.getPayload());
	}
	
}
