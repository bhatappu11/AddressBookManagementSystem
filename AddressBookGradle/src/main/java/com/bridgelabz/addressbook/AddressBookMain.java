package com.bridgelabz.addressbook;

import java.util.Scanner;

public class AddressBookMain {
	public static AddressBookList addressBookList = new AddressBookList();
	
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book System");
		AddressBookMain addressBookMain = new AddressBookMain();
		Scanner sc = new Scanner(System.in);
		boolean exit1 = true;
		while(exit1) {
			System.out.println("Select option 1:Add address Book 2:open Address Book 3:Exit");
			switch(sc.nextInt()) {
			case 1: 
				System.out.println("Enter the address book name");
				String name = sc.next();
				AddressBook addressBook = new AddressBook();
				addressBookList.add(name, addressBook);
				break;
			case 2:
				System.out.println("Enter the name of Address Book");
				String book = sc.next();
				if(addressBookList.get(book)==null)
					System.out.println("Address Book does not exist");
				else {
					AddressBook bookName = addressBookList.get(book);
					addressBookList.addressMenu(bookName);
				}
				break;
			default:
				exit1 = false;

			}
		}

	}
	
}
