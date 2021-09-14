package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AddressBook {
	private HashMap<String,Contact> addressBook = new HashMap<String,Contact>();
	Scanner sc=new Scanner(System.in);
	public void editContact() {
		System.out.println("Enter the first name of the contact to edit");
		String firstName = sc.nextLine();
		Contact contact = addressBook.get(firstName.toLowerCase());
		System.out.println("Select the options \t 1.first name \t 2.last name \t 3.city \n 4.state \t 5.zip \t 6.phone number \t 7.email");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			System.out.println("enter new first name");
			String newFirstName = sc.next();
			addressBook.remove(firstName);
			contact.setFirstName(newFirstName);
			addressBook.put(newFirstName, contact);
			break;
		case 2:
			System.out.println("Enter new last name");
			String newLastName=sc.next();
			contact.setLastName(newLastName);
			break;
		case 3:
			System.out.println("Enter new city");
			contact.setCity(sc.next());
			break;
		case 4:
			System.out.println("Enter new state");
			contact.setState(sc.next());
			break;
		case 5:
			System.out.println("Enter new zip");
			contact.setZip(sc.next());
			break;
		case 6:
			System.out.println("Enter new phone number");
			contact.setPhoneNumber(sc.next());
			break;
		case 7:
			System.out.println("Enter new email");
			contact.setEmail(sc.next());
			break;
		default:
			System.err.println("Invalid Option");
			
		}
		System.out.println("Editing done, the new details are: ");
		System.out.println(contact.getFirstName()+" "+contact.getLastName()+" "+contact.getCity()+" "+contact.getState()+" "+contact.getZip()+" "+contact.getPhoneNumber()+" "+contact.getEmail());
		
	}
	public void addContact() {
		System.out.println("Enter contact details");
		System.out.println("Enter first name");
		String firstName = sc.nextLine();
		System.out.println("Enter last name");
		String lastName = sc.nextLine();
		System.out.println("Enter city");
		String city = sc.nextLine();
		System.out.println("Enter state");
		String state = sc.nextLine();
		System.out.println("Enter zip");
		String zip = sc.nextLine();
		System.out.println("Enter phone number");
		String phoneNumber = sc.nextLine();
		System.out.println("Enter email");
		String email = sc.nextLine();
		
		addressBook.put(firstName,new Contact(firstName,lastName,city,state,zip,phoneNumber,email));
		sc.close();
	}
}
