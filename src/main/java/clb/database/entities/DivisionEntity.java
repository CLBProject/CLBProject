package clb.database.entities;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Divisions")
public class DivisionEntity implements ClbEntity{

	@Id
	private String id;

	private String name;
	
	@DBRef
	private Set<DivisionEntity> childrenDivision;
	
	@DBRef
	private Set<AnalyzerEntity> analyzers;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<DivisionEntity> getChildrenDivision() {
		return childrenDivision;
	}

	public void setChildrenDivision(Set<DivisionEntity> childrenDivision) {
		this.childrenDivision = childrenDivision;
	}

	public Set<AnalyzerEntity> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(Set<AnalyzerEntity> analyzers) {
		this.analyzers = analyzers;
	}
	
	
}
