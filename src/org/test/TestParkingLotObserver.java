package org.test;

import org.Events.ParkingLotEvent;
import org.source.ParkingLotObserver;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class TestParkingLotObserver implements ParkingLotObserver{

    private boolean isFull = false;

    public boolean getIsFull(){
        return isFull;
    }
    public void onFull() {
        isFull = true;
    }

    public void onAvailability() {
        isFull = false;
    }

    @Override
    public void notifyHandler(ParkingLotEvent event) {

        switch(event){
            case FULL:
                onFull();
                break;
            case ONAVAILABLE:
                onAvailability();
                break;
        }

    }
}
