package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the BUILDING database table.
 * 
 */
@Entity
@Table(name="BUILDING")
@NamedQueries({
	@NamedQuery(name="Building.findAll", query="SELECT b FROM BuildingEntity b"),
	@NamedQuery(name="Building.findByUsername", query = "select b from BuildingEntity b where b.buildingusername=:busername")
})

public class BuildingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long buildingid;

	private String name;
	
	private String buildingusername;

	//bi-directional many-to-one association to Usersystem
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="userid")
	private UsersystemEntity usersystem;
	
	//bi-directional many-to-one association to DataLoggerEntity
	@OneToMany(mappedBy="building")
	private List<DataLoggerEntity> DataLoggerEntitys;

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

	public String getBuildingusername() {
		return this.buildingusername;
	}

	public void setBuildingusername(String buildingusername) {
		this.buildingusername = buildingusername;
	}
	
	public List<DataLoggerEntity> getDataLoggerEntitys() {
		return this.DataLoggerEntitys;
	}

	public void setDataLoggerEntitys(List<DataLoggerEntity> DataLoggerEntitys) {
		this.DataLoggerEntitys = DataLoggerEntitys;
	}

}