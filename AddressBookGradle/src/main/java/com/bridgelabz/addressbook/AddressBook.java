package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AddressBook {
	private Contact[] contacts = new Contact[20];
	Scanner sc = new Scanner(System.in);
	private int numOfContacts = 0;
	
	public void editContact() {
		System.out.println("Enter the phone number of the contact to edit");
		String phoneNum = sc.next();
		Contact contactToChange = null;
		for(Contact contact : contacts) {
			if(contact.getPhoneNumber().contentEquals(phoneNum)) {
				contactToChange = contact;
				break;
			}
		}System.out.println("Select the options \t 1.first name \t 2.last name \t 3.city \n 4.state \t 5.zip \t 6.phone number \t 7.email");
		int option = sc.nextInt();
		switch(option) {
		case 1:
			System.out.println("enter new first name");
			String newFirstName = sc.next();
			contactToChange.setFirstName(newFirstName);
			break;
		case 2:
			System.out.println("Enter new last name");
			String newLastName=sc.next();
			contactToChange.setLastName(newLastName);
			break;
		case 3:
			System.out.println("Enter new city");
			contactToChange.setCity(sc.next());
			break;
		case 4:
			System.out.println("Enter new state");
			contactToChange.setState(sc.next());
			break;
		case 5:
			System.out.println("Enter new zip");
			contactToChange.setZip(sc.next());
			break;
		case 6:
			System.out.println("Enter new phone number");
			contactToChange.setPhoneNumber(sc.next());
			break;
		case 7:
			System.out.println("Enter new email");
			contactToChange.setEmail(sc.next());
			break;
		default:
			System.err.println("Invalid Option");
			
		}
		System.out.println("Editing done, the new details are: ");
		System.out.println(contactToChange.getFirstName()+" "+contactToChange.getLastName()+" "+contactToChange.getCity()+" "+contactToChange.getState()+" "+contactToChange.getZip()+" "+contactToChange.getPhoneNumber()+" "+contactToChange.getEmail());
		
	}
	public void deleteContact() {
		System.out.println("Enter the phone num of the contact to delete");
		String phone = sc.next();
		int index = 0;
		for(Contact contact: contacts) {
			index++;
			if(contact.getPhoneNumber().equals(phone))
				break;
		}
		for(;index < numOfContacts-1;index++) {
			contacts[index] = contacts[index+1];
		}
		System.out.println("contact deleted");
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
		
		contacts[numOfContacts] = new Contact(firstName,lastName,city,state,zip,phoneNumber,email);
		numOfContacts++;
	}
}
