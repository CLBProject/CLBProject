package clb.database.entities;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    @DBRef
    private Set<AnalyzerEntity> analyzers;

	private Set<AnalyzerMeterValues> analyzerMeters;
	
	private Set<String> analyzerRegistriesIds;

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
	
	
	
	public Set<AnalyzerEntity> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(Set<AnalyzerEntity> analyzers) {
		this.analyzers = analyzers;
	}

	public Set<AnalyzerMeterValues> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(Set<AnalyzerMeterValues> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	public Set<String> getAnalyzerRegistriesIds() {
		return analyzerRegistriesIds;
	}

	public void setAnalyzerRegistriesIds(Set<String> analyzerRegistriesIds) {
		this.analyzerRegistriesIds = analyzerRegistriesIds;
	}

    
    
}