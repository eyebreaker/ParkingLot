package org.source;

import org.Events.ParkingLotNotification;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public interface SubscribeStrategy {

  boolean apply(ParkingLotNotification event);
}
