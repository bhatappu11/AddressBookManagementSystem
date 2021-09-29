package com.bridgelabz.addressbook;

public class Contact implements Comparable<Contact>{
	public String firstName;
	public String lastName;
	public String city;
	public String state;
	public String zip;
	public String phoneNumber;
	public String email;
	public String id;
	public AddressBookList bookID;
	public String getId() {
		return id;
	}

	public AddressBookList getBookID() {
		return bookID;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBookID(AddressBookList bookID) {
		this.bookID = bookID;
	}

	
	
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

	public Contact(String id, String firstName2, String lastName2, String phoneNumber2, String email2, AddressBookList addressBookList) {
		this.id = id;
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.phoneNumber = phoneNumber2;
		this.email = email2;
		this.bookID = addressBookList;
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
		return "Contact [firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email=" + email + ", id=" + id + ", bookID="
				+ bookID + "]";
	}

	@Override
    public boolean equals(Object object) {
    	if(object == this)  return true;
    	if(!(object instanceof Contact)) return false;
    	Contact person1 = (Contact) object;
    	return (this.firstName.equals(person1.firstName)  && this.lastName.equals(person1.lastName) && this.phoneNumber.equals(person1.phoneNumber)); 
	}
}
