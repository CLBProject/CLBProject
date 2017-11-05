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
	private long buildingid;

	private String name;

	//bi-directional many-to-one association to Usersystem
	@OneToMany(mappedBy="building")
	private List<UsersystemEntity> usersystems;

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

	public List<UsersystemEntity> getUsersystems() {
		return this.usersystems;
	}

	public void setUsersystems(List<UsersystemEntity> usersystems) {
		this.usersystems = usersystems;
	}

	public UsersystemEntity addUsersystem(UsersystemEntity usersystem) {
		getUsersystems().add(usersystem);
		usersystem.setBuilding(this);

		return usersystem;
	}

	public UsersystemEntity removeUsersystem(UsersystemEntity usersystem) {
		getUsersystems().remove(usersystem);
		usersystem.setBuilding(null);

		return usersystem;
	}

}