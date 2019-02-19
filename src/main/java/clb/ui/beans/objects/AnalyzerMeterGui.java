package clb.ui.beans.objects;

import clb.business.objects.AnalyzerMeterObject;

public class AnalyzerMeterGui {

	private String meterId;
	private String labelKey;
	private String name;
	private String unit;
	
	public AnalyzerMeterGui() {
		
	}
	
	public AnalyzerMeterGui(AnalyzerMeterObject analyzerMeterObject) {
		this.meterId = analyzerMeterObject.getMeterId();
		this.labelKey = analyzerMeterObject.getLabelKey();
		this.name = analyzerMeterObject.getName();
		this.unit = analyzerMeterObject.getUnit();
	}
	
	public AnalyzerMeterObject toObject() {
		AnalyzerMeterObject anMetObj = new AnalyzerMeterObject();
		anMetObj.setLabelKey(this.labelKey);
		anMetObj.setMeterId(this.meterId);
	    anMetObj.setName(this.name);
	    anMetObj.setUnit(this.unit);
		
	    return anMetObj;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}