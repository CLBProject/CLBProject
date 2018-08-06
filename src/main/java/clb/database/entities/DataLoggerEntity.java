package clb.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the DATA_LOGGER database table.
 * 
 */

@Document(collection="DataLoggers")
public class DataLoggerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String dataloggerid;

	private String name;
	
	@DBRef
	private List<AnalyzerEntity> analyzers;

	public DataLoggerEntity() {
	}

	public String getDataloggerid() {
		return this.dataloggerid;
	}

	public void setDataloggerid(String dataloggerid) {
		this.dataloggerid = dataloggerid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<AnalyzerEntity> getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers( List<AnalyzerEntity> analyzers ) {
        this.analyzers = analyzers;
    }
	
	public void addAnalyzer(AnalyzerEntity analyzer) {
		if(this.analyzers == null) {
			analyzers = new ArrayList<AnalyzerEntity>();
		}
		
		analyzers.add(analyzer);
	}
}