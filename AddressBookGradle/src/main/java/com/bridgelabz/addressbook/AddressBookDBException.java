package com.bridgelabz.addressbook;

public class AddressBookDBException extends RuntimeException {
	enum ExceptionType {
        FAILED_TO_CONNECT, CANNOT_EXECUTE_QUERY, UPDATE_FAILED
    }
    ExceptionType exceptionType;

    public AddressBookDBException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }
}
