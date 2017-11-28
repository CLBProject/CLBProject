package clb.business.constants;

public enum Month
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
    
    Month(Integer month){
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public static Month getMonthById( Integer monthId ) {
        for(Month value: values()){
            if(value.getMonth() == monthId)
                return value;
        }
        return null;
    }
}
