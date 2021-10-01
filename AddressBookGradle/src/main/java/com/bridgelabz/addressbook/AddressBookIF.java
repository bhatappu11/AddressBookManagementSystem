package com.bridgelabz.addressbook;

import java.util.Scanner;

import com.bridgelabz.addressbook.IOService.ioService;

public interface AddressBookIF {
	public void writeDataToFile(ioService ioService);
	public void readDataFromFile(ioService fileIo);
	public void findContactInCity(String cityName);
	public void findContactInState(String stateName);
	public void sortByName();
	public void sortByCity();
	public void sortByZip();
	public void sortByState();
	public void editContact();
	public void deleteContact();
	public void addContact(Contact contact);
	public void createContact(Scanner scanner);
	public boolean checkIfContactExists(Contact contact);
	public void printCountByCity();
	public void printCountByState();
	
}
