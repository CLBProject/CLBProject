package ui;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import clb.business.AnalyzerDataService;
import clb.ui.beans.AnalysisBean;
import clb.ui.beans.ClbHomeLoginBean;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.AnalyzerMeterGui;
import clb.ui.beans.objects.BuildingGui;

public class AnalysisBeanTest extends AbstractBeanTest{

	@InjectMocks
	AnalysisBean analysisBean;
	
	@Mock
	AnalyzerDataService analyzerDataService;
	
	@Mock
	ClbHomeLoginBean clbHomeLoginBean;
	

	@Override
	public void initBean() {
		
		List<BuildingGui> buildings = new ArrayList<BuildingGui>();
		BuildingGui bGui = new BuildingGui();
		BuildingGui bGui2 = new BuildingGui();
		AnalyzerGui analObj = new AnalyzerGui();
		AnalyzerGui analObj2 = new AnalyzerGui();
		
		AnalyzerMeterGui anaMeterObj = new AnalyzerMeterGui();
		AnalyzerMeterGui anaMeterObj2 = new AnalyzerMeterGui();

		bGui.addAnalyzer(analObj);
		bGui2.addAnalyzer(analObj2);
		
		analObj.addAnalyzerMeter(anaMeterObj);
		analObj2.addAnalyzerMeter(anaMeterObj2);
		
		buildings.add(bGui);
		buildings.add(bGui2);
		
		Date currentDate = new Date();
		
		when(analyzerDataService.getLowestAnalyzerRegistryDate()).thenReturn(currentDate);
		when(clbHomeLoginBean.getUserBuildings()).thenReturn(buildings);
		analysisBean.init();
	}

	@Override
	public Object getBean() {
		return analysisBean;
	}

	@Override
	public Set<String> getExecptions() {
		return new HashSet<String>();
	}
}
