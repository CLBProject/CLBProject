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
				Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks( i, year), 4);
			else
				Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks( i, year), 5);
		}
		
		//29 days february
		cal.set(Calendar.YEAR,yearBi);
		for(int i=0;i<12;i++) {
			cal.set(Calendar.MONTH, i);
			Assert.assertEquals(DateUtils.getInstance().getNumberOfMonthWeeks(i, yearBi), 5);
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
		
		cal.setTime(DateUtils.getInstance().getHourReseted(cal.getTime(),false));
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 4);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.setTime(DateUtils.getInstance().getHourReseted(cal.getTime(),false));
		
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
		
		cal.setTime(DateUtils.getInstance().getHourReseted(cal.getTime(),true));
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 6);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.setTime(DateUtils.getInstance().getHourReseted(cal.getTime(),true));
		
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
	public void testPreviousWeekAndMonths() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateUtils.getWeekFirstDayReseted(3, 2, 2018));
		Date calDate = cal.getTime();
		
		int week = DateUtils.getInstance().getWeekFromDate(calDate);
		int month = DateUtils.getInstance().getMonthFromDate(calDate);
		int year = DateUtils.getInstance().getYearFromDate(calDate);
		
		Assert.assertEquals(DateUtils.getInstance().getPreviousWeekFromWeek(week, month, year),2);
		Assert.assertEquals(DateUtils.getInstance().getPreviousMonthFromWeek(week, month, year),2);
		Assert.assertEquals(DateUtils.getInstance().getPreviousYearFromWeek(week, month, year),2018);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(1, 0, 2018));
		Date calDate2 = cal.getTime();
		
		int week2 = DateUtils.getInstance().getWeekFromDate(calDate2);
		int month2 = DateUtils.getInstance().getMonthFromDate(calDate2);
		int year2 = DateUtils.getInstance().getYearFromDate(calDate2);
		
		Assert.assertEquals(DateUtils.getInstance().getPreviousWeekFromWeek(week2, month2, year2),5);
		Assert.assertEquals(DateUtils.getInstance().getPreviousMonthFromWeek(week2, month2, year2),11);
		Assert.assertEquals(DateUtils.getInstance().getPreviousYearFromWeek(week2, month2, year2),2017);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(1, 10, 2018));
		Date calDate3 = cal.getTime();
		
		int week3 = DateUtils.getInstance().getWeekFromDate(calDate3);
		int month3 = DateUtils.getInstance().getMonthFromDate(calDate3);
		int year3 = DateUtils.getInstance().getYearFromDate(calDate3);
		
		Assert.assertEquals(DateUtils.getInstance().getPreviousWeekFromWeek(week3, month3, year3),5);
		Assert.assertEquals(DateUtils.getInstance().getPreviousMonthFromWeek(week3, month3, year3),9);
		Assert.assertEquals(DateUtils.getInstance().getPreviousYearFromWeek(week3, month3, year3),2018);
	}
	
	@Test
	public void testNextWeeksAndMonths() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateUtils.getWeekFirstDayReseted(3, 4, 2018));
		Date calDate = cal.getTime();
		
		int week = DateUtils.getInstance().getWeekFromDate(calDate);
		int month = DateUtils.getInstance().getMonthFromDate(calDate);
		int year = DateUtils.getInstance().getYearFromDate(calDate);
		
		Assert.assertEquals(DateUtils.getInstance().getNextWeekFromWeek(week, month, year),4);
		Assert.assertEquals(DateUtils.getInstance().getNextMonthFromWeek(week, month, year),4);
		Assert.assertEquals(DateUtils.getInstance().getNextYearFromWeek(week, month, year),2018);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(5, 11, 2017));
		Date calDate2 = cal.getTime();
		
		int week2 = DateUtils.getInstance().getWeekFromDate(calDate2);
		int month2 = DateUtils.getInstance().getMonthFromDate(calDate2);
		int year2 = DateUtils.getInstance().getYearFromDate(calDate2);
		
		Assert.assertEquals(DateUtils.getInstance().getNextWeekFromWeek(week2, month2, year2),1);
		Assert.assertEquals(DateUtils.getInstance().getNextMonthFromWeek(week2, month2, year2),0);
		Assert.assertEquals(DateUtils.getInstance().getNextYearFromWeek(week2, month2, year2),2018);
		
		cal.setTime(dateUtils.getWeekFirstDayReseted(5, 10, 2018));
		Date calDate3 = cal.getTime();
		
		int week3 = DateUtils.getInstance().getWeekFromDate(calDate3);
		int month3 = DateUtils.getInstance().getMonthFromDate(calDate3);
		int year3 = DateUtils.getInstance().getYearFromDate(calDate3);
		
		Assert.assertEquals(DateUtils.getInstance().getNextWeekFromWeek(week3, month3, year3),1);
		Assert.assertEquals(DateUtils.getInstance().getNextMonthFromWeek(week3, month3, year3),11);
		Assert.assertEquals(DateUtils.getInstance().getNextYearFromWeek(week3, month3, year3),2018);
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
