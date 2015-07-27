package org.source;

import java.util.*;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLot {

    private int currentNumCars = 1;
    private int parkingLotSize = 2;
    private boolean isFull = false;
    private ParkingLotOwner owner;
    private List<ParkingLotObserver> observers ;
    private Map<Integer,Car> parkingSpace ;


    public ParkingLot(int ParkingLotSize){
        this.parkingLotSize = ParkingLotSize;
        parkingSpace = new HashMap<Integer,Car>();
        owner = new Owner();
        observers = new ArrayList<ParkingLotObserver>();
    }

    public ParkingLot(int ParkingLotSize,ParkingLotOwner owner){
        this.parkingLotSize = ParkingLotSize;
        this.owner = owner;
        parkingSpace = new HashMap<Integer,Car>();
        observers = new ArrayList<ParkingLotObserver>();
        registerObserver(owner);
    }

    public boolean registerObserver(ParkingLotObserver observer){
        return observers.add(observer);
    }

    public boolean removeObserver(ParkingLotObserver observer){
        return observers.remove(observer);
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

        if(currentNumCars > parkingLotSize) {
            throw new ParkingLotFullException("There is no Space in the Parking Lot");
        }
        if(!checkCarUnique(car)){
            throw new CarParkedAgainException("The Car Already Exists");
        }
        parkingSpace.put(currentNumCars++, car);
        checkFull();
        return currentNumCars-1;
    }

    public void checkFull(){
        if(currentNumCars > parkingLotSize) {
            if(isFull != true) {
                isFull = true;
                for (ParkingLotObserver observer : observers){
                    observer.onFull();
                }
            }
        }
    }


    public void checkNotFull(){
        if(isFull == true) {
            isFull = false;
            for(ParkingLotObserver observer : observers){
                observer.onAvailability();
            }
        }
    }

    public Car retriveCar(int token) {
        Iterator it = parkingSpace.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey() == token) {
                currentNumCars--;
                checkNotFull();
                return (Car) pair.getValue();
            }
        }
        throw new CarNotExistException("There is no Car against this Token Number");
    }

}
