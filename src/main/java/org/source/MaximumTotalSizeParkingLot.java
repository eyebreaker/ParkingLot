package org.source;

import java.util.Map;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public class MaximumTotalSizeParkingLot implements ParkingLotSelectStrategy {
    @Override
    public ParkingLot apply(Map<ParkingLot, Integer> parkingLotMap) {

        ParkingLot parkingLot = null;
        int maxCapacity = 0;
        for (Map.Entry<ParkingLot, Integer> entry : parkingLotMap.entrySet()) {
            if(entry.getValue()> 0 && entry.getKey().getParkingLotSize()>maxCapacity){
                parkingLot = entry.getKey();
                maxCapacity = entry.getKey().getParkingLotSize();
            }
        }

        return parkingLot;
    }
}
