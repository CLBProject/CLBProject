package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USERSYSTEM database table.
 * 
 */
@Entity
@NamedQuery(name="Usersystem.findAll", query="SELECT u FROM UsersystemEntity u")
public class UsersystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String userid;

	private String address;

	private String name;

	private String password;

	private String username;

	//bi-directional many-to-one association to DataLogger
	@OneToMany(mappedBy="usersystem")
	private List<DataLoggerEntity> dataLoggers;

	//bi-directional many-to-one association to Building
	@ManyToOne
	@JoinColumn(name="BUILDINGID")
	private BuildingEntity building;

	public UsersystemEntity() {
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<DataLoggerEntity> getDataLoggers() {
		return this.dataLoggers;
	}

	public void setDataLoggers(List<DataLoggerEntity> dataLoggers) {
		this.dataLoggers = dataLoggers;
	}

	public DataLoggerEntity addDataLogger(DataLoggerEntity dataLogger) {
		getDataLoggers().add(dataLogger);
		dataLogger.setUsersystem(this);

		return dataLogger;
	}

	public DataLoggerEntity removeDataLogger(DataLoggerEntity dataLogger) {
		getDataLoggers().remove(dataLogger);
		dataLogger.setUsersystem(null);

		return dataLogger;
	}

	public BuildingEntity getBuilding() {
		return this.building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}

}