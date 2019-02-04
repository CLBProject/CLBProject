package clb.ui.beans.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import clb.business.objects.DivisionObject;

public class DivisionGui {

	private String divisionId;
	private String name;
	private List<AnalyzerGui> analyzers;
	private List<DivisionGui> childrenDivisions;
	private TreeNode treeNode;

	public DivisionGui() {
		
	}
	
	public DivisionGui(DivisionObject divisionObj) {
		this.divisionId = divisionObj.getDivisionid();
		this.name = divisionObj.getName();
		this.childrenDivisions =  divisionObj.getChildrenDivisions() != null ? 
									divisionObj.getChildrenDivisions().stream().map(DivisionGui::new).collect(Collectors.toList()): null;
		this.analyzers = divisionObj.getAnalyzers() != null ?
				divisionObj.getAnalyzers().stream().map(AnalyzerGui::new).collect(Collectors.toList()) : null;
	}
	
	public DivisionObject toObject() {
		DivisionObject divObj = new DivisionObject();
		divObj.setName(this.name);
		divObj.setDivisionid(this.divisionId);
		divObj.setAnalyzers(this.analyzers != null ? 
				this.analyzers.stream().map(AnalyzerGui::toObject).collect(Collectors.toList()) : null);
		divObj.setChildrenDivisions(this.childrenDivisions != null ? 
				this.childrenDivisions.stream().map(DivisionGui::toObject).collect(Collectors.toList()) : null);
		
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

	public List<AnalyzerGui> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerGui> analyzers) {
		this.analyzers = analyzers;
	}

	public List<DivisionGui> getChildrenDivisions() {
		return childrenDivisions;
	}

	public void setChildrenDivisions(List<DivisionGui> childrenDivisions) {
		this.childrenDivisions = childrenDivisions;
	}

	public boolean hasAnalyzers() {
		return analyzers != null && analyzers.size() > 0;
	}

	public void addAnalyzer(AnalyzerGui analObj) {
		if(this.analyzers == null) {
			this.analyzers = new ArrayList<AnalyzerGui>();
		}
		
		this.analyzers.add(analObj);
	}

	public boolean hasSubDivisions() {
		return childrenDivisions != null && childrenDivisions.size() > 0;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}


	
}
