package org.test;

import org.Events.ParkingLotEvent;
import org.source.ParkingLot;
import org.source.ParkingLotObserver;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class TestParkingLotObserver implements ParkingLotObserver{

    private boolean status = false;

    public boolean getStatus(){
        return status;
    }


    @Override
    public void notifyHandler(ParkingLot parkingLot) {
        status = true;
    }
}
