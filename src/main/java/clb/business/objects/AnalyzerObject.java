package clb.business.objects;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject
{
    private long analyzerid;

    private String name;
    
    public AnalyzerObject(){
        
    }
    
    public AnalyzerObject(AnalyzerEntity analyzerEntity){
        this.analyzerid = analyzerEntity.getAnalyzerid();
        this.name = analyzerEntity.getName();
    }
    
    public AnalyzerEntity toEntity(){
        AnalyzerEntity analyzerEntity = new AnalyzerEntity();
        analyzerEntity.setAnalyzerid( this.analyzerid );
        analyzerEntity.setName( this.name );
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
    
}
