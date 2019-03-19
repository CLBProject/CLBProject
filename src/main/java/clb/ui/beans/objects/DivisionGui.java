package clb.ui.beans.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;

import clb.business.objects.DivisionObject;

public class DivisionGui {

	private String divisionId;
	private String name;
	private Set<AnalyzerGui> analyzers;
	private Set<DivisionGui> childrenDivisions;
	private SelectItem parentDivision;

	public DivisionGui() {
		
	}
	
	public DivisionGui(DivisionObject divisionObj) {
		this.divisionId = divisionObj.getId();
		this.name = divisionObj.getName();
		this.childrenDivisions =  divisionObj.getChildrenDivisions() != null ? 
									divisionObj.getChildrenDivisions().stream().map(DivisionGui::new).collect(Collectors.toSet()): null;
		this.analyzers = divisionObj.getAnalyzers() != null ?
				divisionObj.getAnalyzers().stream().map(AnalyzerGui::new).collect(Collectors.toSet()) : null;
	}
	
	public DivisionObject toObject() {
		DivisionObject divObj = new DivisionObject();
		divObj.setName(this.name);
		divObj.setId(this.divisionId);
		divObj.setAnalyzers(this.analyzers != null ? 
				this.analyzers.stream().map(AnalyzerGui::toObject).collect(Collectors.toSet()) : null);
		divObj.setChildrenDivisions(this.childrenDivisions != null ? 
				this.childrenDivisions.stream().map(DivisionGui::toObject).collect(Collectors.toSet()) : null);
		
		return divObj;
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

	public Set<AnalyzerGui> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(Set<AnalyzerGui> analyzers) {
		this.analyzers = analyzers;
	}

	public Set<DivisionGui> getChildrenDivisions() {
		return childrenDivisions;
	}

	public void setChildrenDivisions(Set<DivisionGui> childrenDivisions) {
		this.childrenDivisions = childrenDivisions;
	}

	public boolean hasAnalyzers() {
		return analyzers != null && analyzers.size() > 0;
	}

	public void addAnalyzer(AnalyzerGui analObj) {
		if(this.analyzers == null) {
			this.analyzers = new HashSet<AnalyzerGui>();
		}
		
		this.analyzers.add(analObj);
	}

	public boolean hasSubDivisions() {
		return childrenDivisions != null && childrenDivisions.size() > 0;
	}

	public SelectItem getParentDivision() {
		return parentDivision;
	}

	public void setParentDivision(SelectItem parentDivision) {
		this.parentDivision = parentDivision;
	}
	
	
	
}
