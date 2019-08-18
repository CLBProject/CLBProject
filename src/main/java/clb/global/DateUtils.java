package clb.global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
	private static DateUtils instance;
	private DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat hourOutputFormat = new SimpleDateFormat("HH:mm:ss");
	private DateFormat weekFormat = new SimpleDateFormat("EEEE dd MMMM");
	private DateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
	private DateFormat prettyDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

	public static synchronized DateUtils getInstance() {
		if (instance == null) {
			instance = new DateUtils();
		}
		return instance;
	}
	
	public String convertDateToSimpleWeekFormat(int week, int month, int year, int weekShift) {
		return week + "-" + month + "-" + year + "-" + weekShift;
	}
	
	public String convertDateToSimpleMonthFormat(int month, int year, int weekShift) {
		return month + "-" + year + "-" + weekShift;
	}

	public String convertDateToSimpleStringFormat(Date date) {
		return outputFormat.format( date );
	}

	public Date convertDateStringToSimpleDateFormat(String dateStr) throws ParseException {
		return outputFormat.parse( dateStr );
	}

	public String concatTimeWithString(String name, Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime( time );

		String year = cal.get(Calendar.YEAR) + "";
		String month = cal.get(Calendar.MONTH) >= 10 ? "" + cal.get(Calendar.MONTH) : "0" + cal.get(Calendar.MONTH);
		String day = cal.get(Calendar.DAY_OF_MONTH) >= 10 ? "" + cal.get(Calendar.DAY_OF_MONTH) : "0" + cal.get(Calendar.DAY_OF_MONTH);

		return name + "_"+ year+month+day;
	}

	public int getHourFromDate(Date analysisDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public int getMonthFromDate(Date analysisDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);

		return cal.get(Calendar.MONTH);
	}

	public Date setDayForDate(Date analysisDate, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);
		cal.set(Calendar.DAY_OF_MONTH,value);

		return cal.getTime();

	}

	public boolean isThisHour(Date date) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);

		Calendar today = Calendar.getInstance();

		return dateCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) && 
				dateCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
				dateCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) &&
				dateCal.get(Calendar.HOUR_OF_DAY) == today.get(Calendar.HOUR_OF_DAY);
	}

	public boolean isToday(Date date) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);

		Calendar today = Calendar.getInstance();

		return dateCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) && 
				dateCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
				dateCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
	}

	public Date getHourReseted(Date timeFrame) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);

		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		return cal.getTime();
	}

	public Date getHour(Date timeFrame, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);

		if(next)
			cal.add(Calendar.HOUR_OF_DAY, 1);
		else cal.add(Calendar.HOUR_OF_DAY, -1);

		return cal.getTime();
	}

	public Date getDay(Date timeFrame, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);

		if(next)
			cal.add(Calendar.DAY_OF_MONTH, 1);
		else cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();
	}

	public Date getDayReseted(Date timeFrame) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}

	public Date getWeekFirstDayReseted(int weekNr, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,(weekNr-1)*7+1);
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}

	public Date getWeekLastDay(int weekNr, int month, int year) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,(weekNr-1)*7+1);
		cal.add(Calendar.DATE, 7);

		if(cal.get(Calendar.MONTH) != month) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}

		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}

	public boolean isTheSameDay(Date date1, Date date2) {

		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date1);

		Calendar dateCal2 = Calendar.getInstance();
		dateCal2.setTime(date2);

		return dateCal.get(Calendar.YEAR) == dateCal2.get(Calendar.YEAR) && 
				dateCal.get(Calendar.MONTH) == dateCal2.get(Calendar.MONTH) &&
				dateCal.get(Calendar.DAY_OF_MONTH) == dateCal2.get(Calendar.DAY_OF_MONTH);
	}

	public Date getWeekToDate(Date date,boolean previous) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if(previous) {
			cal.add(Calendar.DATE, 7);
		}
		else {
			cal.add(Calendar.DATE, -7);
		}

		return cal.getTime();
	}


	public boolean isThisWeek(int week,int month,int year) {

		Calendar dateCal = Calendar.getInstance();

		int dayOfMonth = dateCal.get(Calendar.DAY_OF_MONTH);

		return dateCal.get(Calendar.YEAR) == year && 
				dateCal.get(Calendar.MONTH) == month && getWeekFromByDayOfMonth(dayOfMonth) == week;
	}

	public boolean isThisMonth(int month, int year) {
		Calendar dateCal = Calendar.getInstance();

		return dateCal.get(Calendar.YEAR) == year && 
				dateCal.get(Calendar.MONTH) == month;
	}

	public Date getMonthToDate(Date date,boolean add) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if(add) {
			cal.add(Calendar.MONTH, 1);
		}
		else {
			cal.add(Calendar.MONTH, -1);
		}

		return cal.getTime();
	}

	public boolean isThisYear(int year) {
		Calendar dateCal = Calendar.getInstance();
		return dateCal.get(Calendar.YEAR) == year;
	}

	public Date getMonthFirstDay(int month, int year) {	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}

	public Date getMonthLastDay(int month, int year) {	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}


	public Date getMonthLastDay(Date firstDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDay);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return cal.getTime();
	}

	public String prettyFormat(Date analysisDate) {

		return prettyDateFormat.format(analysisDate);
	}

	public String hourFormat(Date analysisDate) {

		return hourOutputFormat.format(analysisDate);
	}

	public String weekFormat(Date weekDate) {
		return weekFormat.format(weekDate);
	}

	public String monthFormat(Date dateMonth) {
		return monthFormat.format(dateMonth);
	}

	public String transformDateToHoursOrDaysAverage(Date currenttime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currenttime);

		int minute = cal.get(Calendar.MINUTE);
		String minuteStr = minute > 9 ? ""+minute : "0" + minute;
		int minuteValue = Integer.parseInt(minuteStr.charAt(1)+"");


		if(minuteValue <=5 && minuteValue > 0) {
			minuteStr = minuteStr.charAt(0) + "5";
		}
		else minuteStr = minuteStr.charAt(0) + "0";

		cal.set(Calendar.MINUTE, Integer.parseInt(minuteStr));

		return convertDateToSimpleStringFormat(cal.getTime());
	}

	public String transformDateToWeekAverage(Date currenttime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currenttime);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);

		return convertDateToSimpleStringFormat(cal.getTime());
	}

	public String transformDateToMonthAverage(Date currenttime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currenttime);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		return convertDateToSimpleStringFormat(cal.getTime());
	}

	public Integer getYearFromDate(Date todayDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(todayDate);

		return cal.get(Calendar.YEAR);
	}

	public Date setHourOfDate(Date date, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, value);
		return cal.getTime();
	}

	public boolean isDateBiggerThen(Date date1, Date date2) {

		return date1.compareTo(date2) > 0;
	}

	public int getNumberOfMonthWeeks(int month, int year) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		if(isThisMonth(month, year)) {
			return getWeekFromByDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
		}
		else {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);

			int maxMonthDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			if(maxMonthDay % 7 == 0) {
				return maxMonthDay / 7;
			}
			else return maxMonthDay/7 + 1;
		}
	}

	public int getNumberOfMonthsInYear(int year) {

		Calendar cal = Calendar.getInstance();

		if(isThisYear(year)) {
			return cal.get(Calendar.MONTH);
		}
		else return 12;
	}



	public int getWeekFromDate(Date analysisDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);
		return getWeekFromByDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
	}

	private int getWeekFromByDayOfMonth(int dayOfMonth) {
		int week = dayOfMonth/7;

		if((dayOfMonth % 7) == 0) {
			return week;
		}
		else return week + 1;
	}

	public int geWeekNumberOfDays(int month, int year, int week) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR,year);

		if(week == 5)
			return cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 28;
		else return 7;
	}

	public Date shiftDate(Date lastDay, int weekShift) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastDay);
		cal.add(Calendar.DATE, weekShift);

		return cal.getTime();
	}

	public Date shiftMonth(Date date, int monthShift) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, monthShift);

		return cal.getTime();
	}

	public Date shiftHours(Date lastDay, int weekShift) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastDay);
		cal.add(Calendar.HOUR_OF_DAY, weekShift);

		return cal.getTime();
	}

	public Date getWeekFirstDayShift(int week, int month, int year, int weekShift) {

		if(weekShift > 0) {
			Date tempDate = getWeekFirstDayReseted(week,month,year);
			return shiftDate(tempDate,-7);
		}
		else {
			Date lastDay = getWeekLastDayShift(week,month,year, weekShift);
			return shiftDate(lastDay, weekShift);
		}
	}

	public Date getWeekLastDayShift(int week, int month, int year, int weekShift) {

		if(weekShift > 0) {
			Date firstDay = getWeekFirstDayShift(week, month, year, weekShift);
			return shiftDate(firstDay, weekShift);
		}
		else {
			Date tempDate = getWeekLastDay(week,month,year);
			return shiftDate(tempDate,7);
		}
	}

	public Date getMonthFirstDayShift(int week, int month, int year, int monthShift) {
		return shiftMonth(getMonthFirstDay(month, year), monthShift);
	}

}
