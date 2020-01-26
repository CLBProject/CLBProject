package clb.business.objects;

import java.util.HashSet;
import java.util.Set;

import clb.global.AnalyzerMeterValues;

public class AnalyzerObject implements ClbObject
{
    private String id;
    
    private String codeName;
	
    private Set<AnalyzerMeterValues> analyzerMeters;

    
	public AnalyzerObject(){

	}

	public void addAnalyzerMeter(AnalyzerMeterValues analyzerMeter) {
		if(this.analyzerMeters == null) {
			this.analyzerMeters = new HashSet<AnalyzerMeterValues>();
		}
		
		this.analyzerMeters.add(analyzerMeter);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	
	public Set<AnalyzerMeterValues> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterValues> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}
	
}
