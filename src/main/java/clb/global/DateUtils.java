package clb.global;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
    private static DateUtils instance;
    private DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static synchronized DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }
    
    public String convertDateToSimpleStringFormat(Date date) {
        return outputFormat.format( date );
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

	public Date setHourForDate(Date analysisDate, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(analysisDate);
		cal.set(Calendar.HOUR_OF_DAY,value);
		
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

	public Date getWeekFirstDayReseted(Date timeFrame) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeFrame);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		return cal.getTime();
	}

	public Date getWeekLastDay(Date timeFrame) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(getWeekFirstDayReseted(timeFrame));
		
		for (int i = 0; i < 7; i++) {
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
		// TODO Auto-generated method stub
		return false;
	}
}
