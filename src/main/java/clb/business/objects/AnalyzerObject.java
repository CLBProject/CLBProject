package clb.business.objects;

import clb.database.entities.AnalyzerEntity;

public class AnalyzerObject
{
    private String analyzerid;

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

    public String getAnalyzerid() {
        return analyzerid;
    }

    public void setAnalyzerid( String analyzerid ) {
        this.analyzerid = analyzerid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
    
}
