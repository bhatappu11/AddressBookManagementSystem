package com.bridgelabz.addressbook;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AddressBook {
	public static String FILE_NAME = "address-book-data.txt";
	private List<Contact> contacts;
	private HashMap<String, LinkedList<Contact>> contactsByCity;
	private HashMap<String, LinkedList<Contact>> contactsByState;
	Scanner scanner = new Scanner(System.in);
	private int numOfContacts = 0;
	
	public AddressBook() {
		this.contacts = new LinkedList<Contact>();
		this.contactsByCity = new HashMap<>();
		this.contactsByState = new HashMap<>();
		this.numOfContacts = 0;
	}
	public void writeDataToFile() {
		System.out.print("Writing AddressBook data to a file ");
		StringBuffer writeBuffer = new StringBuffer();
		contacts.forEach(contact -> {
			String contactDetails = contact.toString().concat("\n");
			writeBuffer.append(contactDetails);
		});
		
		try {
			Files.write(Paths.get(FILE_NAME),writeBuffer.toString().getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readDataFromFile() {
		System.out.println("Reading AddressBook data from the file");
		try {
			Files.lines(Paths.get(FILE_NAME))
				 .map(contact -> contact.trim())
				 .forEach(contact -> System.out.println(contact));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
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
		String firstName = scanner.next();
		Contact contactToChange = contacts.stream().filter(c -> c.getFirstName().equals(firstName)).findFirst().orElse(null);
		if(contactToChange == null) {
			System.out.println("contact does not exist");
			return;
		}
		System.out.println("Select the options \t 1.first name \t 2.last name \t 3.city \n 4.state \t 5.zip \t 6.phone number \t 7.email");
		int option = scanner.nextInt();
		switch(option) {
		case 1:
			System.out.println("enter new first name");
			String newFirstName = scanner.next();
			contactToChange.setFirstName(newFirstName);
			break;
		case 2:
			System.out.println("Enter new last name");
			String newLastName=scanner.next();
			contactToChange.setLastName(newLastName);
			break;
		case 3:
			System.out.println("Enter new city");
			contactToChange.setCity(scanner.next());
			break;
		case 4:
			System.out.println("Enter new state");
			contactToChange.setState(scanner.next());
			break;
		case 5:
			System.out.println("Enter new zip");
			contactToChange.setZip(scanner.next());
			break;
		case 6:
			System.out.println("Enter new phone number");
			contactToChange.setPhoneNumber(scanner.next());
			break;
		case 7:
			System.out.println("Enter new email");
			contactToChange.setEmail(scanner.next());
			break;
		default:
			System.err.println("Invalid Option");
			
		}
		System.out.println("Editing done, the new details are: ");
		System.out.println(contactToChange.getFirstName()+" "+contactToChange.getLastName()+" "+contactToChange.getCity()+" "+contactToChange.getState()+" "+contactToChange.getZip()+" "+contactToChange.getPhoneNumber()+" "+contactToChange.getEmail());
		
	}
	public void deleteContact() {
		System.out.println("Enter first name number of person you want to delete:");
		String firstName = scanner.next();
		for(int i=0;i<contacts.size();i++) {
			if(contacts.get(i).getFirstName().equals(firstName)) {
				contacts.remove(i);
				System.out.println("Successfully Deleted");
				return;
			}
		}
	}
	
	public void addContact() {
		Contact contact = createContact(scanner);
		if(!checkIfContactExists(contact)) {
			contacts.add(createContact(scanner));
		}
		else {
			System.out.println("Duplicate");
		}
		if(contactsByCity.get(contact.getCity())==null) {
			contactsByCity.put(contact.getCity(), new LinkedList<>());
		}
		contactsByCity.get(contact.getCity()).add(contact);
		
		if(contactsByState.get(contact.getState())==null) {
			contactsByState.put(contact.getState(), new LinkedList<>());
		}
		contactsByState.get(contact.getState()).add(contact);
		
	}
	
	private Contact createContact(Scanner scanner) {
		System.out.println("Enter contact details");
		System.out.println("Enter first name");
		String firstName = scanner.next();
		System.out.println("Enter last name");
		String lastName = scanner.next();
		System.out.println("Enter city");
		String city = scanner.next();
		System.out.println("Enter state");
		String state = scanner.next();
		System.out.println("Enter zip");
		String zip = scanner.next();
		System.out.println("Enter phone number");
		String phoneNumber = scanner.next();
		System.out.println("Enter email");
		String email = scanner.next();
		Contact contact = new Contact(firstName, lastName, city, state, zip, phoneNumber, email);
		return contact;
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
	@Override
	public String toString() {
		return "AddressBook [contacts=" + contacts + "]";
	}

	
}
