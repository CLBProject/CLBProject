package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject
{
	private long analyzerid;

	private String name;

	private List<AnalyzerRegistryObject> analyzerRegistries;

	private List<AnalyzerRegistryAverageObject> analyzerAverageRegistries;

	public AnalyzerObject(){

	}

	public AnalyzerObject(AnalyzerEntity analyzerEntity){
		this.analyzerid = analyzerEntity.getAnalyzerid();
		this.name = analyzerEntity.getName();
		this.analyzerRegistries = analyzerEntity.getAnalyzerRegistries() != null ? 
				analyzerEntity.getAnalyzerRegistries().stream().map(AnalyzerRegistryObject::new).collect(Collectors.toList()) : null;
		this.analyzerAverageRegistries = analyzerEntity.getAnalyzerRegistries() != null ? 
				analyzerEntity.getAnalyzerRegistriesAverage().stream().map(AnalyzerRegistryAverageObject::new).collect(Collectors.toList()) : null;
	}

	public AnalyzerEntity toEntity(){
		AnalyzerEntity analyzerEntity = new AnalyzerEntity();
		analyzerEntity.setAnalyzerid( this.analyzerid );
		analyzerEntity.setName( this.name );
		analyzerEntity.setAnalyzerRegistries(this.analyzerRegistries != null ?
				this.analyzerRegistries.stream().map(AnalyzerRegistryObject::toEntity).collect(Collectors.toList()) : null);
		
		analyzerEntity.setAnalyzerRegistriesAverage(this.analyzerAverageRegistries != null ?
				this.analyzerAverageRegistries.stream().map(AnalyzerRegistryAverageObject::toEntity).collect(Collectors.toList()) : null);
		return analyzerEntity;
	}
	
    public void addAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject) {
    	if(analyzerRegistries == null) {
    		analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();
    	}
    	
    	analyzerRegistries.add(analyzerRegistryObject);
    }
    
    public void addAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryObject) {
    	if(analyzerAverageRegistries == null) {
    		analyzerAverageRegistries = new ArrayList<AnalyzerRegistryAverageObject>();
    	}
    	
    	analyzerAverageRegistries.add(analyzerRegistryObject);
    }

	public long getAnalyzerid() {
		return analyzerid;
	}

	public void setAnalyzerid( long analyzerid ) {
		this.analyzerid = analyzerid;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public List<AnalyzerRegistryObject> getAnalyzerRegistries() {
		return analyzerRegistries;
	}

	public void setAnalyzerRegistries(List<AnalyzerRegistryObject> analyzerRegistries) {
		this.analyzerRegistries = analyzerRegistries;
	}

	public List<AnalyzerRegistryAverageObject> getAnalyzerAverageRegistries() {
		return analyzerAverageRegistries;
	}

	public void setAnalyzerAverageRegistries(List<AnalyzerRegistryAverageObject> analyzerAverageRegistries) {
		this.analyzerAverageRegistries = analyzerAverageRegistries;
	}

	
}
