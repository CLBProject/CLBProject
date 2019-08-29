package clb.ui.beans.newobjects;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.AnalyzerObject;
import clb.global.AnalyzerMeterValues;

public class AnalyzerNewManagementGui {

	@NotNull
	private String analyzerId;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String codeName;
    
    private Set<String> analyzerMeters;


	public AnalyzerNewManagementGui() {
	}
	
	public AnalyzerObject toObject() {
		AnalyzerObject analyzer = new AnalyzerObject();
		
		analyzer.setCodeName(codeName);
		analyzer.setAnalyzerMeters(analyzerMeters.stream()
				.map(analyzerMeter -> AnalyzerMeterValues.valueOf(analyzerMeter)).collect(Collectors.toSet()));
		
		return analyzer;
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

	public Set<String> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<String> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	
	
}
