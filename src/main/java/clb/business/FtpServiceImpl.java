package clb.business;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.FtpConfiguration;

@Service
public class FtpServiceImpl implements FtpService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {
		
	}
	
	@Override
	public boolean userHasAccount(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AnalyzerObject> getAnalyzersFromUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public FtpConfiguration getFtpConfiguration() {
		return ftpConfiguration;
	}

	public void setFtpConfiguration(FtpConfiguration ftpConfiguration) {
		this.ftpConfiguration = ftpConfiguration;
	}

	
}
