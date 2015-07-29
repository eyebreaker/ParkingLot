package org.Events;

import java.util.Date;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public class ParkingLotParkNotification extends  ParkingLotState implements ParkingLotNotification{

    public ParkingLotParkNotification(int capacity,int size){
        super(capacity,size);
    }

    @Override
    public Date getTime() {
        return null;
    }
}
