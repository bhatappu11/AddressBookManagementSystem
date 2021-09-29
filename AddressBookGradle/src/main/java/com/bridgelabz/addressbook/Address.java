package com.bridgelabz.addressbook;

public class Address {
	public String id;
	public String addressId;
	public String city;
	public String state;
	public String zip;
	public Address(String id, String addressId, String city, String state, String zip) {
		this.id = id;
		this.addressId = addressId;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
	
}
