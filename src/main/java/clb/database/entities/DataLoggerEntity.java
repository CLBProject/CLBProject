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
public class DataLoggerEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long dataloggerid;

	private String name;
	
	private String ftpaddress;
	
	@DBRef
	private List<AnalyzerEntity> analyzers;

	public DataLoggerEntity() {
	}

	public long getDataloggerid() {
		return this.dataloggerid;
	}

	public void setDataloggerid(long dataloggerid) {
		this.dataloggerid = dataloggerid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFtpaddress() {
		return this.ftpaddress;
	}

	public void setFtpaddress(String ftpaddress) {
		this.ftpaddress = ftpaddress;
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