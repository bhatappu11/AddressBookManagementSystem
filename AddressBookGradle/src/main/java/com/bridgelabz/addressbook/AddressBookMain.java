package com.bridgelabz.addressbook;

import java.util.Scanner;

public class AddressBookMain {
	private static AddressBook[] addressBooks = new AddressBook[20];
	private static String[] addressBookName = new String[10];
	private static int numOfAddressBook =0;
	
	private static void addressMenu(AddressBook addressBook) {
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
				System.out.println("Enter the contact phone number to edit");
				addressBook.editContact();
				break;
			case 3:
				System.out.println("Enter phone number to be deleted: ");
				addressBook.deleteContact();
				break;
			default:
				exit = false;

			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book System");
		Scanner sc = new Scanner(System.in);
		AddressBook currentBook;
		boolean exit1 = true;
		while(exit1) {
			System.out.println("Select option 1:Add address Book 2:open Address Book 3:Exit");
			switch(sc.nextInt()) {
			case 1: 
				System.out.println("Enter the address book name");
				String name = sc.next();
				currentBook  = new AddressBook();
				addressBooks[numOfAddressBook] = currentBook;
				addressBookName[numOfAddressBook] = name;
				numOfAddressBook++;
				break;
			case 2:
				System.out.println("The Address books available :");
				for(int i=0;i<numOfAddressBook;i++) {
					System.out.println(addressBookName[i]);
				}
				System.out.println("Enter the name");
				String book = sc.next();
				int i =0;
				for(i=0;i<numOfAddressBook;i++) {
					if(addressBookName[i].equals(book)) break;
				}
				if(i == numOfAddressBook) {
					System.out.println("Name Not Found");
					break;
				}
				currentBook = addressBooks[i];
				addressMenu(currentBook);
				break;
			default:
				exit1 = false;

			}
	}

	}
}
