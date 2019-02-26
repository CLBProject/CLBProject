package clb.ui.beans.treeStructure;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;

import clb.business.objects.DivisionObject;

public class DivisionTreeGui implements Serializable, Comparable<DivisionTreeGui> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionId;
	private String name;
	private List<SelectItem> analyzers;
	
	public DivisionTreeGui(DivisionObject division) {
		this.divisionId = division.getId();
		this.name = division.getName();
		this.analyzers = division.getAnalyzers() != null ? 
									division.getAnalyzers().stream()
										.map( analyzer -> new SelectItem(analyzer,analyzer.getCodeName()))
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

	public List<SelectItem> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<SelectItem> analyzers) {
		this.analyzers = analyzers;
	}
	
}
