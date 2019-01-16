package clb.database.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Divisions")
public class DivisionEntity {

	@Id
	private String divisionid;

	private String name;
	
	@DBRef
	private List<DivisionEntity> childrenDivision;
	
	@DBRef
	private List<AnalyzerEntity> analyzers;

	public String getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DivisionEntity> getChildrenDivision() {
		return childrenDivision;
	}

	public void setChildrenDivision(List<DivisionEntity> childrenDivision) {
		this.childrenDivision = childrenDivision;
	}

	public List<AnalyzerEntity> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerEntity> analyzers) {
		this.analyzers = analyzers;
	}
	
	
}
