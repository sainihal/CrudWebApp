package com.wavemaker.todo.exceptions;

/**
 * Created by sainihala on 5/8/16.
 */
public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
