package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BUILDING database table.
 * 
 */
@Entity
@NamedQuery(name="Building.findAll", query="SELECT b FROM Building b")
public class Building implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long buildingid;

	private String name;

	//bi-directional many-to-one association to Usersystem
	@OneToMany(mappedBy="building")
	private List<Usersystem> usersystems;

	public Building() {
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

	public List<Usersystem> getUsersystems() {
		return this.usersystems;
	}

	public void setUsersystems(List<Usersystem> usersystems) {
		this.usersystems = usersystems;
	}

	public Usersystem addUsersystem(Usersystem usersystem) {
		getUsersystems().add(usersystem);
		usersystem.setBuilding(this);

		return usersystem;
	}

	public Usersystem removeUsersystem(Usersystem usersystem) {
		getUsersystems().remove(usersystem);
		usersystem.setBuilding(null);

		return usersystem;
	}

}