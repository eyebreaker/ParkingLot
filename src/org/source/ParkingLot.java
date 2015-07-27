package org.source;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLot {

    private int currentNumCars = 1;
    private int ParkingLotSize = 2;
    /*private boolean ParkingSpace[][] = new boolean[breadth][height];*/
    private Map<Integer,Car> parkingSpace = new HashMap<Integer,Car>();

    public ParkingLot(int ParkingLotSize){
        this.ParkingLotSize = ParkingLotSize;
    }

    public int park(Car car){

        if(currentNumCars > ParkingLotSize) {
            throw new ParkingLotFullException("There is no Space in the Parking Lot");
        }
        parkingSpace.put(currentNumCars++,car);
        return currentNumCars-1;
    }
}
