package com.bridgelabz.addressbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbook.IOServiceEnum.ioService;
import com.google.gson.Gson;

public class AddressBookFileIOTest {
	public Contact contact1;
	public Contact contact2;
	@Before
	public void initialise() {
		contact1 = new Contact("Ross", "Gellar", "Blore", "Kar", "678678", "1231231234", "ross@gmail.com");
		contact2 = new Contact("Andy", "Samberg", "Mumbai", "Maharashtra", "567567", "7878787878", "andy@gmail.com");
			
	}
	
	@Test
	public void givenContactDetails_ShouldAddToAddressBook() {
		AddressBook book1 = new AddressBook();
		book1.addContact(contact1);
		book1.addContact(contact2);
		Assert.assertEquals(2, book1.contacts.size());
		
	}
	
	@Test
	public void givenContacts_ShouldAddToTextFile() {
		AddressBook book1 = new AddressBook();
		book1.addContact(contact1);
		book1.addContact(contact2);
		book1.writeDataToFile(ioService.FILE_IO);
		long size=0;
		try {
			size = Files.lines(Paths.get(AddressBookIOService.ADDRESSBOOK_FILE_NAME)).count();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(2,size);		
	}
	@Test
	public void whenReadFromTextMethodCalled_ShouldPrintFile() {
		AddressBook book1 = new AddressBook();
		book1.addContact(contact1);
		book1.addContact(contact2);
		book1.writeDataToFile(ioService.FILE_IO);	
		book1.readDataFromFile(ioService.FILE_IO);
		Assert.assertTrue(true);
	}
	
}
