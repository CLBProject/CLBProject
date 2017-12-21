package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long analyzerid;

	private String name;

	//bi-directional many-to-one association to DataLogger
	@ManyToOne
	@JoinColumn(name="dataloggerid")
	private DataLoggerEntity dataLogger;

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
}