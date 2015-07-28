package org.Exceptions;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class CarNotExistException extends RuntimeException {
    public CarNotExistException(String msg){
        super(msg);
    }
}
