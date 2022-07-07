package com.lj.restapi.exception;

import java.text.MessageFormat;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message) {
        super(message);
    }
}
