package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Entity
@Table(name="ANALYZER")
@NamedQuery(name="Analyzer.findAll", query="SELECT a FROM AnalyzerEntity a")
public class AnalyzerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long analyzerid;

	private String name;

	//bi-directional many-to-one association to DataLogger
	@ManyToOne
	@JoinColumn(name="DATALOGGERID")
	private DataLoggerEntity dataLogger;

	//bi-directional many-to-one association to AnalyzerRegistry
	@OneToMany(mappedBy="analyzer")
	private List<AnalyzerRegistryEntity> analyzerRegistries;

	//bi-directional many-to-one association to AnalyzerRegistryTemp
	@OneToMany(mappedBy="analyzer")
	private List<AnalyzerRegistryTempEntity> analyzerRegistryTemps;

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

	public DataLoggerEntity getDataLogger() {
		return this.dataLogger;
	}

	public void setDataLogger(DataLoggerEntity dataLogger) {
		this.dataLogger = dataLogger;
	}

	public List<AnalyzerRegistryEntity> getAnalyzerRegistries() {
		return this.analyzerRegistries;
	}

	public void setAnalyzerRegistries(List<AnalyzerRegistryEntity> analyzerRegistries) {
		this.analyzerRegistries = analyzerRegistries;
	}

	public AnalyzerRegistryEntity addAnalyzerRegistry(AnalyzerRegistryEntity analyzerRegistry) {
		getAnalyzerRegistries().add(analyzerRegistry);
		analyzerRegistry.setAnalyzer(this);

		return analyzerRegistry;
	}

	public AnalyzerRegistryEntity removeAnalyzerRegistry(AnalyzerRegistryEntity analyzerRegistry) {
		getAnalyzerRegistries().remove(analyzerRegistry);
		analyzerRegistry.setAnalyzer(null);

		return analyzerRegistry;
	}

	public List<AnalyzerRegistryTempEntity> getAnalyzerRegistryTemps() {
		return this.analyzerRegistryTemps;
	}

	public void setAnalyzerRegistryTemps(List<AnalyzerRegistryTempEntity> analyzerRegistryTemps) {
		this.analyzerRegistryTemps = analyzerRegistryTemps;
	}

	public AnalyzerRegistryTempEntity addAnalyzerRegistryTemp(AnalyzerRegistryTempEntity analyzerRegistryTemp) {
		getAnalyzerRegistryTemps().add(analyzerRegistryTemp);
		analyzerRegistryTemp.setAnalyzer(this);

		return analyzerRegistryTemp;
	}

	public AnalyzerRegistryTempEntity removeAnalyzerRegistryTemp(AnalyzerRegistryTempEntity analyzerRegistryTemp) {
		getAnalyzerRegistryTemps().remove(analyzerRegistryTemp);
		analyzerRegistryTemp.setAnalyzer(null);

		return analyzerRegistryTemp;
	}

}