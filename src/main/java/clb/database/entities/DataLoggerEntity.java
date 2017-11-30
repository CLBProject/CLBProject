package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DATA_LOGGER database table.
 * 
 */
@Entity
@Table(name="DATA_LOGGER")
@NamedQuery(name="DataLogger.findAll", query="SELECT d FROM DataLoggerEntity d")
public class DataLoggerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dataloggerid;

	private String name;

	//bi-directional many-to-one association to Analyzer
	@OneToMany(mappedBy="dataLogger")
	private List<AnalyzerEntity> analyzers;

	//bi-directional many-to-one association to Building
	@ManyToOne
	@JoinColumn(name="BUILDINGID")
	private BuildingEntity building;

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

	public List<AnalyzerEntity> getAnalyzers() {
		return this.analyzers;
	}

	public void setAnalyzers(List<AnalyzerEntity> analyzers) {
		this.analyzers = analyzers;
	}

	public AnalyzerEntity addAnalyzer(AnalyzerEntity analyzer) {
		getAnalyzers().add(analyzer);
		analyzer.setDataLogger(this);

		return analyzer;
	}

	public AnalyzerEntity removeAnalyzer(AnalyzerEntity analyzer) {
		getAnalyzers().remove(analyzer);
		analyzer.setDataLogger(null);

		return analyzer;
	}

	public BuildingEntity getBuilding() {
		return this.building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}

}