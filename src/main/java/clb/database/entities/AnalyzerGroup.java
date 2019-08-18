package clb.database.entities;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class AnalyzerGroup {

	@DBRef
	private AnalyzerEntity analyzerRel1;
	
	@DBRef
	private AnalyzerEntity analyzerRel2;

	
	public AnalyzerEntity getAnalyzerRel1() {
		return analyzerRel1;
	}

	public void setAnalyzerRel1(AnalyzerEntity analyzerRel1) {
		this.analyzerRel1 = analyzerRel1;
	}

	public AnalyzerEntity getAnalyzerRel2() {
		return analyzerRel2;
	}

	public void setAnalyzerRel2(AnalyzerEntity analyzerRel2) {
		this.analyzerRel2 = analyzerRel2;
	}
	
	
}
