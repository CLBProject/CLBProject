package clb.business.constants;

public enum Month
{
    JANUARY("January",1),
    FEBRUARY("February",2),
    MARCH("March",3),
    APRIL("April",4),
    MAY("May",5),
    JUNE("June",6),
    JULY("July",7),
    AUGUST("August",8),
    SEPTEMBER("September",9),
    OCTOBER("October",10),
    NOVEMBER("November",11),
    DECEMBER("December",12);
    
    Integer month;
    String label;
    
    Month(String label, Integer month){
        this.label = label;
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public String getLabel() {
        return label;
    }

    public static Month getMonthById( Integer monthId ) {
        for(Month value: values()){
            if(value.getMonth() == monthId)
                return value;
        }
        return null;
    }
}
