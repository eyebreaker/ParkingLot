package org.test;

import org.Events.ParkingLotEvent;
import org.source.ParkingLotObserver;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class TestParkingLotObserver implements ParkingLotObserver{

    private ParkingLotEvent status;

    public ParkingLotEvent getStatus(){
        return status;
    }


    @Override
    public void notifyHandler(ParkingLotEvent event, String parkingLotName) {

    }
}
