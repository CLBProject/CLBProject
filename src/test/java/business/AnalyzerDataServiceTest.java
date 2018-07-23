package business;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.configuration.ConfigurationBusiness;
import clb.business.AnalyzerDataService;
import clb.database.ClbDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {
		ConfigurationBusiness.class
})
public class AnalyzerDataServiceTest {

	@Autowired
	private AnalyzerDataService analyzerDataService;

	@Autowired
	private ClbDao clbDao;

	@Before
	public void setup() {
	}

	@Test
	public void testGetYearsAvailable() {

		String[] years = {"2016","2017","2018"};
		
		when(clbDao.getYearsAvailable()).thenReturn(years);

		String[] yearsReturned = analyzerDataService.getYearsAvailable();

		Assert.assertEquals(yearsReturned[0],"2016");
		Assert.assertEquals(yearsReturned[1],"2017");
		Assert.assertEquals(yearsReturned[2],"2018");

	}
}
