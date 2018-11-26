package clb.deprecated;

public class MonthAverageObject
{

    private double al1Average;
    private double al2Average;
    private double al3Average;
    private Integer year;
    private Month month;
    private Integer day;
    
    public MonthAverageObject( double al1Average, double al2Average, double al3Average, Integer year, Month month, Integer day ) {
        super();
        this.al1Average = al1Average;
        this.al2Average = al2Average;
        this.al3Average = al3Average;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public double getAl1Average() {
        return al1Average;
    }

    public double getAl2Average() {
        return al2Average;
    }

    public double getAl3Average() {
        return al3Average;
    }

    public Month getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getYear() {
        return year;
    }
    
    
}
