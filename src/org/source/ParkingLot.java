package org.source;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    public boolean checkCarUnique(Car car){

        boolean retVal = true;
        Iterator it = parkingSpace.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue().equals(car)){
                retVal = false;
                break;
            }
        }

        return retVal;
    }

    public int park(Car car){

        if(currentNumCars > ParkingLotSize) {
            throw new ParkingLotFullException("There is no Space in the Parking Lot");
        }
        if(!checkCarUnique(car)){
            throw new CarParkedAgainException("The Car Already Exists");
        }
        parkingSpace.put(currentNumCars++,car);
        return currentNumCars-1;
    }
}
