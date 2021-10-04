package com.bridgelabz.addressbook;

public class AddressBookSystem {
	public String bookID;
	public String bookName;
	
	
	public AddressBookSystem(String bookID, String bookName) {
		this.bookID = bookID;
		this.bookName = bookName;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	@Override
	public String toString() {
		return "AddressBookList [bookID=" + bookID + ", bookName=" + bookName + "]";
	}
	
}
