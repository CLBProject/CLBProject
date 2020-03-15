package global;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import clb.global.DateUtils;
import org.junit.Assert;

public class DateUtilsTest {

	private DateUtils dateUtils = DateUtils.getInstance();

	@Test
	public void testWeekMethods() {


		Calendar cal = Calendar.getInstance();
		cal.setTime(dateUtils.getWeekFirstDayReseted(1, 0, 1));
		
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 1);
		Assert.assertEquals(cal.get(Calendar.MONTH), 0);
		Assert.assertEquals(cal.get(Calendar.YEAR), 1);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(4, 1, 1));
		
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 22);
		Assert.assertEquals(cal.get(Calendar.MONTH), 1);
		Assert.assertEquals(cal.get(Calendar.YEAR), 1);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(5, 2, 1));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 29);
		Assert.assertEquals(cal.get(Calendar.MONTH), 2);
		Assert.assertEquals(cal.get(Calendar.YEAR), 1);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(6, 9, 1));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 5);
		Assert.assertEquals(cal.get(Calendar.MONTH), 10);
		Assert.assertEquals(cal.get(Calendar.YEAR), 1);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(7, 11, 1));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 12);
		Assert.assertEquals(cal.get(Calendar.MONTH), 0);
		Assert.assertEquals(cal.get(Calendar.YEAR), 2);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(0, 8, 1));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 25);
		Assert.assertEquals(cal.get(Calendar.MONTH), 7);
		Assert.assertEquals(cal.get(Calendar.YEAR), 1);
	}
	
	@Test
	public void testGetNumberOfWeeks() {
		
		int year = 2017;
		int yearBi = 2016;
		
		Calendar cal = Calendar.getInstance();
		
		//28 days february
		cal.set(Calendar.YEAR,year);
		for(int i=0;i<12;i++) {
			cal.set(Calendar.MONTH, i);
			
			if(i == 1)
				Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks( cal.getTime()), 4);
			else
				Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks( cal.getTime()), 5);
		}
		
		//29 days february
		cal.set(Calendar.YEAR,yearBi);
		for(int i=0;i<12;i++) {
			cal.set(Calendar.MONTH, i);
			Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks(cal.getTime()), 5);
		}
		
		cal.setTime(new Date());
		//Test Number of Months in a Year
		Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthsInYear(2017),12);
		Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthsInYear(cal.get(Calendar.YEAR)),cal.get(Calendar.MONTH));
	}
	
	@Test
	public void testPreviousDayAndHourTimes() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 3);	
		cal.set(Calendar.HOUR_OF_DAY, 5);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		
		cal.setTime(DateUtils.getInstance().getHour(cal.getTime(),false));
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 4);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.setTime(DateUtils.getInstance().getHour(cal.getTime(),false));
		
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 23);
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 14);
		
		cal.set(Calendar.DAY_OF_MONTH, 3);
		cal.setTime(DateUtils.getInstance().getDay(cal.getTime(),false));
		
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 2);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.setTime(DateUtils.getInstance().getDay(cal.getTime(),false));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(cal.get(Calendar.MONTH), 2);	
	}
	
	@Test
	public void testNextDayAndHourTimes() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 3);	
		cal.set(Calendar.HOUR_OF_DAY, 5);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		
		cal.setTime(DateUtils.getInstance().getHour(cal.getTime(),true));
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 6);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.setTime(DateUtils.getInstance().getHour(cal.getTime(),true));
		
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 0);
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 16);
		
		cal.set(Calendar.DAY_OF_MONTH, 3);
		cal.setTime(DateUtils.getInstance().getDay(cal.getTime(),true));
		
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 4);

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.setTime(DateUtils.getInstance().getDay(cal.getTime(),true));
		Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), 1);
		Assert.assertEquals(cal.get(Calendar.MONTH), 4);	
	}
	
	@Test
	public void testReplaceDateForTimeString() {
		
		Calendar dateToBaseCal = Calendar.getInstance();
		dateToBaseCal.set(Calendar.YEAR, 2018);
		dateToBaseCal.set(Calendar.MONTH,5);
		dateToBaseCal.set(Calendar.DAY_OF_MONTH,15);
		
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),true));
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 5);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 22);
		
		dateToBaseCal.set(Calendar.DAY_OF_MONTH,26);
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),true));

		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 6);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 3);
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),false));
		
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 5);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 26);
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),false));
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 5);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 19);
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),false));
		
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 5);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 12);
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),false));
		
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 5);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 5);
		
		dateToBaseCal.setTime(DateUtils.getInstance().getWeekToDate(dateToBaseCal.getTime(),false));
		
		Assert.assertEquals(dateToBaseCal.get(Calendar.MONTH), 4);
		Assert.assertEquals(dateToBaseCal.get(Calendar.YEAR), 2018);
		Assert.assertEquals(dateToBaseCal.get(Calendar.DAY_OF_MONTH), 29);
		
	}
}
