package com.appspot.i_can_do.master.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.appspot.i_can_do.master.security.PhoneType;
import com.google.appengine.api.datastore.Key;

@Entity
public class Phone implements Serializable {
	private static final long serialVersionUID = -4244406395900605021L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private PhoneType type;

	public Phone(){
		
	}
	public Phone(String phoneNumber, PhoneType type){
		this.phoneNumber = phoneNumber;
		this.type = type;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public Key getKey() {
		return key;
	}
}
