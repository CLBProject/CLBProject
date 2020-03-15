package business;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.configuration.ConfigurationBusiness;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.services.AnalyzerDataService;
import clb.database.ClbDao;
import clb.global.DateUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {
		ConfigurationBusiness.class
})
public class AnalyzerDataServiceTest {

	@Autowired
	private AnalyzerDataService analyzerDataService;

	@Autowired
	private ClbDao clbDao;

	@Test
	public void getAnalyzerRegistriesNowDateValuesTest() {

		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(10.0);
		analyzerReg1.setAn(54.4);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(3.1);
		analyzerReg2.setAn(12.9);

		AnalyzerRegistryObject analyzerReg3 = new AnalyzerRegistryObject();
		analyzerReg3.setKwh(1.5);
		analyzerReg3.setAn(3.1);


		List<AnalyzerRegistryObject> todayRegistries = new ArrayList<AnalyzerRegistryObject>();
		todayRegistries.add(analyzerReg1);
		todayRegistries.add(analyzerReg2);

		List<AnalyzerRegistryObject> thisHourRegistries = new ArrayList<AnalyzerRegistryObject>();
		thisHourRegistries.add(analyzerReg3);

		//When
		when(clbDao.getDayHourRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(todayRegistries);
		List<AnalyzerRegistryObject> dayRegistries = analyzerDataService.getDayRegistriesFromAnalyzer("1", new Date());


		when(clbDao.getDayHourRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(thisHourRegistries);
		List<AnalyzerRegistryObject> hourRegistries = analyzerDataService.getHourRegistriesFromAnalyzer("1", new Date());
		//Then

		AnalyzerRegistryObject finalReg1Obj = dayRegistries.get(0);
		AnalyzerRegistryObject finalReg2Obj = dayRegistries.get(1);

		assertEquals(dayRegistries.size(), todayRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 10.0, 0.01);
		assertEquals(finalReg1Obj.getAn(), 54.4, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 3.1, 0.01);
		assertEquals(finalReg2Obj.getAn(), 12.9, 0.01);

		AnalyzerRegistryObject finalReg3Obj = hourRegistries.get(0);

		assertEquals(thisHourRegistries.size(), hourRegistries.size());
		assertEquals(finalReg3Obj.getKwh(), 1.5, 0.01);
		assertEquals(finalReg3Obj.getAn(), 3.1, 0.01);
	}

	@Test
	public void getAnalyzerRegistriesNotTodayValuesTest() {

		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(9.2);
		analyzerReg1.setAn(14.5);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(0.1);
		analyzerReg2.setAn(99.2);

		AnalyzerRegistryObject analyzerReg3 = new AnalyzerRegistryObject();
		analyzerReg3.setKwh(5.3);
		analyzerReg3.setAn(26.8);

		List<AnalyzerRegistryObject> notTodayRegistries = new ArrayList<AnalyzerRegistryObject>();
		notTodayRegistries.add(analyzerReg1);
		notTodayRegistries.add(analyzerReg2);

		List<AnalyzerRegistryObject> notThisHourRegistries = new ArrayList<AnalyzerRegistryObject>();
		notThisHourRegistries.add(analyzerReg3);

		//When
		when(clbDao.getDayHourRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(notTodayRegistries);
		List<AnalyzerRegistryObject> dayRegistries = analyzerDataService.getDayRegistriesFromAnalyzer("1", DateUtils.getInstance().shiftDate(new Date(), -3));

		when(clbDao.getDayHourRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(notThisHourRegistries);
		List<AnalyzerRegistryObject> hourRegistries = analyzerDataService.getHourRegistriesFromAnalyzer("1", DateUtils.getInstance().shiftDate(new Date(), -3));


		AnalyzerRegistryObject finalReg1Obj = dayRegistries.get(0);
		AnalyzerRegistryObject finalReg2Obj = dayRegistries.get(1);

		//Then
		assertEquals(dayRegistries.size(), notTodayRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 9.2, 0.01);
		assertEquals(finalReg1Obj.getAn(), 14.5, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 0.1, 0.01);
		assertEquals(finalReg2Obj.getAn(), 99.2, 0.01);

		
		AnalyzerRegistryObject finalReg3Obj = hourRegistries.get(0);
		assertEquals(hourRegistries.size(), notThisHourRegistries.size());
		assertEquals(finalReg3Obj.getKwh(), 5.3, 0.01);
		assertEquals(finalReg3Obj.getAn(), 26.8, 0.01);
	}
	
	@Test
	public void getAnalyzerRegistriesWeekValuesTest() {
		
		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(1.7);
		analyzerReg1.setAn(6.3);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(0.9);
		analyzerReg2.setAn(31.3);


		List<AnalyzerRegistryObject> weekRegistries = new ArrayList<AnalyzerRegistryObject>();
		weekRegistries.add(analyzerReg1);
		weekRegistries.add(analyzerReg2);


		//When
		when(clbDao.getWeekRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(weekRegistries);
		List<AnalyzerRegistryObject> dayRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer("2",2,2,2018);


		AnalyzerRegistryObject finalReg1Obj = dayRegistries.get(0);
		AnalyzerRegistryObject finalReg2Obj = dayRegistries.get(1);

		//Then
		assertEquals(dayRegistries.size(), weekRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 1.7, 0.01);
		assertEquals(finalReg1Obj.getAn(), 6.3, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 0.9, 0.01);
		assertEquals(finalReg2Obj.getAn(), 31.3, 0.01);
	}
	
	@Test
	public void getAnalyzerRegistriesWeekShiftValuesTest() {
		
		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(66);
		analyzerReg1.setAn(12.5);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(72);
		analyzerReg2.setAn(18.6);


		List<AnalyzerRegistryObject> weekRegistries = new ArrayList<AnalyzerRegistryObject>();
		weekRegistries.add(analyzerReg1);
		weekRegistries.add(analyzerReg2);


		//When
		when(clbDao.getWeekRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(weekRegistries);
		List<AnalyzerRegistryObject> weekRegistriesFinal = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift("5",5,4,2017,2);


		AnalyzerRegistryObject finalReg1Obj = weekRegistriesFinal.get(0);
		AnalyzerRegistryObject finalReg2Obj = weekRegistriesFinal.get(1);

		//Then
		assertEquals(weekRegistriesFinal.size(), weekRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 66, 0.01);
		assertEquals(finalReg1Obj.getAn(), 12.5, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 72, 0.01);
		assertEquals(finalReg2Obj.getAn(),18.6, 0.01);
	}
	
	@Test
	public void getAnalyzerRegistriesMonthValuesTest() {
		
		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(3.1);
		analyzerReg1.setAn(4.8);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(1.1);
		analyzerReg2.setAn(900);

		List<AnalyzerRegistryObject> monthRegistries = new ArrayList<AnalyzerRegistryObject>();
		monthRegistries.add(analyzerReg1);
		monthRegistries.add(analyzerReg2);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 10);

		//When
		when(clbDao.getMonthRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(monthRegistries);
		List<AnalyzerRegistryObject> monthRegs = analyzerDataService.getMonthRegistriesFromAnalyzer("8",cal.getTime());

		AnalyzerRegistryObject finalReg1Obj = monthRegs.get(0);
		AnalyzerRegistryObject finalReg2Obj = monthRegs.get(1);

		//Then
		assertEquals(monthRegs.size(), monthRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 3.1, 0.01);
		assertEquals(finalReg1Obj.getAn(), 4.8, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 1.1, 0.01);
		assertEquals(finalReg2Obj.getAn(),900, 0.01);
	}
	
	@Test
	public void getAnalyzerRegistriesMonthShiftValuesTest() {
		
		//Given
		AnalyzerRegistryObject analyzerReg1 = new AnalyzerRegistryObject();
		analyzerReg1.setKwh(70.1);
		analyzerReg1.setAn(22.8);

		AnalyzerRegistryObject analyzerReg2 = new AnalyzerRegistryObject();
		analyzerReg2.setKwh(16.5);
		analyzerReg2.setAn(87);

		List<AnalyzerRegistryObject> monthRegistries = new ArrayList<AnalyzerRegistryObject>();
		monthRegistries.add(analyzerReg1);
		monthRegistries.add(analyzerReg2);

		//When
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, 9);
		
		when(clbDao.getMonthRegistriesFromAnalyzer(any(String.class), any(Date.class) , any(Date.class))).thenReturn(monthRegistries);
		List<AnalyzerRegistryObject> monthRegs = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift("7",cal.getTime(),4);

		AnalyzerRegistryObject finalReg1Obj = monthRegs.get(0);
		AnalyzerRegistryObject finalReg2Obj = monthRegs.get(1);

		//Then
		assertEquals(monthRegs.size(), monthRegistries.size());
		assertEquals(finalReg1Obj.getKwh(), 70.1, 0.01);
		assertEquals(finalReg1Obj.getAn(), 22.8, 0.01);
		assertEquals(finalReg2Obj.getKwh(), 16.5, 0.01);
		assertEquals(finalReg2Obj.getAn(),87, 0.01);
	}
}
