package clb.business.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject implements ClbObject
{
    private String id;
    
    private String codeName;
	
    private Set<AnalyzerMeterObject> analyzerMeters;
    
    private Set<String> analyzerRegistriesIds;

	public AnalyzerObject(){

	}

	public AnalyzerObject(AnalyzerEntity analyzerEntity){
		this.id = analyzerEntity.getId();
		this.codeName = analyzerEntity.getCodeName();
		this.analyzerRegistriesIds = analyzerEntity.getAnalyzerRegistriesIds();
		this.analyzerMeters = analyzerEntity.getAnalyzerMeters() != null ?
				analyzerEntity.getAnalyzerMeters().stream().map(AnalyzerMeterObject::new).collect( Collectors.toSet()) : null;
	}

	public AnalyzerEntity toEntity(){
		AnalyzerEntity analyzerEntity = new AnalyzerEntity();
		
		if(this.id != null) {
			analyzerEntity.setId( this.id );
		}
		
		analyzerEntity.setCodeName( this.codeName );
		analyzerEntity.setAnalyzerRegistriesIds(this.analyzerRegistriesIds);
		analyzerEntity.setAnalyzerMeters( this.analyzerMeters != null ? 
                this.analyzerMeters.stream().map( AnalyzerMeterObject::toEntity ).collect( Collectors.toSet() ) : null);
		
		return analyzerEntity;
	}
	
    public void addAnalyzerRegistryId(String analyzerObjId) {
        if(analyzerRegistriesIds == null) {
        	analyzerRegistriesIds = new HashSet<String>();
        }
        
        analyzerRegistriesIds.add(analyzerObjId);
    }

	public void addAnalyzerMeter(AnalyzerMeterObject analyzerMeterObject) {
		if(this.analyzerMeters == null) {
			this.analyzerMeters = new HashSet<AnalyzerMeterObject>();
		}
		
		this.analyzerMeters.add(analyzerMeterObject);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	
	public Set<AnalyzerMeterObject> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterObject> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	public Set<String> getAnalyzerRegistriesIds() {
		return analyzerRegistriesIds;
	}

	public void setAnalyzerRegistriesIds(Set<String> analyzerRegistriesIds) {
		this.analyzerRegistriesIds = analyzerRegistriesIds;
	}

	
}
