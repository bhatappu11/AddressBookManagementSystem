package com.bridgelabz.addressbook;

public class Contact implements Comparable<Contact>{
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	private String email;
	
	public Contact(String firstName, String lastName, String city, String state, String zip, String phoneNumber,
			String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int compareTo(Contact c) {
		return this.firstName.compareTo(c.getFirstName());
	}

	@Override
	public String toString() {
		return "Contact [FirstName=" + firstName + ", LastName=" + lastName + ", City=" + city + ", State=" + state
				+ ", Zip=" + zip + ", PhoneNumber=" + phoneNumber + ", Email=" + email + "]";
	}
	
}
