package org.test;

import org.source.ParkingLotOwner;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLotOwnerTest extends ParkingLotOwner {

    private boolean isFull = false;

    public boolean getIsFull(){
        return isFull;
    }
    @Override
    public void onFull() {
        isFull = true;
    }

    @Override
    public void onBecomingNotFull() {
        isFull = false;
    }
}
