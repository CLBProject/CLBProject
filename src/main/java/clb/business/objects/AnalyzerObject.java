package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject implements ClbObject
{
    private String id;
    
    private String codeName;

	private List<String> analyzerRegistriesIds;
	
    private List<AnalyzerMeterObject> analyzerMeters;

	public AnalyzerObject(){

	}

	public AnalyzerObject(AnalyzerEntity analyzerEntity){
		this.id = analyzerEntity.getId();
		this.codeName = analyzerEntity.getCodeName();
		this.analyzerRegistriesIds = analyzerEntity.getAnalyzerRegistriesIds();
		this.analyzerMeters = analyzerEntity.getAnalyzerMeters() != null ?
				analyzerEntity.getAnalyzerMeters().stream().map(AnalyzerMeterObject::new).collect( Collectors.toList()) : null;
	}

	public AnalyzerEntity toEntity(){
		AnalyzerEntity analyzerEntity = new AnalyzerEntity();
		
		if(this.id != null) {
			analyzerEntity.setId( this.id );
		}
		
		analyzerEntity.setCodeName( this.codeName );
		analyzerEntity.setAnalyzerRegistriesIds(this.analyzerRegistriesIds);
		
		analyzerEntity.setAnalyzerMeters( this.analyzerMeters != null ? 
                this.analyzerMeters.stream().map( AnalyzerMeterObject::toEntity ).collect( Collectors.toList() ) : null);
		
		return analyzerEntity;
	}
	
    public void addAnalyzerRegistry(String analyzerRegId) {
        if(analyzerRegistriesIds == null) {
            analyzerRegistriesIds = new ArrayList<String>();
        }
        
        analyzerRegistriesIds.add(analyzerRegId);
    }

	public void addAnalyzerMeter(AnalyzerMeterObject analyzerMeterObject) {
		if(this.analyzerMeters == null) {
			this.analyzerMeters = new ArrayList<AnalyzerMeterObject>();
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

	public List<String> getAnalyzerRegistriesIds() {
        return analyzerRegistriesIds;
    }

    public void setAnalyzerRegistriesIds( List<String> analyzerRegistriesIds ) {
        this.analyzerRegistriesIds = analyzerRegistriesIds;
    }

	public List<AnalyzerMeterObject> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(List<AnalyzerMeterObject> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	
}
