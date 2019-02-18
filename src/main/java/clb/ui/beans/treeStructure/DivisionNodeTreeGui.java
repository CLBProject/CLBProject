package clb.ui.beans.treeStructure;

import java.io.Serializable;
import java.util.List;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.DivisionObject;

public class DivisionNodeTreeGui implements Serializable, Comparable<DivisionNodeTreeGui> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionId;
	private String name;
	private List<AnalyzerObject> analyzers;
	
	public DivisionNodeTreeGui(DivisionObject division) {
		this.divisionId = division.getDivisionid();
		this.name = division.getName();
		this.analyzers = division.getAnalyzers();
	}
	
	@Override
	public int compareTo(DivisionNodeTreeGui document) {
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
