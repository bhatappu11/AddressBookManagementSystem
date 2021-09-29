package com.bridgelabz.addressbook;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbook.AddressBookService.IOService;

public class AddressBookDBIOTest {
	public Contact contact1;
	public Contact contact2;
	@Before
	public void initialise() {
		contact1 = new Contact("Ross", "Gellar", "Blore", "Kar", "678678", "1231231234", "ross@gmail.com");
		contact2 = new Contact("Andy", "Samberg", "Mumbai", "Maharashtra", "567567", "7878787878", "andy@gmail.com");
			
	}
	
	@Test
	public void givenContactInDB_WhenRetrieved_ShouldMatchContactCount(){
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> contactList = addressBookService.readContact(IOService.DB_IO);
		System.out.println(contactList);
		Assert.assertEquals(2, contactList.size());
	}
	@Test
	public void givenAContact_WhenAdded_ShouldSyncWithDB(){
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readContact(IOService.DB_IO);
		addressBookService.addContact("c20","Pooja","lal","8845234567","rit@gmail.com","b3","a9","Banglore","Karnataka","657345","t3");
		//List<Contact> contactList = addressBookService.readContact(IOService.DB_IO);
		boolean result=addressBookService.checkContactInSyncWithDB("c20");
		Assert.assertTrue(result);
	}/*
	@Test
	public void givenACity_WhenQueried_ShouldGetNumberOfContacts()
	{
		AddressBookService addressBookService = new AddressBookService();
		int count  = addressBookService.countByCity("Blore", IOService.DB_IO);
		Assert.assertEquals(2, count);
	}*/
	@Test
	public void givenNewPhoneNumberForContact_WhenUpdated_ShouldSyncWithDB() {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> contactList = addressBookService.readContact(IOService.DB_IO);
		addressBookService.updateContactPhone("c1","7834560977");
		boolean result = addressBookService.checkContactInSyncWithDB("c1");
		Assert.assertTrue(result);
	}
	
	
}
