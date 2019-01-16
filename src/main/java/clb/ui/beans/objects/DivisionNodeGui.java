package clb.ui.beans.objects;

import java.io.Serializable;
import java.util.List;

public class DivisionNodeGui implements Serializable, Comparable<DivisionNodeGui> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionId;
	private String name;
	private List<AnalyzerGui> analyzers;
	
	public DivisionNodeGui(DivisionGui division) {
		this.divisionId = division.getDivisionId();
		this.name = division.getName();
		this.analyzers = division.getAnalyzers();
	}
	
	@Override
	public int compareTo(DivisionNodeGui document) {
        return this.getDivisionId().compareTo((document).getDivisionId());
    }

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AnalyzerGui> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerGui> analyzers) {
		this.analyzers = analyzers;
	}

	
}
