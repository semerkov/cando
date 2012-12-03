package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.appspot.i_can_do.master.security.Permission;
import com.google.appengine.api.datastore.Key;

@Entity
public class ProfileKeeper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private Key profileKey;
	@Enumerated(EnumType.STRING)
	private Permission permission;
	
	public ProfileKeeper(){
		
	}
	public ProfileKeeper(Key userKey, Key profileKey, Permission permission){
		this.userKey = userKey;
		this.profileKey = profileKey;
		this.permission = permission;
	}
	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public Key getProfileKey() {
		return profileKey;
	}
	public void setProfileKey(Key profileKey) {
		this.profileKey = profileKey;
	}
	public Key getKey(){
		return key;
	}
	
}
