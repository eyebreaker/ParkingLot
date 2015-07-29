package org.source;

import java.util.Map;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public interface ParkingLotSelectStrategy {

    public ParkingLot apply(Map<ParkingLot, Integer> parkingLotMap);
}
