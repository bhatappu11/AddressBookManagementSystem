package com.bridgelabz.addressbook;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class AddressBookIOService {
public static String ADDRESSBOOK_FILE_NAME = "AddressBook-file.txt";
public static String ADDRESSBOOK_CSV_FILE_NAME = "AddressBook-CSV.csv";
	
	public void writeData(List<Contact> contacts) {
		StringBuffer addressBuffer = new StringBuffer();
		contacts.forEach(contact -> {
			String employeeDataString = contact.toString().concat("\n");
			addressBuffer.append(employeeDataString);
		});
		
		try {
			Files.write(Paths.get(ADDRESSBOOK_FILE_NAME),addressBuffer.toString().getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void printData() {
		try {
			Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath()).forEach(System.out::println);
		} catch(IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public List<String> readDataFromFile() {
			
			List<String> addressBookList = new ArrayList<String>();
			
			try {
				Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath())
					.map(contact -> contact.trim())
					.forEach(contact -> {
						System.out.println(contact);
						addressBookList.add(contact);
				});
				
			}
			catch(IOException e){
				e.printStackTrace();
			}
			return addressBookList;
		}
	public void writeDataToCsv(List<Contact> contacts){
		 try {
		            Writer writer = Files.newBufferedWriter(Paths.get(ADDRESSBOOK_CSV_FILE_NAME));

		            CSVWriter csvWriter = new CSVWriter(writer,
		                   		CSVWriter.DEFAULT_SEPARATOR,
		                   		CSVWriter.NO_QUOTE_CHARACTER,
		                   		CSVWriter.DEFAULT_ESCAPE_CHARACTER,
		                   		CSVWriter.DEFAULT_LINE_END);
		            List<String[]> data = new ArrayList();
			        for(Contact c : contacts) {
			        	String[] row = {c.getFirstName(),c.getLastName(),c.getCity(),c.getState(),c.getZip(),c.getPhoneNumber(),c.getEmail()};
			        	data.add(row);
			        }
			        
			        csvWriter.writeAll(data);
			        csvWriter.close();

		 }catch(IOException e) {
			 e.printStackTrace();
		 }
		
	}
	public void readFromCsv() {
		try {
	            Reader reader = Files.newBufferedReader(Paths.get(ADDRESSBOOK_CSV_FILE_NAME));
	            CSVReader csvReader = new CSVReader(reader);
	        	List<String[]> records = csvReader.readAll();
	        	for (String[] record : records) {
	        		System.out.println("First Name: "+record[0]);
	        		System.out.println("Last Name: "+record[1]);
	        		System.out.println("City: "+record[2]);
	        		System.out.println("State: "+record[3]);
	        		System.out.println("Zip: "+record[4]);
	        		System.out.println("Phone number: "+record[5]);
	        		System.out.println("Email: "+record[6]);
	        	}
	        	
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
