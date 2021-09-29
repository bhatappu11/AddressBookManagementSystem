package com.bridgelabz.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgelabz.addressbook.AddressBookException.ExceptionType;

public class AddressBookDBService {

	private PreparedStatement addressBookDataStatement;
	private PreparedStatement addressBookCountPreparedStatement;
	private static AddressBookDBService addressBookDBService;
	private AddressBookDBService() {
	}
	
	public static AddressBookDBService getInstance(){
		if (addressBookDBService == null)
			addressBookDBService=new AddressBookDBService();
		return addressBookDBService;
	}

	public List<Contact> readData() {
		String sql = "select * from contact c,address a where c.id=a.contact_id;";
		HashMap<String, AddressBookList> bookMap = getBook();
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
	private HashMap<String, AddressBookList> getBook() {
		HashMap<String, AddressBookList> bookMap = new HashMap<>();
		String sql = "select * from address_book";
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				String bookID  = result.getString("book_id");
				String bookName  = result.getString("book_name");
				bookMap.put(bookID, new AddressBookList(bookID, bookName));
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
			//System.out.println(contactList);
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
		HashMap<String, AddressBookList> bookMap = getBook();
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
			String email, String bookID, String addressID, String city, String state, String zip, String typeID) {
		HashMap<String, AddressBookList> bookMap = getBook();
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
			String sql = String.format("INSERT INTO contact (id,first_name,last_name,phone,email,book_id) VALUES('%s','%s','%s',%s,'%s','%s')",id,firstName,
					lastName,phone,email,bookID);
			int result = statement.executeUpdate(sql);
			System.out.println(result);
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
		try (Statement statement = connection.createStatement()){
			String sql = String.format("select * from address_book_type where type_id = '%s'",typeID);
			ResultSet result = statement.executeQuery(sql);
			if(result.next() == false) {
				throw new AddressBookException(ExceptionType.CANNOT_EXECUTE_QUERY, "Book with type id :"+typeID+" not present");
			}
		}catch(SQLException e) {
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


	public int countByCity(String city) {
		int count = 0;
		if(this.addressBookCountPreparedStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			addressBookCountPreparedStatement.setString(1, "Blore");
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
			String sqlStatement = "select * from contact c join address a on c.id=a.id where city = ?;";
			addressBookCountPreparedStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	

}
