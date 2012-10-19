package com.appspot.i_can_do.master.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
		@NamedQuery(name = "User.getUsers", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.getUser", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password"), })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String sername;
	private String email;
	private String password;
	private int timeZone;

	@Transient
	public static final User NULL_USER = new User();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSername() {
		return sername;
	}

	public void setSername(String sername) {
		this.sername = sername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

}
