package clb.global;

public enum BuildingMeterParameterValues
{
    AL1("AL1"),
    AL2("AL2"),
    AL3("AL3");
    
    private String label;
    
    BuildingMeterParameterValues(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
}
