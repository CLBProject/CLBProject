package ui;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import clb.business.objects.BuildingObject;
import clb.business.services.AnalyzerDataService;
import clb.global.AnalyzerMeterValues;
import clb.ui.beans.AnalysisBean;
import clb.ui.beans.ClbHomeLoginBean;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.objects.DivisionGui;

public class AnalysisBeanTest extends AbstractBeanTest{

	@InjectMocks
	AnalysisBean analysisBean;
	
	@Mock
	AnalyzerDataService analyzerDataService;
	
	@Mock
	ClbHomeLoginBean clbHomeLoginBean;
	

	@Override
	public void initBean() {
		
		List<BuildingAnalysisGui> buildings = new ArrayList<BuildingAnalysisGui>();
		BuildingAnalysisGui bGui = new BuildingAnalysisGui(new BuildingObject());
		BuildingAnalysisGui bGui2 = new BuildingAnalysisGui(new BuildingObject());
		AnalyzerGui analObj = new AnalyzerGui();
		AnalyzerGui analObj2 = new AnalyzerGui();
		
		DivisionGui divisionG = new DivisionGui();
		DivisionGui divisionG2 = new DivisionGui();

		bGui.addDivision(divisionG);
		bGui2.addDivision(divisionG2);
		
		divisionG.addAnalyzer(analObj);
		divisionG2.addAnalyzer(analObj2);
		
		analObj.addAnalyzerMeter(AnalyzerMeterValues.FREQUENCY);
		analObj2.addAnalyzerMeter(AnalyzerMeterValues.VOLT_AMPERE);
		
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
