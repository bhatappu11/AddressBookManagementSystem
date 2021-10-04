package com.bridgelabz.addressbook;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.bridgelabz.addressbook.IOService.ioService;


public class AddressBookService {
	public static HashMap<String, AddressBook> addressBooks;
	private List<Contact> contactList;
	private AddressBookIOService addressBookIOService;
	
	public AddressBookService() {
		this.addressBooks = new HashMap<>();
		addressBookIOService = AddressBookIOService.getInstance();
	}
	public AddressBookService(List<Contact> contactList) {
		this();
		this.contactList = contactList;
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
	public void countByState() {
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.printCountByState();
		}
		
	}


	public void countByCity() {
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.printCountByCity();
		}		
	}
	
	public static void addressMenu(AddressBook addressBook) {
		Scanner sc = new Scanner(System.in);
		int option = 0;
		boolean exit = true;
		while(exit) {
			System.out.println("Select option \n1: Add Contact.  \n2: Edit Existing Contact. \n3:Delete contact \n4:Write contacts. \n5:Read contacts \n");
			option  = sc.nextInt();
			switch(option) {
			case 1 :
				addressBook.createContact(sc);
				break;
			case 2 :
				System.out.println("Enter the details to edit");
				addressBook.editContact();
				break;
			case 3:
				System.out.println("Enter the details to edit: ");
				addressBook.deleteContact();
				break;
			case 4:
				System.out.println("Enter \n1.To console\n2.To text file 3.To CSV file 4.To json file");
				int choice = sc.nextInt();
				if(choice==1)
					addressBook.writeDataToFile(ioService.CONSOLE_IO);
				else if(choice==2)
					addressBook.writeDataToFile(ioService.FILE_IO);
				else if(choice==3)
					addressBook.writeDataToFile(ioService.CSV_IO);
				else 
					addressBook.writeDataToFile(ioService.JSON_IO);
				break;
			case 5:
				System.out.println("Enter 1.from text file 2.from csv file 3.from json file");
				int opt=sc.nextInt();
				if(opt==1)
					addressBook.readDataFromFile(ioService.FILE_IO);
				else if(opt==2)
					addressBook.readDataFromFile(ioService.CSV_IO);
				else
					addressBook.readDataFromFile(ioService.JSON_IO);
				break;
			default:
				exit = false;

			}
			System.out.println();
		}
	}
	public void sortByName() {		
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.sortByName();
		}
	}
	public void sortByZip() {		
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.sortByZip();
		}
	}
	public void sortByCity() {		
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.sortByCity();
		}
	}
	public void sortByState() {		
		for(AddressBook addressBook : addressBooks.values()) {
			addressBook.sortByName();
		}
	}

	public List<Contact> readContact(ioService dbIo) {
		contactList = addressBookIOService.readData();
		return contactList;
	}

	public void addContact(String id, String firstName,String lastName, String phone,
			String email, String bookID, String addressID, String city, String state, String zip, String typeID, LocalDate date) {
		contactList.add(addressBookIOService.addContact(id, firstName, lastName, phone, email, bookID, addressID, city, state, zip, typeID,date));
		System.out.println(contactList);
	}

	public int countByCity(String city, ioService dbIo) {
		return addressBookIOService.countByCity(city);
	}
	
	private Contact getContactData(String id) {
		return this.contactList.parallelStream()
				.filter(contactDataItem -> contactDataItem.id.equals(id))
				.findFirst()
				.orElse(null);
	}
	
	public boolean checkContactInSyncWithDB(String id) {
		List<Contact> contactDataList = addressBookIOService.getContactData(id);
		System.out.println(contactDataList);
		return contactDataList.get(0).equals(getContactData(id));
	}
	
	public void updateContactPhone(String id, String phoneNumber) {
		int result = addressBookIOService.updateContactData(id,phoneNumber);
		if(result == 0) return;
		Contact contact = this.getContactData(id);
		if(contact != null) contact.phoneNumber = phoneNumber;		
	}
	
	public List<Contact> getContactsInADateRange(LocalDate startDate, LocalDate endDate) {
			return addressBookIOService.getContactBetweenDateRange(startDate, endDate);	
	}
	
	public int countByState(String state, ioService dbIo) {
		return addressBookIOService.countByState(state);
	}
	
}
