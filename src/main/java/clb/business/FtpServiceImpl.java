package clb.business;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.stereotype.Service;

import clb.business.objects.AnalyzerObject;

@Service
public class FtpServiceImpl implements FtpService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	FtpOutboundGateway ftpOutboundGateway;
	
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

	public FtpOutboundGateway getFtpOutboundGateway() {
		return ftpOutboundGateway;
	}

	public void setFtpOutboundGateway(FtpOutboundGateway ftpOutboundGateway) {
		this.ftpOutboundGateway = ftpOutboundGateway;
	}
	
	
}
