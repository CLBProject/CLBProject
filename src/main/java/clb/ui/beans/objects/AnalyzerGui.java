package clb.ui.beans.objects;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clb.business.objects.AnalyzerObject;
import clb.global.AnalyzerMeterValues;

public class AnalyzerGui {

	private String analyzerId;
	private String codeName;
	private Set<AnalyzerMeterValues> analyzerMeters;
	private List<Date> registriesDates;

	public AnalyzerGui() {
		
	}
	
	public AnalyzerGui(AnalyzerObject analyzerObj) {
		this.analyzerId = analyzerObj.getId();
		this.codeName = analyzerObj.getCodeName();
		this.analyzerMeters = analyzerObj.getAnalyzerMeters();
	}
	
	public AnalyzerObject toObject() {
		AnalyzerObject analyzer = new AnalyzerObject();
		analyzer.setId(this.analyzerId);
		analyzer.setCodeName(this.codeName);
		analyzer.setAnalyzerMeters(this.analyzerMeters);
		
		return analyzer;
	}
	
	public void addAnalyzerMeter(AnalyzerMeterValues analyzerMeter) {
		if(this.analyzerMeters == null) {
			this.analyzerMeters = new HashSet<AnalyzerMeterValues>();
		}
		
		this.analyzerMeters.add(analyzerMeter);
	}

	public Set<AnalyzerMeterValues> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterValues> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	public String getAnalyzerId() {
		return analyzerId;
	}

	public void setAnalyzerId(String analyzerId) {
		this.analyzerId = analyzerId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public List<Date> getRegistriesDates() {
		return registriesDates;
	}

	public void setRegistriesDates(List<Date> registriesDates) {
		this.registriesDates = registriesDates;
	}
	
	
}
