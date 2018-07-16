package business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import clb.business.AnalyzerDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class AnalyzerDataServiceTest {

	@Autowired
	private AnalyzerDataService analyzerDataService;
	
	@Test
	public void testGetYearsAvailable() {
		System.out.println(analyzerDataService.toString());
	}
}
