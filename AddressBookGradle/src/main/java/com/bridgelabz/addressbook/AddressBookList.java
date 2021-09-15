package com.bridgelabz.addressbook;

import java.util.HashMap;
import java.util.Scanner;

public class AddressBookList {
	private static HashMap<String, AddressBook> addressBooks;
	public AddressBookList() {
		this.addressBooks = new HashMap<>();
	}
	public void add(String name, AddressBook addressBook) {
		addressBooks.put(name,addressBook);
		
	}
	public AddressBook get(String book) {
		return addressBooks.get(book);
	}
	public void searchAcrossByCity(String city) {
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.findContactInCity(city);
		}
	}
	
	public void searchAcrossByState(String state) {
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.findContactInState(state);
		}
	}
	public static void addressMenu(AddressBook addressBook) {
		Scanner sc = new Scanner(System.in);
		int option = 0;
		boolean exit = true;
		while(exit) {
			System.out.println("Select option \n1: Add Contact.  \n2: Edit Existing Contact. \n3:Delete contact.");
			option  = sc.nextInt();
			switch(option) {
			case 1 :
				addressBook.addContact();
				break;
			case 2 :
				System.out.println("Enter the details to edit");
				addressBook.editContact();
				break;
			case 3:
				System.out.println("Enter the details to edit: ");
				addressBook.deleteContact();
				break;
			default:
				exit = false;

			}
			System.out.println();
		}
	}
	public void sortContacts() {
		
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.sortContacts();
		}
	}
}
