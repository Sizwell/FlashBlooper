package com.itech.Blooper.exception;

public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(Long id)
    {
        super("Word with ID " + "'" + id + "'" + " does not exist.");
    }
}
