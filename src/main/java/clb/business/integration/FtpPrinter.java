package clb.business.integration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import clb.business.schedulers.ExternalRoutines;
import clb.database.ClbDao;

@Component
public class FtpPrinter {
	
	@Autowired
	ExternalRoutines externalRoutines;
	
	public void print(Message<?> messageGet) {
		//analyzerDataService.setAnalyzersFtp((List<String>)messageGet.getPayload());
	}

	public void printPut(Message<?> messageGet) {
		System.out.println("Put:" + messageGet.getPayload());
	}
	
	public void printRm(Message<?> messageGet) {
		System.out.println("Rm:" + messageGet.getPayload());
	}
	
	public void printMget(Message<?> messageGet) {
		List<File> files = (List<File>) messageGet.getPayload();
		externalRoutines.setRegistiesData(this.parseFilesToRegistries(files));
	}
	
	private List<String[][]> parseFilesToRegistries(List<File> incomingFiles) {
		List<String[][]> filesData = new ArrayList<String[][]>();
		
		return filesData;
	}
}
