package clb.business.objects;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject
{

    private long analyzerid;

    private String name;

    private DataLoggerObject dataLogger;
    
    public AnalyzerObject(){
        
    }
    
    public AnalyzerObject(AnalyzerEntity analyzerEntity){
        this.analyzerid = analyzerEntity.getAnalyzerid();
        this.name = analyzerEntity.getName();
        this.dataLogger = analyzerEntity.getDataLogger() != null ? new DataLoggerObject(analyzerEntity.getDataLogger()) : new DataLoggerObject();
    }
    
    public AnalyzerEntity toEntity(){
        AnalyzerEntity analyzerEntity = new AnalyzerEntity();
        analyzerEntity.setAnalyzerid( this.analyzerid );
        analyzerEntity.setName( this.name );
        analyzerEntity.setDataLogger( this.dataLogger != null ? this.dataLogger.toEntity() : null );
        return analyzerEntity;
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

    public DataLoggerObject getDataLogger() {
        return dataLogger;
    }

    public void setDataLogger( DataLoggerObject dataLogger ) {
        this.dataLogger = dataLogger;
    }
    
}
