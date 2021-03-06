package com.bridgelabz.addressbook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bridgelabz.addressbook.IOServiceEnum.ioService;
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
		AddressBookManager addressBookService = new AddressBookManager();
		List<Contact> contactList = addressBookService.readContact(ioService.DB_IO);
		System.out.println(contactList);
		Assert.assertEquals(2, contactList.size());
	}
	@Test
	public void givenAContact_WhenAdded_ShouldSyncWithDB(){
		AddressBookManager addressBookService = new AddressBookManager();
		addressBookService.readContact(ioService.DB_IO);
		addressBookService.addContact("c20","Pooja","lal","8845234567","rit@gmail.com","b3","a9","Banglore","Karnataka","657345","t3",LocalDate.now());
		boolean result=addressBookService.checkContactInSyncWithDB("c20");
		Assert.assertTrue(result);
	}
	@Test
	public void givenACity_WhenQueried_ShouldGetNumberOfContacts()
	{
		AddressBookManager addressBookService = new AddressBookManager();
		int count  = addressBookService.countByCity("Banglore", ioService.DB_IO);
		Assert.assertEquals(3, count);
	}
	@Test
	public void givenAState_WhenQueried_ShouldGetNumberOfContacts()
	{
		AddressBookManager addressBookService = new AddressBookManager();
		int count  = addressBookService.countByState("Karnataka", ioService.DB_IO);
		Assert.assertEquals(3, count);
	}
	@Test
	public void givenNewPhoneNumberForContact_WhenUpdated_ShouldSyncWithDB() {
		AddressBookManager addressBookService = new AddressBookManager();
		List<Contact> contactList = addressBookService.readContact(ioService.DB_IO);
		addressBookService.updateContactPhone("c1","7834560977");
		boolean result = addressBookService.checkContactInSyncWithDB("c1");
		Assert.assertTrue(result);
	}
	@Test
	public void givenDateRange_WhenQueried_ShouldReturnContactCount(){
		AddressBookManager addressBookService = new AddressBookManager();
		addressBookService.readContact(ioService.DB_IO);
		LocalDate startDate = LocalDate.of(2018,01,01);
		LocalDate endDate = LocalDate.now(); 
		List<Contact> contactList = addressBookService.getContactsInADateRange(startDate,endDate);
		Assert.assertEquals(5, contactList.size());
	}
	@Test
	public void givenContactWithWrongData_WhenInserted_ShouldThrowException() {
		AddressBookManager addressBookService = new AddressBookManager();
		try {
			addressBookService.addContact("c20","Pooja","lal","8845234567","rit@gmail.com","b8","a9","Banglore","Karnataka","657345","t3",LocalDate.now());
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(AddressBookException.class);
		}
		catch(AddressBookException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
}
