package clb.global;

public enum BuildingMeterParameterValues
{
    VOLTAGE("Voltage"),
    VOLTAGE_BETWEEN_PHASES("Voltage Between Phases"),
    POWER("Power"),
    REACTIVE_POWER("Reactive Power"),
    VOLT_AMPERE("Volt-Ampere"),
    POWER_FACTOR("Power Factor"),
    FREQUENCY("Frequency"),
    CURRENT("Current");
    
    private String label;
    
    BuildingMeterParameterValues(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
}
