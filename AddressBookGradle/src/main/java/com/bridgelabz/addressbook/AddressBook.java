package com.bridgelabz.addressbook;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class AddressBook {
	private List<Contact> contacts;
	Scanner sc = new Scanner(System.in);
	private int numOfContacts = 0;
	
	public AddressBook() {
		this.contacts = new LinkedList<Contact>();
		this.numOfContacts = 0;
	}

	public void editContact() {
		System.out.println("Enter first name of person you want edit:");
		String firstName = sc.next();
		Contact contactToChange = contacts.stream().filter(c -> c.getFirstName().equals(firstName)).findFirst().orElse(null);
		if(contactToChange == null) {
			System.out.println("contact does not exist");
			return;
		}
		System.out.println("Select the options \t 1.first name \t 2.last name \t 3.city \n 4.state \t 5.zip \t 6.phone number \t 7.email");
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
		System.out.println("Enter first name number of person you want to delete:");
		String firstName = sc.next();
		for(int i=0;i<contacts.size();i++) {
			if(contacts.get(i).getFirstName().equals(firstName)) {
				contacts.remove(i);
				System.out.println("Successfully Deleted");
				return;
			}
		}
	}
	
	public void addContact() {
		System.out.println("Enter contact details");
		System.out.println("Enter first name");
		String firstName = sc.next();
		System.out.println("Enter last name");
		String lastName = sc.next();
		System.out.println("Enter city");
		String city = sc.next();
		System.out.println("Enter state");
		String state = sc.next();
		System.out.println("Enter zip");
		String zip = sc.next();
		System.out.println("Enter phone number");
		String phoneNumber = sc.next();
		System.out.println("Enter email");
		String email = sc.next();
		Contact contact = new Contact(firstName, lastName, city, state, zip, phoneNumber, email);
		if(!checkIfContactExists(contact)) {
			contacts.add(contact);
		}
		else {
			System.out.println("Duplicate");
		}
	}
	
	private boolean checkIfContactExists(Contact contact) {
		return contacts.stream().filter(c -> c.equals(contact)).findFirst().orElse(null) != null;
	}


}
