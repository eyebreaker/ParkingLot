package org.source;

import org.Events.ParkingLotEvent;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public interface ParkingLotObserver {

    public void notifyHandler(ParkingLotEvent event,String parkingLotName);
}
