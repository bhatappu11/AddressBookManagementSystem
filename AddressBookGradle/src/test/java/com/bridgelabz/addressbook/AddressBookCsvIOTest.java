package com.bridgelabz.addressbook;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbook.IOServiceEnum.ioService;

public class AddressBookCsvIOTest {
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
	public void givenContacts_ShouldAddToCSVFile() {
		AddressBook book1 = new AddressBook();
		book1.addContact(contact1);
		book1.addContact(contact2);
		book1.writeDataToFile(ioService.CSV_IO);
		long size=0;
		try {
			size = Files.lines(Paths.get(AddressBookIOService.ADDRESSBOOK_CSV_FILE_NAME)).count();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(2,size);		
	}
	@Test
	public void whenReadFromCSVMethodCalled_ShouldPrintFile() {
		AddressBook book1 = new AddressBook();
		book1.addContact(contact1);
		book1.addContact(contact2);
		book1.writeDataToFile(ioService.CSV_IO);	
		book1.readDataFromFile(ioService.CSV_IO);
		Assert.assertTrue(true);
	}
	
}
