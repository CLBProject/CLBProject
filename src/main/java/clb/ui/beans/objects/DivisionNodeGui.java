package clb.ui.beans.objects;

import java.io.Serializable;
import java.util.List;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.DivisionObject;

public class DivisionNodeGui implements Serializable, Comparable<DivisionNodeGui> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionId;
	private String name;
	private List<AnalyzerObject> analyzers;
	
	public DivisionNodeGui(DivisionObject division) {
		this.divisionId = division.getDivisionid();
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

	public List<AnalyzerObject> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerObject> analyzers) {
		this.analyzers = analyzers;
	}

	
}
