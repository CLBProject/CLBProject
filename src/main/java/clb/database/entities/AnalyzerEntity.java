package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Document(collection="Analyzers")
public class AnalyzerEntity implements ClbEntity, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    private String name;

    private List<String> analyzerRegistriesIds;

    private List<String> analyzerRegistriesAverageIds;

    public AnalyzerEntity() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAnalyzerRegistriesIds() {
        return analyzerRegistriesIds;
    }

    public void setAnalyzerRegistriesIds( List<String> analyzerRegistriesIds ) {
        this.analyzerRegistriesIds = analyzerRegistriesIds;
    }

    public List<String> getAnalyzerRegistriesAverageIds() {
        return analyzerRegistriesAverageIds;
    }

    public void setAnalyzerRegistriesAverageIds( List<String> analyzerRegistriesAverageIds ) {
        this.analyzerRegistriesAverageIds = analyzerRegistriesAverageIds;
    }
    
}