package clb.business.schedulers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import clb.business.integration.FtpGatewayMget;
import clb.database.ClbDao;

@Component
public class ExternalRoutines {

	@Autowired
	FtpGatewayMget ftpGatewayMget;
	
	@Autowired
	ClbDao clbDao;
	
	private List<String[][]> registiesData;
	
	@Transactional
	public void fetchAnalyzerRegistries() {
		ftpGatewayMget.mGet("nobreyeste@hotmail.com/5d67093b93a701de8eb93649/CEFatima", "*");
	}

	public List<String[][]> getRegistiesData() {
		return registiesData;
	}

	public void setRegistiesData(List<String[][]> registiesData) {
		this.registiesData = registiesData;
	}

	
}
