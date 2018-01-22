package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.DataLoggerEntity;

public class DataLoggerObject
{

    private long dataloggerid;

    private String name;
    
    private String ftpAddress;
    
    private List<AnalyzerObject> analyzers;

    public DataLoggerObject(){
        
    }

    public DataLoggerObject( DataLoggerEntity dataLogger ) {
        this.dataloggerid = dataLogger.getDataloggerid();
        this.name = dataLogger.getName();
        this.ftpAddress = dataLogger.getFtpaddress();
        this.analyzers = dataLogger.getAnalyzers() != null ? 
        		dataLogger.getAnalyzers().stream().map(AnalyzerObject::new).collect(Collectors.toList()) : null;
    }
    
    public DataLoggerEntity toEntity() {
        DataLoggerEntity dataLogEntity = new DataLoggerEntity();
        dataLogEntity.setDataloggerid( this.dataloggerid );
        dataLogEntity.setFtpaddress(this.ftpAddress);
        dataLogEntity.setAnalyzers( this.analyzers != null ? 
        		this.analyzers.stream().map(AnalyzerObject::toEntity).collect(Collectors.toList()) : null);
        return dataLogEntity;
    }
    
    public void addAnalyzer(AnalyzerObject analyzerObject) {
    	if(analyzers == null) {
    		analyzers = new ArrayList<AnalyzerObject>();
    	}
    	
    	analyzers.add(analyzerObject);
    }


    public long getDataloggerid() {
        return dataloggerid;
    }


    public void setDataloggerid( long dataloggerid ) {
        this.dataloggerid = dataloggerid;
    }


    public String getName() {
        return name;
    }


    public void setName( String name ) {
        this.name = name;
    }

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public List<AnalyzerObject> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerObject> analyzers) {
		this.analyzers = analyzers;
	}
    
    
}
