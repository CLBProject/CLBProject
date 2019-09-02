package clb.business.integration;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class FtpPrinter {
	
	
	public void print(Message<?> messageGet) {
		//analyzerDataService.setAnalyzersFtp((List<String>)messageGet.getPayload());
	}

	public void printPut(Message<?> messageGet) {
		System.out.println("Put:" + messageGet.getPayload());
	}
	
	public void printRm(Message<?> messageGet) {
		System.out.println("Rm:" + messageGet.getPayload());
	}
}
