package clb.business.objects;

import clb.business.constants.Month;

public class MonthAverageObject
{

    private double al1Average;
    private double al2Average;
    private double al3Average;
    private Month month;
    
    public MonthAverageObject( double al1Average, double al2Average, double al3Average, Month month ) {
        super();
        this.al1Average = al1Average;
        this.al2Average = al2Average;
        this.al3Average = al3Average;
        this.month = month;
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
    
    
}
