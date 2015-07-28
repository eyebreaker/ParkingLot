package org.Exceptions;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLotFullException extends RuntimeException {

    public ParkingLotFullException(String msg){
        super(msg);
    }
}
