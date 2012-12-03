package com.appspot.i_can_do.master.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.appspot.i_can_do.master.security.AddressEmailType;
import com.google.appengine.api.datastore.Key;

@Entity
public class Email implements Serializable  {
	private static final long serialVersionUID = -7279688582402660068L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String email;
	@Enumerated(EnumType.STRING)
	private AddressEmailType type;
	
	public Email(){
	}
	public Email(String email, AddressEmailType type){
		this.email = email;
		this.type = type;
	}
	
	public AddressEmailType getType() {
		return type;
	}
	public void setType(AddressEmailType type) {
		this.type = type;
	}
	public Key getKey() {
		return key;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
