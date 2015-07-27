package org.test;

import org.source.ParkingLotObserver;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLotObserverTest implements ParkingLotObserver{

    private boolean isFull = false;

    public boolean getIsFull(){
        return isFull;
    }
    @Override
    public void onFull() {
        isFull = true;
    }

    @Override
    public void onAvailability() {
        isFull = false;
    }
}
