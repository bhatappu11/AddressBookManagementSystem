package com.bridgelabz.addressbook;

public class AddressBookType {
	public String typeID;
	public String typeName;
	public AddressBookType(String typeID, String typeName) {
		this.typeID = typeID;
		this.typeName = typeName;
	}
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
