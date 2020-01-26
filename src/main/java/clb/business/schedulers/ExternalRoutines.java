package clb.business.schedulers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import clb.business.integration.FtpGatewayMget;
import clb.business.objects.AnalyzerRegistryObject;
import clb.database.ClbDao;

@Component
public class ExternalRoutines {

	@Autowired
	FtpGatewayMget ftpGatewayMget;
	
	@Autowired
	ClbDao clbDao;
	
	
	@Transactional
	public void fetchAnalyzerRegistries() {
		ftpGatewayMget.mGet("/", "*");
	}


	public void persistIncomingRegistriesData(Map<String, List<String[]>> registiesData) {

		registiesData.entrySet().stream().forEach(entryFile -> {
			Set<AnalyzerRegistryObject> registries = new HashSet<AnalyzerRegistryObject>();
			entryFile.getValue().stream().forEach(line -> {
				if(line.length > 33) {
					
					AnalyzerRegistryObject analyzerObj = new AnalyzerRegistryObject();
					analyzerObj.setRecorttype(line[0]);
					analyzerObj.setProducttype(line[1]);
					analyzerObj.setItemlabel(line[3]);
					analyzerObj.setComport(Integer.parseInt(line[4]));
					analyzerObj.setModbusid(Integer.parseInt(line[5]));
					analyzerObj.setCurrenttime(new Date(Long.parseLong(line[6])*1000));
					analyzerObj.setVlnsys(Double.parseDouble(line[10]));
					analyzerObj.setVl1n(Double.parseDouble(line[11]));
					analyzerObj.setVl2n(Double.parseDouble(line[12]));
					analyzerObj.setVl3n(Double.parseDouble(line[13]));
					analyzerObj.setVllsys(Double.parseDouble(line[14]));
					analyzerObj.setVl1l2(Double.parseDouble(line[15]));
					analyzerObj.setVl2l3(Double.parseDouble(line[16]));
					analyzerObj.setVl3l1(Double.parseDouble(line[17]));
					analyzerObj.setAl1(Double.parseDouble(line[18]));
					analyzerObj.setAl2(Double.parseDouble(line[19]));
					analyzerObj.setAl3(Double.parseDouble(line[20]));
					analyzerObj.setKwsys(Double.parseDouble(line[21]));
					analyzerObj.setKwl1(Double.parseDouble(line[22]));
					analyzerObj.setKwl2(Double.parseDouble(line[23]));
					analyzerObj.setKwl3(Double.parseDouble(line[24]));
					analyzerObj.setKvarsys(Double.parseDouble(line[25]));
					analyzerObj.setKvarl1(Double.parseDouble(line[26]));
					analyzerObj.setKvarl2(Double.parseDouble(line[27]));
					analyzerObj.setKvarl3(Double.parseDouble(line[28]));
					analyzerObj.setKvasys(Double.parseDouble(line[29]));
					analyzerObj.setKval1(Double.parseDouble(line[30]));
					analyzerObj.setKval2(Double.parseDouble(line[31]));
					analyzerObj.setKval3(Double.parseDouble(line[32]));
					analyzerObj.setPfsys(Double.parseDouble(line[33]));
					analyzerObj.setPfl1(Double.parseDouble(line[34]));
					analyzerObj.setPfl2(Double.parseDouble(line[35]));
					analyzerObj.setPfl3(Double.parseDouble(line[36]));
					analyzerObj.setHz(Double.parseDouble(line[38]));
					analyzerObj.setAnalyzerId(entryFile.getKey());
					
					registries.add(analyzerObj);
				}
			});
			clbDao.saveAnalyzerRegistries(registries, entryFile.getKey());
		});
		

	}

	
}
