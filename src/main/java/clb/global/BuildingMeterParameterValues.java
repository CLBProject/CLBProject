package clb.global;

public enum BuildingMeterParameterValues
{
    VOLTAGE("Voltage","Volts"),
    VOLTAGE_BETWEEN_PHASES("Voltage B. Phases","Volts"),
    POWER("Power","Kwh"),
    REACTIVE_POWER("Reactive Power","Kv"),
    VOLT_AMPERE("Volt-Ampere","Kv"),
    POWER_FACTOR("Power Factor","Kwh"),
    FREQUENCY("Frequency","Hz"),
    CURRENT("Current","Kwh");
    
    private String label;
    private String unit;
    
    BuildingMeterParameterValues(String label, String unit) {
        this.label = label;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

	public String getUnit() {
		return unit;
	}
    
    
}
