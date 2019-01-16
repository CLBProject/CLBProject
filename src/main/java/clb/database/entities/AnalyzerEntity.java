package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Document(collection="Analyzers")
public class AnalyzerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String codeName;

    @DBRef
    private List<AnalyzerEntity> analyzers;
    
	@DBRef
	private List<AnalyzerMeterEntity> analyzerMeters;
	
	private List<String> analyzerRegistriesIds;

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
	
	
	
	public List<AnalyzerEntity> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerEntity> analyzers) {
		this.analyzers = analyzers;
	}

	public List<AnalyzerMeterEntity> getAnalyzerMeters() {
		return analyzerMeters;
	}

	public void setAnalyzerMeters(List<AnalyzerMeterEntity> analyzerMeters) {
		this.analyzerMeters = analyzerMeters;
	}

	public List<String> getAnalyzerRegistriesIds() {
		return analyzerRegistriesIds;
	}

	public void setAnalyzerRegistriesIds(List<String> analyzerRegistriesIds) {
		this.analyzerRegistriesIds = analyzerRegistriesIds;
	}

    
    
}