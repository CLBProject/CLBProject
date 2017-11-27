package clb.global;

public enum Months
{
    January(1),
    February(2),
    March(3),
    April(4),
    May(5),
    June(6),
    July(7),
    August(8),
    September(9),
    October(10),
    November(11),
    December(12);
    
    Integer month;
    
    Months(Integer month){
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public static String getMonthName( Integer monthId ) {
        for(Months value: values()){
            if(value.getMonth() == monthId)
                return value.name();
        }
        return null;
    }
}
