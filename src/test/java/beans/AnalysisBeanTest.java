package beans;

import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import clb.beans.management.AnalysisBean;
import clb.business.AnalyzerDataService;

@RunWith(MockitoJUnitRunner.class)
public class AnalysisBeanTest {

	@InjectMocks
	AnalysisBean analysisBean;
	
	@Mock
	AnalyzerDataService analyzerDataService;

	@Before
	public void init() {
		
		Date currentDate = new Date();
		
		when(analyzerDataService.getLowestAnalyzerRegistryDate()).thenReturn(currentDate);
		//analysisBean.init();
	}
	
	@Test
	public void testBean() {
	}
}
