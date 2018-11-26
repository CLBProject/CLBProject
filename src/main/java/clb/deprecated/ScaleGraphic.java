package clb.deprecated;

public enum ScaleGraphic
{
    HOUR("Hour"),
    DAY("Day"),
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
