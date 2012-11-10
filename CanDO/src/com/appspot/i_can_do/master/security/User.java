package com.appspot.i_can_do.master.security;

import java.util.Date;

import javax.persistence.Column;
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
		@NamedQuery(name = "User.getUser", query = "SELECT u FROM User u WHERE u.email = :userEmail"), })
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String email;
	private String name;
	private String sername;
	private Date lastLogin;
	private int timeZone;
	private boolean disabled;
	@Column(nullable = false)
	private byte[] passwordHash;
	 
	@Transient
	public static final User NULL_USER = new User();

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

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
