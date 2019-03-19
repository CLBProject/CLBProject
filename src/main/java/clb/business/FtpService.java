package clb.business;

import java.util.List;

import clb.business.objects.AnalyzerObject;

public interface FtpService {

	public boolean userHasAccount(String username);
	
	public void createUser(String username);
	
	public void deleteUser(String username);
	
	public List<AnalyzerObject> getAnalyzersFromUser(String username);
}
