package clb.business.objects;

import clb.database.entities.AnalyzerMeterEntity;

public class AnalyzerMeterObject
{
    private String meterId;

    private String name;
    
    private String labelKey;
    
    private String unit;
    
    public AnalyzerMeterObject(){
        
    }
    
    public AnalyzerMeterObject( AnalyzerMeterEntity analyzerMeterEntity ) {
        this.meterId = analyzerMeterEntity.getBuildingMeterId();
        this.name = analyzerMeterEntity.getName();
        this.unit = analyzerMeterEntity.getUnit();
        this.labelKey = analyzerMeterEntity.getLabelKey();
    }

    public AnalyzerMeterEntity toEntity() {
        AnalyzerMeterEntity analyzerMeterEntity = new AnalyzerMeterEntity();
        analyzerMeterEntity.setBuildingMeterId( this.meterId );
        analyzerMeterEntity.setName( this.name );
        analyzerMeterEntity.setLabelKey( this.labelKey );
        analyzerMeterEntity.setUnit( this.unit );
        
        return analyzerMeterEntity;
    }
    
    

    public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey( String labelKey ) {
        this.labelKey = labelKey;
    }

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
    
    
    
}
