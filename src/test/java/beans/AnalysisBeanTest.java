package beans;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import clb.beans.management.AnalysisBean;
import clb.beans.management.ClbHomeLoginBean;
import clb.beans.pojos.UserLoginPojo;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;

public class AnalysisBeanTest extends AbstractBeanTest{

	@InjectMocks
	AnalysisBean analysisBean;
	
	@Mock
	AnalyzerDataService analyzerDataService;
	
	@Mock
	ClbHomeLoginBean clbHomeLoginBean;
	
	@Mock
	UserLoginPojo userLoginPojo;
	
	@Mock
	UsersystemObject user;

	@Override
	public void initBean() {
		
		List<BuildingObject> buildings = new ArrayList<BuildingObject>();
		BuildingObject bObject = new BuildingObject();
		BuildingObject bObject2 = new BuildingObject();
		DataLoggerObject dlObj = new DataLoggerObject();
		DataLoggerObject dlObj2 = new DataLoggerObject();
		AnalyzerObject analObj = new AnalyzerObject();
		AnalyzerObject analObj2 = new AnalyzerObject();
		
		BuildingMeterObject buildingMeterObj = new BuildingMeterObject();
		BuildingMeterObject buildingMeterObj2 = new BuildingMeterObject();
		
		dlObj.addAnalyzer(analObj);
		dlObj2.addAnalyzer(analObj2);
		
		bObject.addDataLogger(dlObj);
		bObject2.addDataLogger(dlObj2);
		
		bObject.addBuildingMeter(buildingMeterObj);
		bObject2.addBuildingMeter(buildingMeterObj2);
		
		buildings.add(bObject);
		buildings.add(bObject2);
		
		Date currentDate = new Date();
		
		when(analyzerDataService.getLowestAnalyzerRegistryDate()).thenReturn(currentDate);
		when(clbHomeLoginBean.getUserLoginPojo()).thenReturn(userLoginPojo);
		when(userLoginPojo.getCurrentUser()).thenReturn(user);
		when(user.getBuildings()).thenReturn(buildings);
		analysisBean.init();
	}
	
	@Test
	public void test() {
	}

	@Override
	public Object getBean() {
		return analysisBean;
	}
}
