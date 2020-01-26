package clb.ui.beans.treeStructure;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import clb.business.objects.DivisionObject;
import clb.ui.beans.objects.AnalyzerGui;

public class DivisionTreeGui implements Serializable, Comparable<DivisionTreeGui> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionId;
	private String name;
	private List<AnalyzerGui> analyzers;
	
	public DivisionTreeGui(DivisionObject division) {
		this.divisionId = division.getId();
		this.name = division.getName();
		this.analyzers = division.getAnalyzers() != null ? 
									division.getAnalyzers().stream()
										.map( AnalyzerGui::new)
										.collect(Collectors.toList()) : 
									null;
	}
	
	@Override
	public int compareTo(DivisionTreeGui document) {
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
