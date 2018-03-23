package clb.beans.enums;

public enum ScaleGraphic
{
    HOUR("Hour"),
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year");
    
    private String label;
    
    ScaleGraphic(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
}
