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
@NamedQuery(name="DataLogger.findAll", query="SELECT d FROM DataLogger d")
public class DataLogger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long dataloggerid;

	private String name;

	//bi-directional many-to-one association to Analyzer
	@OneToMany(mappedBy="dataLogger")
	private List<Analyzer> analyzers;

	//bi-directional many-to-one association to Usersystem
	@ManyToOne
	@JoinColumn(name="USERID")
	private Usersystem usersystem;

	public DataLogger() {
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

	public List<Analyzer> getAnalyzers() {
		return this.analyzers;
	}

	public void setAnalyzers(List<Analyzer> analyzers) {
		this.analyzers = analyzers;
	}

	public Analyzer addAnalyzer(Analyzer analyzer) {
		getAnalyzers().add(analyzer);
		analyzer.setDataLogger(this);

		return analyzer;
	}

	public Analyzer removeAnalyzer(Analyzer analyzer) {
		getAnalyzers().remove(analyzer);
		analyzer.setDataLogger(null);

		return analyzer;
	}

	public Usersystem getUsersystem() {
		return this.usersystem;
	}

	public void setUsersystem(Usersystem usersystem) {
		this.usersystem = usersystem;
	}

}