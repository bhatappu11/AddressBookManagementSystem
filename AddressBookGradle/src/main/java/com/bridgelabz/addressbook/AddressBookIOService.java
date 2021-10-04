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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.bridgelabz.addressbook.AddressBookException.ExceptionType;

import com.bridgelabz.addressbook.IOServiceEnum.ioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


public class AddressBookIOService {
	public static final String ADDRESSBOOK_FILE_NAME = "AddressBook-file.txt";
	public static final String ADDRESSBOOK_CSV_FILE_NAME = "AddressBook-CSV.csv";
	public static final String ADDRESSBOOK_JSON_FILE_NAME = "./AddressBook-Json.json";
	private PreparedStatement addressBookDataStatement;
	private PreparedStatement addressBookCountPreparedStatement;
	private PreparedStatement contactDateStatement;
	private PreparedStatement addressBookCountStatePreparedStatement;
	private static AddressBookIOService addressBookIOService;
	private AddressBookIOService() {
}

public static AddressBookIOService getInstance(){
	if (addressBookIOService == null)
		addressBookIOService=new AddressBookIOService();
	return addressBookIOService;
}

	
	public void writeData(List<Contact> contacts, ioService ioService) {
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
	public void printData(ioService fileIo) {
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
		
	public List<Contact> readData() {
		String sql = "select * from contact c,address a where c.id=a.contact_id;";
		HashMap<String, AddressBookSystem> bookMap = getBook();
		List<Contact> contactList = new ArrayList<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = this.getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
		
	}
	private HashMap<String, AddressBookSystem> getBook() {
		HashMap<String, AddressBookSystem> bookMap = new HashMap<>();
		String sql = "select * from address_book";
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				String bookID  = result.getString("book_id");
				String bookName  = result.getString("book_name");
				bookMap.put(bookID, new AddressBookSystem(bookID, bookName));
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		return bookMap;
	}
	public List<Contact> getContactData(String firstName) {
		List<Contact> contactList = null;
		if (this.addressBookDataStatement == null)
			this.prepareStatementForContactData();
		try {
			addressBookDataStatement.setString(1, firstName);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			contactList = this.getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;	
	}
	private void prepareStatementForContactData() {
		try {
			Connection connection = this.getConnection();
			String sql=	"SELECT	* FROM contact WHERE id = ?";
			addressBookDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<Contact> getContactData(ResultSet result) {
		HashMap<String, AddressBookSystem> bookMap = getBook();
		List<Contact> contactList = new ArrayList<>();
		try{
			while(result.next()) {
				String id = result.getString("id");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String phoneNumber = result.getString("phone");
				String email = result.getString("email");
				String bookID = result.getString("book_id");
				contactList.add(new Contact(id, firstName, lastName,phoneNumber,email,bookMap.get(bookID)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}



	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "perfios";
		Connection connection;
		System.out.println("connecting to database: "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("connection is successful!"+connection);
		return connection;
	}
	private HashMap<String, ArrayList<AddressBookType>> getAddressBookTypeList() {
		HashMap<String,ArrayList<AddressBookType>> departmentList = new HashMap<>();
		String sql = "select * from addressbook_booktype";
		HashMap<String,AddressBookType> typeMap = getType();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				String bookId = resultSet.getString("book_id");
				String typeId = resultSet.getString("type_id");
				if(departmentList.get(bookId) == null) departmentList.put(bookId, new ArrayList<AddressBookType>());
				departmentList.get(bookId).add(typeMap.get(typeId));
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		return departmentList;
	}

	private HashMap<String, AddressBookType> getType() {
		String sql = "select * from address_book_type";
		HashMap<String,AddressBookType> type = new HashMap<>();
		try(Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				String typeId = result.getString("type_id");
				String typeName  = result.getString("type_name");
				type.put(typeId,new AddressBookType(typeId, typeName));
			}
		}
		catch(SQLException e) {
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		return type;
	}

	public Contact addContact(String id, String firstName,String lastName, String phone,
			String email, String bookID, String addressID, String city, String state, String zip, String typeID, LocalDate date) {
		HashMap<String, AddressBookSystem> bookMap = getBook();
		HashMap<String,ArrayList<AddressBookType>> typeList = getAddressBookTypeList();
		HashMap<String,AddressBookType> type = new HashMap<>();
		
		Contact contactData = null;
		Connection connection = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		}catch(Exception e) {
			throw new AddressBookException(ExceptionType.FAILED_TO_CONNECT, "couldn't establish connection");
		}
	
		try (Statement statement = connection.createStatement()){
			String sql = String.format("select * from address_book where book_id = '%s'",bookID);
			ResultSet result = statement.executeQuery(sql);
			if(result.next() == false) {
				throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "Book with id:"+bookID+" not present");
			}
		}catch(SQLException e) {
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		
		try (Statement statement = connection.createStatement()){
			String sql = String.format("INSERT INTO contact (id,first_name,last_name,phone,email,book_id,date_added) VALUES('%s','%s','%s',%s,'%s','%s','%s')",id,firstName,
					lastName,phone,email,bookID,date.toString());
			int result = statement.executeUpdate(sql);
		}catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		try (Statement statement = connection.createStatement()){
			String sql = String.format("select * from address_book_type where type_id = '%s'",typeID);
			ResultSet result = statement.executeQuery(sql);
			if(result.next() == false) {
				throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "Book with type id :"+typeID+" not present");
			}
		}catch(SQLException e) {
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		
		try (Statement statement = connection.createStatement()){
			String sql = String.format("INSERT INTO address(contact_id,address_id,city,state,zip)VALUES('%s','%s','%s','%s','%s')",id
					,addressID,city,state,zip);
			int result = statement.executeUpdate(sql);
			
		}catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}
		try(Statement statement = connection.createStatement()){
			String sql = String.format("INSERT INTO addressbook_booktype(book_id,type_id)VALUES('%s','%s')",
					bookID,typeID);
			int result = statement.executeUpdate(sql);
			if(result == 1) {
				contactData = new Contact(id, firstName, lastName,phone,email,bookMap.get(bookID));
			}
		}catch(SQLException e) {
			try {
				connection.rollback();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "query execution failed");
		}try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return contactData;
}


	public int updateContactData(String id, String phoneNumber) {
			return this.updateContactDataUsingStatement(id,phoneNumber);
	}

	private int updateContactDataUsingStatement(String id, String phoneNumber) {
		String sql = String.format("update contact set phone = '%s' where id = '%s';",phoneNumber,id);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		} 
		return 0;

	}


	public int countByCity(String city) {
		int count = 0;
		if(this.addressBookCountPreparedStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			addressBookCountPreparedStatement.setString(1, city);
			ResultSet resultSet = addressBookCountPreparedStatement.executeQuery();
			while(resultSet.next()) {
				count++; 
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return count;
	}

	private void prepareStatementForAddressBook() {
		try {
			Connection connection = this.getConnection();
			String sqlStatement = "select * from contact c join address a on c.id=a.contact_id where city = ?;";
			addressBookCountPreparedStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public int countByState(String state) {
		int count = 0;
		if(this.addressBookCountStatePreparedStatement == null) {
			this.prepareStatementForCountContact();
		}
		try {
			addressBookCountStatePreparedStatement.setString(1, state);
			ResultSet resultSet = addressBookCountStatePreparedStatement.executeQuery();
			while(resultSet.next()) {
				count++; 
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new AddressBookException(AddressBookException.ExceptionType.CANNOT_EXECUTE_QUERY, "Failed to execute query");
		}
		return count;
	}
	private void prepareStatementForCountContact() {
		try {
			Connection connection = this.getConnection();
			String sqlStatement = "select * from contact c join address a on c.id=a.contact_id where state = ?;";
			addressBookCountStatePreparedStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> getContactBetweenDateRange(LocalDate startDate, LocalDate endDate) {
		List<Contact> contactList=null;
		if(this.contactDateStatement==null){
			this.preparedStatementForContactInDateRange();
		}
		try{
			contactDateStatement.setDate(1,java.sql.Date.valueOf(startDate));
			contactDateStatement.setDate(2,java.sql.Date.valueOf(endDate));

			ResultSet resultSet= contactDateStatement.executeQuery();
			contactList=this.getContactData(resultSet);

		}catch (SQLException e){
			e.printStackTrace();
		}
		return contactList;


	}

	private void preparedStatementForContactInDateRange() {
		try{
			Connection connection = this.getConnection();
			String sql="SELECT * FROM contact WHERE date_added BETWEEN ? AND ?";
			contactDateStatement=connection.prepareStatement(sql);
		}catch (SQLException e){
			e.printStackTrace();
		}
		
	}

}
