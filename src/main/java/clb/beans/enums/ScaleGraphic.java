package clb.beans.enums;

public enum ScaleGraphic
{
    HOUR("Hour",1),
    DAY("Day",96),
    WEEK("Week",168),
    MONTH("Month",124);
    
    private String label;
    
    private int maxRegistries;
    
    ScaleGraphic(String label, int maxRegistries){
        this.label = label;
        this.maxRegistries = maxRegistries;
    }

    public String getLabel() {
        return label;
    }

	public int getMaxRegistries() {
		return maxRegistries;
	}
    
}
