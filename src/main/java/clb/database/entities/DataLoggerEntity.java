package clb.database.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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
	
	private String ftpaddress;

	//bi-directional many-to-one association to Building
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="buildingid")
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

	public BuildingEntity getBuilding() {
		return this.building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}

	public String getFtpaddress() {
		return this.ftpaddress;
	}

	public void setFtpaddress(String ftpaddress) {
		this.ftpaddress = ftpaddress;
	}
}