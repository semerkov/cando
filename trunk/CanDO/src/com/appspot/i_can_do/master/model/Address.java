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
public class Address implements Serializable {
	private static final long serialVersionUID = 1200380128710597897L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String address;
	@Enumerated(EnumType.STRING)
	private AddressEmailType type;
	
	public Address(){
	}
	public Address(String address, AddressEmailType type){
		this.address = address;
		this.type = type;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
}
