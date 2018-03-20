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
}
