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
	private DateFormat hourOutputFormat = new SimpleDateFormat("mm:ss");
	private DateFormat prettyDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");

	public static synchronized DateUtils getInstance() {
		if (instance == null) {
			instance = new DateUtils();
		}
		return instance;
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


	public Date getPreviousHourFromDate(Date timeFrame) {
		Calendar timeFrameCalTomorrow = Calendar.getInstance();
		timeFrameCalTomorrow.setTime( timeFrame );
		timeFrameCalTomorrow.add( Calendar.HOUR_OF_DAY, -1 );

		return timeFrameCalTomorrow.getTime();
	}

	public Date getPreviousDayFromDate(Date timeFrame) {
		Calendar timeFrameCalTomorrow = Calendar.getInstance();
		timeFrameCalTomorrow.setTime( timeFrame );
		timeFrameCalTomorrow.add( Calendar.DAY_OF_MONTH, -1 );

		return timeFrameCalTomorrow.getTime();
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

	public Date getHourReseted(Date timeFrame, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);

		if(next)
			cal.add(Calendar.HOUR_OF_DAY, 1);
		else cal.add(Calendar.HOUR_OF_DAY, -1);

		return cal.getTime();
	}

	public Date getDayReseted(Date timeFrame, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		if(next)
			cal.add(Calendar.DAY_OF_MONTH, 1);
		else cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();
	}

	public Date getWeekReseted(Date analysisDate, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);

		if(next)
			cal.add(Calendar.DATE, 7);
		else
			cal.add(Calendar.DATE,- 7);

		return cal.getTime();
	}


	public Date getMonthReseted(Date analysisDate, boolean next) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.DAY_OF_MONTH,0);

		if(next)
			cal.add(Calendar.MONTH, 1);
		else cal.add(Calendar.MONTH, -1);

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

	public Date getWeekFirstDayReseted(Date timeFrame) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MILLISECOND,0);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		return cal.getTime();
	}

	public Date getWeekLastDay(Date timeFrame) {

		Calendar c = Calendar.getInstance();
		c.setTime(getWeekFirstDayReseted(timeFrame));

		for (int i = 0; i < 6; i++) {
			c.add(Calendar.DATE, 1);
		}

		return c.getTime();
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

	public boolean isThisWeek(Date timeFrame) {

		Date todayDate = new Date();

		return timeFrame.compareTo(getWeekFirstDayReseted(todayDate)) >= 0 &&
				timeFrame.compareTo(getWeekLastDay(todayDate)) < 0;
	}

	public boolean isThisMonth(Date timeFrame) {
		Date todayDate = new Date();

		return timeFrame.compareTo(getMonthFirstDayReseted(todayDate)) >= 0 &&
				timeFrame.compareTo(getMonthLastDay(todayDate)) < 0;
	}

	public Date getMonthLastDay(Date todayDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(todayDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public Date getMonthFirstDayReseted(Date todayDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(todayDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return updateIfIsSundayForMonth(cal.getTime());
	}

	public String prettyFormat(Date analysisDate) {

		return prettyDateFormat.format(analysisDate);
	}

	public String hourFormat(Date analysisDate) {

		return hourOutputFormat.format(analysisDate);
	}


	public Date setMonthOfDate(Date date, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getMonthFirstDayReseted(date));
		cal.set(Calendar.MONTH, value);
		return cal.getTime();

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

	public Date setYearOfDate(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}

	public Date setHourOfDate(Date date, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, value);
		return cal.getTime();
	}


	public int getNumberOfWeeksFromDate(Date date) {

		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		Date lateDate = getMonthLastDay(date);

		if(isDateBiggerThen(lateDate, currentDate)) {
			cal.setTime(currentDate);
		}
		else cal.setTime(lateDate);

		return cal.get(Calendar.WEEK_OF_MONTH) ;
	}

	public int getWeekFromDate(Date todayDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(todayDate);

		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	public Date setWeekOfDate(Date date, int value) {

		//First Week
		if(value == 1) {
			return getMonthFirstDayReseted(date);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.WEEK_OF_MONTH, value);
		cal.setTime(getWeekFirstDayReseted(cal.getTime()));

		return cal.getTime();
	}

	public boolean isSameYearOfCurrent(Date analysisDate) {
		Calendar otherDate = Calendar.getInstance();
		otherDate.setTime(analysisDate);

		Calendar thisDate = Calendar.getInstance();
		thisDate.setTime(new Date());

		return thisDate.get(Calendar.YEAR) == otherDate.get(Calendar.YEAR);
	}

	public boolean isDateBiggerThen(Date date1, Date date2) {

		return date1.compareTo(date2) > 0;
	}

	public Date updateIfIsSundayForMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if(cal.get(Calendar.DAY_OF_WEEK) == 1)
			return getDayReseted(date, true);

		return date;
	}


	public Date replaceDateForOtherDate(Date dateToBase, Date currentTime, boolean hour) {
		Calendar otherDate = Calendar.getInstance();
		otherDate.setTime(dateToBase);

		Calendar thisDate = Calendar.getInstance();
		thisDate.setTime(currentTime);


		if(hour) {
			otherDate.set(Calendar.HOUR_OF_DAY,thisDate.get(Calendar.HOUR_OF_DAY));
		}
		otherDate.set(Calendar.MINUTE,thisDate.get(Calendar.MINUTE));
		return otherDate.getTime();
	}

	public Date reaplceDateForWeekDay(Date dateToBase, Date currentTime) {

		Calendar otherDate = Calendar.getInstance();
		otherDate.setTime(dateToBase);

		Calendar thisDate = Calendar.getInstance();
		thisDate.setTime(currentTime);
		
		for (int i = 0; i < 6; i++) {
			if(otherDate.get(Calendar.WEEK_OF_YEAR) > thisDate.get(Calendar.WEEK_OF_YEAR))
				otherDate.add(Calendar.DATE, -1);
			else otherDate.add(Calendar.DATE, 1);
		}
		
		System.out.println(prettyFormat(otherDate.getTime()));
		
		return otherDate.getTime();
	}
}
