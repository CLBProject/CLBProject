package clb.ui.beans.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import clb.business.objects.AnalyzerObject;

public class AnalyzerGui {

	private String analyzerId;
	private String codeName;
	private Set<AnalyzerMeterGui> analyzerMeters;

	public AnalyzerGui() {
		
	}
	
	public AnalyzerGui(AnalyzerObject analyzerObj) {
		this.analyzerId = analyzerObj.getId();
		this.codeName = analyzerObj.getCodeName();
		this.analyzerMeters = analyzerObj.getAnalyzerMeters() != null ?
				analyzerObj.getAnalyzerMeters().stream().map(AnalyzerMeterGui::new).collect(Collectors.toSet()) : null;
	}
	
	public AnalyzerObject toObject() {
		AnalyzerObject analyzer = new AnalyzerObject();
		analyzer.setId(this.analyzerId);
		analyzer.setCodeName(this.codeName);
		analyzer.setAnalyzerMeters(this.analyzerMeters != null ? this.analyzerMeters.stream().map(AnalyzerMeterGui::toObject).collect(Collectors.toSet()) : null);
		
		return analyzer;
	}
	
	public void addAnalyzerMeter(AnalyzerMeterGui analyzerMeter) {
		if(this.analyzerMeters == null) {
			this.analyzerMeters = new HashSet<AnalyzerMeterGui>();
		}
		
		this.analyzerMeters.add(analyzerMeter);
	}

	public Set<AnalyzerMeterGui> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterGui> analyzerMeters) {
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
	
	
}
