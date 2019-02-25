package clb.business.objects;

import clb.database.entities.AnalyzerMeterEntity;

public class AnalyzerMeterObject implements ClbObject {
	private String id;

	private String name;

	private String labelKey;

	private String unit;

	public AnalyzerMeterObject() {

	}

	public AnalyzerMeterObject(AnalyzerMeterEntity analyzerMeterEntity) {
		this.id = analyzerMeterEntity.getId();
		this.name = analyzerMeterEntity.getName();
		this.unit = analyzerMeterEntity.getUnit();
		this.labelKey = analyzerMeterEntity.getLabelKey();
	}

	public AnalyzerMeterEntity toEntity() {
		AnalyzerMeterEntity analyzerMeterEntity = new AnalyzerMeterEntity();
		analyzerMeterEntity.setId(this.id);
		analyzerMeterEntity.setName(this.name);
		analyzerMeterEntity.setLabelKey(this.labelKey);
		analyzerMeterEntity.setUnit(this.unit);

		return analyzerMeterEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
