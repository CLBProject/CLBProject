package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ANALYZER database table.
 * 
 */
@Entity
@NamedQuery(name="Analyzer.findAll", query="SELECT a FROM Analyzer a")
public class Analyzer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long analyzerid;

	private String name;

	//bi-directional many-to-one association to DataLogger
	@ManyToOne
	@JoinColumn(name="DATALOGGERID")
	private DataLogger dataLogger;

	//bi-directional many-to-one association to AnalyzerRegistry
	@OneToMany(mappedBy="analyzer")
	private List<AnalyzerRegistry> analyzerRegistries;

	public Analyzer() {
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

	public DataLogger getDataLogger() {
		return this.dataLogger;
	}

	public void setDataLogger(DataLogger dataLogger) {
		this.dataLogger = dataLogger;
	}

	public List<AnalyzerRegistry> getAnalyzerRegistries() {
		return this.analyzerRegistries;
	}

	public void setAnalyzerRegistries(List<AnalyzerRegistry> analyzerRegistries) {
		this.analyzerRegistries = analyzerRegistries;
	}

	public AnalyzerRegistry addAnalyzerRegistry(AnalyzerRegistry analyzerRegistry) {
		getAnalyzerRegistries().add(analyzerRegistry);
		analyzerRegistry.setAnalyzer(this);

		return analyzerRegistry;
	}

	public AnalyzerRegistry removeAnalyzerRegistry(AnalyzerRegistry analyzerRegistry) {
		getAnalyzerRegistries().remove(analyzerRegistry);
		analyzerRegistry.setAnalyzer(null);

		return analyzerRegistry;
	}

}