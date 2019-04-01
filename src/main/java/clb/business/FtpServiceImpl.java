package clb.business;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.stereotype.Service;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.FtpConfiguration;

@Service
public class FtpServiceImpl implements FtpService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	FtpConfiguration ftpConfiguration;

	public void init() {
		FtpOutboundGateway outboundGw = ftpConfiguration.getGW();
		System.out.println("Outbound: " + outboundGw.toString());
	}

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
