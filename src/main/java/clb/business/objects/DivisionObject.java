package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.DivisionEntity;

public class DivisionObject implements ClbObject
{
    private String id;

    private String name;

    private List<DivisionObject> childrenDivisions;

    private List<AnalyzerObject> analyzers;

    public DivisionObject(){
    }

    public DivisionObject( DivisionEntity division ) {
    	this.id = division.getId();
        this.name = division.getName();
        this.childrenDivisions = division.getChildrenDivision() != null ? 
        		division.getChildrenDivision().stream().map(DivisionObject::new).collect(Collectors.toList()) : null;
        this.analyzers = division.getAnalyzers() != null ? 
        		division.getAnalyzers().stream().map(AnalyzerObject::new).collect(Collectors.toList()) : null;          
    }

    public DivisionEntity toEntity() {
    	DivisionEntity divisionEntity = new DivisionEntity();
    	divisionEntity.setId(this.id);
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
	
	public void deleteSubDivision(DivisionObject divisionChildObj) {
		if(childrenDivisions != null)
			childrenDivisions.remove(divisionChildObj);
	}

	

	public boolean hasChildren() {
		return childrenDivisions != null && childrenDivisions.size() > 0;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((analyzers == null) ? 0 : analyzers.hashCode());
		result = prime * result + ((childrenDivisions == null) ? 0 : childrenDivisions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DivisionObject other = (DivisionObject) obj;
		if (analyzers == null) {
			if (other.analyzers != null)
				return false;
		} else if (!analyzers.equals(other.analyzers))
			return false;
		if (childrenDivisions == null) {
			if (other.childrenDivisions != null)
				return false;
		} else if (!childrenDivisions.equals(other.childrenDivisions))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<DivisionObject> getChildrenDivisions() {
		return childrenDivisions;
	}

	public void setChildrenDivisions(List<DivisionObject> childrenDivisions) {
		this.childrenDivisions = childrenDivisions;
	}


	
}
