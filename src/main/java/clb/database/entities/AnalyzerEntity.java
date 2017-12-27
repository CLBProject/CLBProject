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
@Document
public class AnalyzerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long analyzerid;

	private String name;
	
	@DBRef
	private List<DataLoggerEntity> analyzerRegistries;

	public AnalyzerEntity() {
	}

	public long getAnalyzerid() {
		return this.analyzerid;
	}

	public void setAnalyzerid(long analyzerid) {
		this.analyzerid = analyzerid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<DataLoggerEntity> getAnalyzerRegistries() {
        return analyzerRegistries;
    }

    public void setAnalyzerRegistries( List<DataLoggerEntity> analyzerRegistries ) {
        this.analyzerRegistries = analyzerRegistries;
    }
	
	
}