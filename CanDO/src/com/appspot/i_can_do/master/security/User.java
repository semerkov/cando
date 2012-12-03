package com.appspot.i_can_do.master.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.appspot.i_can_do.master.model.Profile;
import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
		@NamedQuery(name = "User.getUsers", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.getUser", query = "SELECT u FROM User u WHERE u.email = :userEmail")})
public class User implements Serializable{
	private static final long serialVersionUID = -3871586450241480038L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String email;
	@OneToOne(cascade=CascadeType.PERSIST)
	private Profile profile;
	private Date lastEntryDate;
	private byte[] rememberCookiesHash;
	private String rememberIpAdress;
	private int timeZone;
	private boolean disabled;
	@Column(nullable = false)
	private byte[] passwordHash;

	public User(){
		profile = new Profile();
	}
	public String getName() {
		return profile.getName();
	}

	public void setName(String name) {
		profile.setName(name);
	}

	public String getSername() {
		return profile.getSername();
	}

	public void setSername(String sername) {
		profile.setSername(sername);
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
	
	public Key getKey(){
		return key;
	}

	public Date getLastEntryDate() {
		return lastEntryDate;
	}

	public void setLastEntryDate(Date lastEntryDate) {
		this.lastEntryDate = lastEntryDate;
	}

	public byte[] getRememberCookiesHash() {
		return rememberCookiesHash;
	}

	public void setRememberCookiesHash(byte[] rememberCookiesHash) {
		this.rememberCookiesHash = rememberCookiesHash;
	}

	public String getRememberIpAdress() {
		return rememberIpAdress;
	}

	public void setRememberIpAdress(String rememberIpAdress) {
		this.rememberIpAdress = rememberIpAdress;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
