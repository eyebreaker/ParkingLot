package org.source;

import org.Events.ParkingLotEvent;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public interface SubscribeStrategy {

    public boolean apply(ParkingLotEvent event);
}
