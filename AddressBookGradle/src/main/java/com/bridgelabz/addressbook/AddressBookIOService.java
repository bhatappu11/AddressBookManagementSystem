package com.bridgelabz.addressbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bridgelabz.addressbook.AddressBookList.IOService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


public class AddressBookIOService {
public static final String ADDRESSBOOK_FILE_NAME = "AddressBook-file.txt";
public static final String ADDRESSBOOK_CSV_FILE_NAME = "AddressBook-CSV.csv";
private static final String ADDRESSBOOK_JSON_FILE_NAME = "./AddressBook-Json.json";

	
	public void writeData(List<Contact> contacts, IOService ioService) {
		switch(ioService) {
		case FILE_IO:
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
			break;
		case CSV_IO:
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
			break;
		case JSON_IO:
			Gson gson=new Gson();
			String json=gson.toJson(contacts);
			FileWriter writer;
			try {
				writer = new FileWriter(ADDRESSBOOK_JSON_FILE_NAME);
				writer.write(json);	
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.err.println("Invalid choice");
			
		}
		
	}
	public void printData(IOService fileIo) {
		switch(fileIo) {
		case FILE_IO:
			try {
				Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath()).forEach(System.out::println);
			} catch(IOException e) {
				e.printStackTrace(); 
			}
			break;
		case CSV_IO:
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
	        		System.out.println("============================");
	        	}
	        	
			} catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case JSON_IO:
			Gson gson = new Gson();
			List<Contact> contactList = new ArrayList<Contact>();
			BufferedReader bufferObject;
			try {
				bufferObject = new BufferedReader(new FileReader(ADDRESSBOOK_JSON_FILE_NAME));
				Contact[] addressBookData = gson.fromJson(bufferObject, Contact[].class);
				contactList = Arrays.asList(addressBookData);
				for(Contact contact : contactList) {
					System.out.println("First name: "+contact.getFirstName());
					System.out.println("Last name: "+contact.getLastName());
					System.out.println("City: "+contact.getCity());
					System.out.println("State: "+contact.getState());
					System.out.println(("Zip: "+contact.getZip()));
					System.out.println(("Phone number: "+contact.getPhoneNumber()));
					System.out.println(("Email: "+contact.getEmail()));
					System.out.println("=========================");
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.err.println("Invalid choice");
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
		

}
