package clb.database.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import clb.global.AnalyzerMeterValues;

/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Document(collection="Analyzers")
public class AnalyzerEntity implements ClbEntity, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String codeName;

	private Set<AnalyzerMeterValues> analyzerMeters;
	
	private Set<String> registriesCollections;

    public AnalyzerEntity() {
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
	

	public Set<AnalyzerMeterValues> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterValues> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	public Set<String> getRegistriesCollections() {
		return registriesCollections;
	}

	public void setRegistriesCollections(Set<String> registriesCollections) {
		this.registriesCollections = registriesCollections;
	}

	public void addRegistryCollection(String currentCollectionName) {
		if (registriesCollections == null) {
			registriesCollections = new HashSet<String>();
		}
		
		registriesCollections.add(currentCollectionName);
	}
	
	
    
}