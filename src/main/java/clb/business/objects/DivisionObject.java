package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.DivisionEntity;

public class DivisionObject
{
    private String divisionid;

    private String name;

    private List<DivisionObject> childrenDivisions;

    private List<AnalyzerObject> analyzers;

    public DivisionObject(){
    }

    public DivisionObject( DivisionEntity division ) {
    	this.divisionid = division.getDivisionid();
        this.name = division.getName();
        this.childrenDivisions = division.getChildrenDivision() != null ? 
        		division.getChildrenDivision().stream().map(DivisionObject::new).collect(Collectors.toList()) : null;
        this.analyzers = division.getAnalyzers() != null ? 
        		division.getAnalyzers().stream().map(AnalyzerObject::new).collect(Collectors.toList()) : null;          
    }

    public DivisionEntity toEntity() {
    	DivisionEntity divisionEntity = new DivisionEntity();
    	divisionEntity.setDivisionid( this.divisionid );
        divisionEntity.setName( this.name );
        divisionEntity.setChildrenDivision(childrenDivisions != null ? 
        		childrenDivisions.stream().map(DivisionObject::toEntity).collect(Collectors.toList()) : 
        						null);
        		
        divisionEntity.setAnalyzers(this.analyzers != null ?
                this.analyzers.stream().map(AnalyzerObject::toEntity).collect(Collectors.toList()) : null);

        return divisionEntity;
    }
    
    public void addAnalyzer(AnalyzerObject dataLoggerObject) {
        if(analyzers == null) {
            analyzers = new ArrayList<AnalyzerObject>();
        }

        analyzers.add(dataLoggerObject);
    }
    
	public void addSubDivision(DivisionObject subDiv) {
		if(childrenDivisions == null) {
			childrenDivisions = new ArrayList<DivisionObject>();
        }

        childrenDivisions.add(subDiv);
	}
	

	public boolean hasChildren() {
		return childrenDivisions != null && childrenDivisions.size() > 0;
	}




    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<AnalyzerObject> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerObject> analyzers) {
		this.analyzers = analyzers;
	}

	public String getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}

	public List<DivisionObject> getChildrenDivisions() {
		return childrenDivisions;
	}

	public void setChildrenDivisions(List<DivisionObject> childrenDivisions) {
		this.childrenDivisions = childrenDivisions;
	}

	
}
