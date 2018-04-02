package clb.global;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import clb.beans.enums.Hours;

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
        Integer realMonth = cal.get(Calendar.MONTH) + 1;
        String month = realMonth >= 10 ? "" + realMonth : "0" + realMonth;
        String day = cal.get(Calendar.DAY_OF_MONTH) >= 10 ? "" + cal.get(Calendar.DAY_OF_MONTH) : "0" + cal.get(Calendar.DAY_OF_MONTH);

        return name + "_"+ year+month+day;
    }
    
    public Date getDateResetedbyHour(Date timeFrame) {
        Calendar timeFrameCalToday = Calendar.getInstance();
        timeFrameCalToday.setTime( timeFrame );
        timeFrameCalToday.set( Calendar.MINUTE, 0 );
        timeFrameCalToday.set( Calendar.SECOND, 0 );
        
        return timeFrameCalToday.getTime();
    }
    
    public Date getNextHourFromDate(Date timeFrame) {
        Calendar timeFrameCalTomorrow = Calendar.getInstance();
        timeFrameCalTomorrow.setTime( timeFrame );
        timeFrameCalTomorrow.add( Calendar.HOUR_OF_DAY, 1 );
        
        return timeFrameCalTomorrow.getTime();
    }
    
    public Date getDateResetedbyDay(Date timeFrame) {
        Calendar timeFrameCalToday = Calendar.getInstance();
        timeFrameCalToday.setTime( timeFrame );
        timeFrameCalToday.set( Calendar.MINUTE, 0 );
        timeFrameCalToday.set( Calendar.SECOND, 0 );
        timeFrameCalToday.set( Calendar.HOUR_OF_DAY, 0 );
        
        return timeFrameCalToday.getTime();
    }
    
    public Date getNextDayFromDate(Date timeFrame) {
        Calendar timeFrameCalTomorrow = Calendar.getInstance();
        timeFrameCalTomorrow.setTime( timeFrame );
        timeFrameCalTomorrow.add( Calendar.DAY_OF_MONTH, 1 );
        
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
		
		return cal.get(Calendar.MONTH+1);
	}
    


}
