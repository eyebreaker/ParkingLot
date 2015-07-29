package org.source;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public class Token {

    private ParkingLot parkingLot;
    private int token;

    public  Token(ParkingLot parkingLot,int token){
        this.parkingLot = parkingLot;
        this.token = token;
    }

    public ParkingLot getParkingLot(){
        return parkingLot;
    }

    public int getToken(){
        return token;
    }


}
