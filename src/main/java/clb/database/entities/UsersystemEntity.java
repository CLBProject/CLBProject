package clb.database.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the USERSYSTEM database table.
 * 
 */
@Entity
@Table(name="USERSYSTEM")
@NamedQuery(name="Usersystem.findAll", query="SELECT u FROM UsersystemEntity u")
public class UsersystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String userid;

	private String address;

	private String name;

	private String password;

	private String username;
	

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
}