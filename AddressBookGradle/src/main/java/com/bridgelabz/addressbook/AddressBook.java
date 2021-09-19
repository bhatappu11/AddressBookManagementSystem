package com.bridgelabz.addressbook;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.*;

public class AddressBook {
	private List<Contact> contacts;
	private HashMap<String, LinkedList<Contact>> contactsByCity;
	private HashMap<String, LinkedList<Contact>> contactsByState;
	Scanner sc = new Scanner(System.in);
	private int numOfContacts = 0;
	
	public AddressBook() {
		this.contacts = new LinkedList<Contact>();
		this.contactsByCity = new HashMap<>();
		this.contactsByState = new HashMap<>();
		this.numOfContacts = 0;
	}
	public void findContactInCity(String cityName) {
		contacts.stream().filter(c -> c.getCity().equals(cityName)).peek(c -> {
			System.out.println(c.getFirstName()+" : "+cityName);
		});
	}
	public void findContactInState(String stateName) {
		contacts.stream().filter(c -> c.getState().equals(stateName)).peek(c -> {
			System.out.println(c.getFirstName()+" : "+stateName);
		});
	}
	public void sortByName() {
		contacts.stream()
		 .sorted((contact1,contact2) -> contact1.getFirstName().compareTo(contact2.getFirstName()))
		 .forEach(n->{
				System.out.println(n.getFirstName());
			});
	}
	public  void sortByZip() {
		contacts.stream()
					 .sorted((contact1,contact2) -> contact1.getZip().compareTo(contact2.getZip()))
					 .forEach(System.out::println);
	}
	public  void sortByCity() {
		contacts.stream()
					 .sorted((contact1,contact2) -> contact1.getCity().compareTo(contact2.getCity()))
					 .forEach(System.out::println);
	}
	public  void sortByState() {
		contacts.stream()
					 .sorted((contact1,contact2) -> contact1.getState().compareTo(contact2.getState()))
					 .forEach(System.out::println);
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
		if(contactsByCity.get(city)==null) {
			contactsByCity.put(city, new LinkedList<>());
		}
		contactsByCity.get(city).add(contact);
		
		if(contactsByState.get(state)==null) {
			contactsByState.put(state, new LinkedList<>());
		}
		contactsByState.get(state).add(contact);
		
	}
	
	private boolean checkIfContactExists(Contact contact) {
		return contacts.stream().filter(c -> c.getFirstName().equals(contact.getFirstName())).findFirst().orElse(null) != null;
	}
	public void printCountByCity() {
		contactsByCity.keySet().stream().peek(c -> {
			System.out.println(c+" : "+contactsByCity.get(c).stream().count());
		});		
	}
	public void printCountByState() {
		contactsByState.keySet().stream().peek(s -> {
			System.out.println(s+" : "+contactsByState.get(s).stream().count());
		});			
	}


}
