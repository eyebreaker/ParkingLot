package org.source;

import java.util.Map;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public class MaximumTotalRemainingSize implements ParkingLotSelectStrategy {
    @Override
    public ParkingLot apply(Map<ParkingLot, Integer> parkingLotMap) {

        ParkingLot parkingLot = null;
        int maxCapacity = 0;
        for (Map.Entry<ParkingLot, Integer> entry : parkingLotMap.entrySet()) {
            if(entry.getValue()>maxCapacity){
                parkingLot = entry.getKey();
                maxCapacity = entry.getValue();
            }
        }

        return parkingLot;
    }
}
