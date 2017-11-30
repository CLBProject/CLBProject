package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BUILDING database table.
 * 
 */
@Entity
@NamedQuery(name="Building.findAll", query="SELECT b FROM BuildingEntity b")
public class BuildingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long buildingid;

	private String name;

	//bi-directional many-to-one association to Usersystem
	@ManyToOne
	@JoinColumn(name="USERID")
	private UsersystemEntity usersystem;

	//bi-directional many-to-one association to DataLogger
	@OneToMany(mappedBy="building")
	private List<DataLoggerEntity> dataLoggers;

	public BuildingEntity() {
	}

	public long getBuildingid() {
		return this.buildingid;
	}

	public void setBuildingid(long buildingid) {
		this.buildingid = buildingid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UsersystemEntity getUsersystem() {
		return this.usersystem;
	}

	public void setUsersystem(UsersystemEntity usersystem) {
		this.usersystem = usersystem;
	}

	public List<DataLoggerEntity> getDataLoggers() {
		return this.dataLoggers;
	}

	public void setDataLoggers(List<DataLoggerEntity> dataLoggers) {
		this.dataLoggers = dataLoggers;
	}

	public DataLoggerEntity addDataLogger(DataLoggerEntity dataLogger) {
		getDataLoggers().add(dataLogger);
		dataLogger.setBuilding(this);

		return dataLogger;
	}

	public DataLoggerEntity removeDataLogger(DataLoggerEntity dataLogger) {
		getDataLoggers().remove(dataLogger);
		dataLogger.setBuilding(null);

		return dataLogger;
	}

}