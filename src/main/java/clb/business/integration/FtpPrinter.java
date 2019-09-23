package clb.business.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import clb.business.schedulers.ExternalRoutines;

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

	@SuppressWarnings("unchecked")
	public void printMget(Message<?> messageGet) {
		List<File> files = (List<File>) messageGet.getPayload();
		externalRoutines.persistIncomingRegistriesData(this.parseFilesToRegistries(files));
	}
	
	private Map<String,List<String[]>> parseFilesToRegistries(List<File> incomingFiles) {
		Map<String,List<String[]>> filesData = new HashMap<String,List<String[]>>();
		
		incomingFiles.stream().forEach(incomingFile -> {
			
			try (FileInputStream fis = new FileInputStream(incomingFile);
			     Scanner in = new Scanner(fis)){
				
				List<String[]> dataFromFile = new ArrayList<String[]>();
				while(in.hasNext()) {
					String nextLine = in.nextLine();
					
					if(nextLine.contains(";"))
						dataFromFile.add(nextLine.split(";"));
				}
				
				String splitFilePath = incomingFile.getParentFile().getName();
				filesData.put(splitFilePath,dataFromFile);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return filesData;
	}
}
