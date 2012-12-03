package com.appspot.i_can_do.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

@Entity
public class Profile implements Serializable {
	private static final long serialVersionUID = -1015612055948091246L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String MimeType;  // Useful to store the mime type in case you want to send it back via a servlet.
	@Basic(fetch=FetchType.LAZY) // this gets ignored anyway, but it is recommended for blobs
	private  byte[]  imageFile;
	private String about;
	
	private String name;
	private String sername;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Address> addresses;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Email> emails;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Phone> phones;
	
	public Profile(){
		addresses = new ArrayList<Address>();
		emails = new ArrayList<Email>();
		phones = new ArrayList<Phone>();
	}
	public String getMimeType() {
		return MimeType;
	}
	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}
	public byte[] getImageFile() {
		return imageFile;
	}
	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
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
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public Key getKey() {
		return key;
	}
	
}
