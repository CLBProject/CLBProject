package ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.services.AnalyzerDataService;
import clb.ui.beans.objects.AnalyzerRegistryGui;
import clb.ui.beans.utils.AnalysisBeanCache;

@RunWith(MockitoJUnitRunner.class)
public class AnalysisBeanCacheTest {

	@Mock
	AnalyzerDataService analyzerDataService;

	private AnalysisBeanCache analysisBeanCache;

	@Before
	public void init() {
		analysisBeanCache = new AnalysisBeanCache(analyzerDataService);
	}

	@Test
	@Ignore
	public void testHoursCache() {
		final String analyzerId = "11";
		final Date timeFrame = new Date();

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		AnalyzerRegistryObject obj1 = new AnalyzerRegistryObject();
		obj1.setAn(2.3);
		obj1.setCurrenttime(timeFrame);
		obj1.setAsys(9.3);

		AnalyzerRegistryObject obj2 = new AnalyzerRegistryObject();
		obj2.setAn(11.5);
		obj2.setCurrenttime(timeFrame);
		obj2.setAsys(4.1);

		registries.add(obj1);
		registries.add(obj2);

		//When
		when(analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame)).thenReturn(registries);
		List<AnalyzerRegistryGui> hourRegs = analysisBeanCache.getHourRegistriesFromAnalyzer(analyzerId, timeFrame);
		List<AnalyzerRegistryGui> hourRegsAfterCache = analysisBeanCache.getHourRegistriesFromAnalyzer(analyzerId, timeFrame);

		//Then

		assertEquals(hourRegs.size(),2);
		assertEquals(hourRegsAfterCache.size(),2);

		AnalyzerRegistryGui cacheObj1 = hourRegs.get(0);
		AnalyzerRegistryGui cacheObj2 = hourRegs.get(1);

		assertEquals(cacheObj1.getAsys(),2.3,0.0);
		assertEquals(cacheObj1.getAsys(),9.3,0.0);
		assertEquals(cacheObj1.getCurrentTime().toString(),obj1.getCurrenttime().toString());

		assertEquals(cacheObj2.getAsys(),11.5,0.0);
		assertEquals(cacheObj2.getAsys(),4.1,0.0);
		assertEquals(cacheObj2.getCurrentTime().toString(),obj2.getCurrenttime().toString());

		AnalyzerRegistryGui cacheObjAfterCache1 = hourRegsAfterCache.get(0);
		AnalyzerRegistryGui cacheObjAfterCache2 = hourRegsAfterCache.get(1);

		assertEquals(cacheObjAfterCache1.getAsys(),2.3,0.0);
		assertEquals(cacheObjAfterCache1.getAsys(),9.3,0.0);
		assertEquals(cacheObjAfterCache1.getCurrentTime().toString(),obj1.getCurrenttime().toString());

		assertEquals(cacheObjAfterCache2.getAsys(),11.5,0.0);
		assertEquals(cacheObjAfterCache2.getAsys(),4.1,0.0);
		assertEquals(cacheObjAfterCache2.getCurrentTime().toString(),obj2.getCurrenttime().toString());
	}

	@Test
	@Ignore
	public void testDaysCache() {
		final String analyzerId = "9";
		final Date timeFrame = new Date();

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		AnalyzerRegistryObject obj1 = new AnalyzerRegistryObject();
		obj1.setKwh(9.9);
		obj1.setCurrenttime(timeFrame);
		obj1.setHz(6.3);

		AnalyzerRegistryObject obj2 = new AnalyzerRegistryObject();
		obj2.setKwh(31.0);
		obj2.setCurrenttime(timeFrame);
		obj2.setHz(12.6);

		registries.add(obj1);
		registries.add(obj2);

		//When
		when(analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, timeFrame)).thenReturn(registries);
		List<AnalyzerRegistryGui> dayRegs = analysisBeanCache.getDayRegistriesFromAnalyzer(analyzerId, timeFrame);
		List<AnalyzerRegistryGui> dayRegsAfterCache = analysisBeanCache.getDayRegistriesFromAnalyzer(analyzerId, timeFrame);

		//Then

		assertEquals(dayRegs.size(),2);
		assertEquals(dayRegsAfterCache.size(),2);

		AnalyzerRegistryGui cacheObj1 = dayRegs.get(0);
		AnalyzerRegistryGui cacheObj2 = dayRegs.get(1);

		assertEquals(cacheObj1.getKwsys(),9.9,0.0);
		assertEquals(cacheObj1.getHz(),6.3,0.0);
		assertEquals(cacheObj1.getCurrentTime().toString(),obj1.getCurrenttime().toString());

		assertEquals(cacheObj2.getKwsys(),31.0,0.0);
		assertEquals(cacheObj2.getHz(),12.6,0.0);
		assertEquals(cacheObj2.getCurrentTime().toString(),obj2.getCurrenttime().toString());

		AnalyzerRegistryGui cacheObjAfterCache1 = dayRegsAfterCache.get(0);
		AnalyzerRegistryGui cacheObjAfterCache2 = dayRegsAfterCache.get(1);

		assertEquals(cacheObjAfterCache1.getKwsys(),9.9,0.0);
		assertEquals(cacheObjAfterCache1.getHz(),6.3,0.0);
		assertEquals(cacheObjAfterCache1.getCurrentTime().toString(),obj1.getCurrenttime().toString());

		assertEquals(cacheObjAfterCache2.getKwsys(),31.0,0.0);
		assertEquals(cacheObjAfterCache2.getHz(),12.6,0.0);
		assertEquals(cacheObjAfterCache2.getCurrentTime().toString(),obj2.getCurrenttime().toString());
	}

	@Test
	@Ignore
	public void testWeekCache() {
		testWeekCacheFunc(false);
	}
	
	@Ignore
	@Test
	public void testWeekCacheWithShift() {
		testWeekCacheFunc(true);
	}

	private void testWeekCacheFunc(boolean withShift) {
		final String analyzerId = "122";
		final int week = 2;
		final int month = 3;
		final int year = 2016;
		final int shift = 5;

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		AnalyzerRegistryObject obj1 = new AnalyzerRegistryObject();
		obj1.setPfl1(1.4);
		obj1.setKvahl(23.5);

		AnalyzerRegistryObject obj2 = new AnalyzerRegistryObject();
		obj2.setPfl1(8.8);
		obj2.setKvahl(0.4);

		registries.add(obj1);
		registries.add(obj2);

		//When

		List<AnalyzerRegistryGui> weekRegs;
		List<AnalyzerRegistryGui> weekRegsAfterCache;
		
		if(withShift) {
			when(analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId,week,month,year, shift)).thenReturn(registries);
			weekRegs = analysisBeanCache.getWeekRegistriesFromAnalyzerWithShift(analyzerId, week,month,year, shift);
			weekRegsAfterCache = analysisBeanCache.getWeekRegistriesFromAnalyzerWithShift(analyzerId, week,month,year, shift);
		}
		else {
			when(analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId,week,month,year)).thenReturn(registries);
			weekRegs = analysisBeanCache.getWeekRegistriesFromAnalyzer(analyzerId, week,month,year);
			weekRegsAfterCache = analysisBeanCache.getWeekRegistriesFromAnalyzer(analyzerId, week,month,year);
		}

		//Then

		assertEquals(weekRegs.size(),2);
		assertEquals(weekRegsAfterCache.size(),2);

		AnalyzerRegistryGui cacheObj1 = weekRegs.get(0);
		AnalyzerRegistryGui cacheObj2 = weekRegs.get(1);

		assertEquals(cacheObj1.getPfsys(),1.4,0.0);
		assertEquals(cacheObj1.getKvasys(),23.5,0.0);

		assertEquals(cacheObj2.getPfsys(),8.8,0.0);
		assertEquals(cacheObj2.getKvasys(),0.4,0.0);

		AnalyzerRegistryGui cacheObjAfterCache1 = weekRegsAfterCache.get(0);
		AnalyzerRegistryGui cacheObjAfterCache2 = weekRegsAfterCache.get(1);

		assertEquals(cacheObjAfterCache1.getPfsys(),1.4,0.0);
		assertEquals(cacheObjAfterCache1.getKvasys(),23.5,0.0);

		assertEquals(cacheObjAfterCache2.getPfsys(),8.8,0.0);
		assertEquals(cacheObjAfterCache2.getKvasys(),0.4,0.0);

	}

	@Test
	public void testMonthCache() {
		testMonthCacheFunc(false);
	}
	
	@Test
	public void testMonthCacheWithShift() {
		testMonthCacheFunc(true);
	}
	
	private void testMonthCacheFunc(boolean withShift) {
		final String analyzerId = "9999ssd124";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.YEAR, 2001);
		
		final int shift = 3;

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		AnalyzerRegistryObject obj1 = new AnalyzerRegistryObject();
		obj1.setKwsys(53.1);
		obj1.setKvasys(2.1);

		AnalyzerRegistryObject obj2 = new AnalyzerRegistryObject();
		obj2.setKwsys(33.3);
		obj2.setKvasys(126.02);

		registries.add(obj1);
		registries.add(obj2);

		//When
		List<AnalyzerRegistryGui> monthRegs;
		List<AnalyzerRegistryGui> monthRegsAfterCache;
		
		if(withShift) {
			when(analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId,cal.getTime(),shift)).thenReturn(registries);
			monthRegs = analysisBeanCache.getMonthRegistriesFromAnalyzerWithShift(analyzerId,cal.getTime(),shift);
			monthRegsAfterCache = analysisBeanCache.getMonthRegistriesFromAnalyzerWithShift(analyzerId,cal.getTime(),shift);
		}
		else {
			when(analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId,cal.getTime())).thenReturn(registries);
			monthRegs = analysisBeanCache.getMonthRegistriesFromAnalyzer(analyzerId,cal.getTime());
			monthRegsAfterCache = analysisBeanCache.getMonthRegistriesFromAnalyzer(analyzerId,cal.getTime());
		}
		

		//Then
		assertEquals(monthRegs.size(),2);
		assertEquals(monthRegsAfterCache.size(),2);

		AnalyzerRegistryGui cacheObj1 = monthRegs.get(0);
		AnalyzerRegistryGui cacheObj2 = monthRegs.get(1);

		assertEquals(cacheObj1.getKwsys(),53.1,0.0);
		assertEquals(cacheObj1.getKvasys(),2.1,0.0);

		assertEquals(cacheObj2.getKwsys(),33.3,0.0);
		assertEquals(cacheObj2.getKvasys(),126.02,0.0);

		AnalyzerRegistryGui cacheObjAfterCache1 = monthRegsAfterCache.get(0);
		AnalyzerRegistryGui cacheObjAfterCache2 = monthRegsAfterCache.get(1);

		assertEquals(cacheObjAfterCache1.getKwsys(),53.1,0.0);
		assertEquals(cacheObjAfterCache1.getKvasys(),2.1,0.0);

		assertEquals(cacheObjAfterCache2.getKwsys(),33.3,0.0);
		assertEquals(cacheObjAfterCache2.getKvasys(),126.02,0.0);

	}
}
