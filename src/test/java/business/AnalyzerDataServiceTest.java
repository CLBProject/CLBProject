package business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import clb.business.AnalyzerDataService;
import clb.business.AnalyzerDataServiceImpl;
import clb.database.ClbDao;
import clb.database.ClbDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {
		AnalyzerDataServiceImpl.class,
		ClbDaoImpl.class
})
public class AnalyzerDataServiceTest {

	@Autowired
	private AnalyzerDataService analyzerDataService;

	@Autowired
	TaskExecutor taskExecutor;
	
	@Mock
	private ClbDao clbDao;
	
	@Before
	public void setUp() {
		Mockito.mock(TaskExecutor.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetYearsAvailable() {
		
		String[] years = {"2016","2017","2018"};
		
		Mockito.when(clbDao.getYearsAvailable()).thenReturn(years);
		
		String[] yearsReturned = analyzerDataService.getYearsAvailable();
		
		Assert.assertEquals(yearsReturned[0],"2016");
		Assert.assertEquals(yearsReturned[1],"2017");
		Assert.assertEquals(yearsReturned[2],"2018");
		
	}
}
