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
@NamedQuery(name="DataLoggerEntity.findAll", query="SELECT d FROM DataLoggerEntity d")
public class DataLoggerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long dataloggerid;

	private String name;

	//bi-directional many-to-one association to Analyzer
	@OneToMany(mappedBy="dataLogger")
	private List<AnalyzerEntity> analyzers;

	//bi-directional many-to-one association to Usersystem
	@ManyToOne
	@JoinColumn(name="USERID")
	private UsersystemEntity usersystem;

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

	public UsersystemEntity getUsersystem() {
		return this.usersystem;
	}

	public void setUsersystem(UsersystemEntity usersystem) {
		this.usersystem = usersystem;
	}

}