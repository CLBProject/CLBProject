package clb.business.objects;

import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;

public class AnalyzerObject
{

    private long analyzerid;

    private String name;

    private DataLoggerObject dataLogger;

    private List<AnalyzerRegistryObject> analyzerRegistries;
    
    public AnalyzerObject(){
        
    }
    
    public AnalyzerObject(AnalyzerEntity analyzerEntity){
        this.analyzerid = analyzerEntity.getAnalyzerid();
        this.name = analyzerEntity.getName();
        this.dataLogger = new DataLoggerObject(analyzerEntity.getDataLogger());
        this.analyzerRegistries = analyzerEntity.getAnalyzerRegistries() != null ?
        		analyzerEntity.getAnalyzerRegistries().stream().map(AnalyzerRegistryObject::new).collect(Collectors.toList()) : null;
    }
    
    public AnalyzerEntity toEntity(){
        AnalyzerEntity analyzerEntity = new AnalyzerEntity();
        analyzerEntity.setAnalyzerid( this.analyzerid );
        analyzerEntity.setName( this.name );
        analyzerEntity.setDataLogger( this.dataLogger.toEntity() );
        analyzerEntity.setAnalyzerRegistries( this.analyzerRegistries != null ?
        		this.analyzerRegistries.stream().map( AnalyzerRegistryObject::toEntity).collect(Collectors.toList()) : null);
        
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

    public List<AnalyzerRegistryObject> getAnalyzerRegistries() {
        return analyzerRegistries;
    }

    public void setAnalyzerRegistries( List<AnalyzerRegistryObject> analyzerRegistries ) {
        this.analyzerRegistries = analyzerRegistries;
    }
    
    
}
