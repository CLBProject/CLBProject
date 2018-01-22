package clb.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Document(collection="Analyzers")
public class AnalyzerEntity implements ClbEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private long analyzerid;

    private String name;

    @DBRef
    private List<AnalyzerRegistryEntity> analyzerRegistries;

    @DBRef
    private List<AnalyzerRegistryAverageEntity> analyzerRegistriesAverage;

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

    public List<AnalyzerRegistryEntity> getAnalyzerRegistries() {
        return analyzerRegistries;
    }

    public void setAnalyzerRegistries( List<AnalyzerRegistryEntity> analyzerRegistries ) {
        this.analyzerRegistries = analyzerRegistries;
    }

    public void addAnalyzerRegistry(AnalyzerRegistryEntity analyzerRegistry) {
        if(this.analyzerRegistries == null)
            analyzerRegistries = new ArrayList<AnalyzerRegistryEntity>();

        analyzerRegistries.add(analyzerRegistry);
    }
    
    public void setAnalyzerRegistriesAverage( List<AnalyzerRegistryAverageEntity> analyzerRegistriesAverage ) {
        this.analyzerRegistriesAverage = analyzerRegistriesAverage;
    }

    public void addAnalyzerRegistryAverage(AnalyzerRegistryAverageEntity analyzerRegistryAverage) {
        if(this.analyzerRegistriesAverage == null)
            analyzerRegistriesAverage = new ArrayList<AnalyzerRegistryAverageEntity>();

        analyzerRegistriesAverage.add(analyzerRegistryAverage);
    }

}