package org.source;

import org.Events.ParkingLotEvent;
import org.Events.ParkingLotNotification;
import org.Events.ParkingLotParkNotification;
import org.Events.ParkingLotUnParkNotification;
import org.Exceptions.CarNotExistException;
import org.Exceptions.CarParkedAgainException;
import org.Exceptions.ParkingLotFullException;

import java.util.*;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLot {

    private int id;
    private int token = 0;
    private int currentNumCars = 0;
    private int parkingLotSize = 2;
    private ParkingLotOwner owner;
    private Map<ParkingLotObserver,SubscribeStrategy> observers;
    private Map<Integer,Car> parkingSpace ;
    private ParkingLotNotification event = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLot)) return false;

        ParkingLot that = (ParkingLot) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public ParkingLot(int ParkingLotSize){
        this.parkingLotSize = ParkingLotSize;
        parkingSpace = new HashMap<Integer,Car>();
        owner = new Owner();
        observers = new HashMap<ParkingLotObserver,SubscribeStrategy>();
    }

    public ParkingLot(final int ParkingLotSize,int id,ParkingLotOwner owner){
        this.parkingLotSize = ParkingLotSize;
        this.owner = owner;
        this.id = id;
        parkingSpace = new HashMap<Integer,Car>();
        observers = new HashMap<ParkingLotObserver,SubscribeStrategy>();
        subscribeObserver(owner, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotNotification event) {
                if (event instanceof ParkingLotParkNotification) {
                    if(currentNumCars == parkingLotSize)
                        return true;

                }
                else if(event instanceof ParkingLotUnParkNotification){
                    if(currentNumCars == (ParkingLotSize-1))
                        return true;
                }
                return false;
            }

        });
    }


    public void subscribeObserver(ParkingLotObserver observer,SubscribeStrategy strategy){
        observers.put(observer, strategy);
        event = new ParkingLotParkNotification(currentNumCars,parkingLotSize);
        notifyObservers();
    }

    public void unsubscribeObserver(ParkingLotObserver observer){
        observers.remove(observer);
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

        if(currentNumCars >= parkingLotSize) {
            throw new ParkingLotFullException("There is no Space in the Parking Lot");
        }
        if(!checkCarUnique(car)){
            throw new CarParkedAgainException("The Car Already Exists");
        }
        parkingSpace.put(++token, car);
        ++currentNumCars;
        event = new ParkingLotParkNotification(currentNumCars,parkingLotSize);
        notifyObservers();
        return token;
    }

    public Car retrieveCar(int token) {
        Iterator it = parkingSpace.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey() == token) {
                currentNumCars--;
                event = new ParkingLotUnParkNotification(currentNumCars,parkingLotSize);
                notifyObservers();
                return (Car) pair.getValue();
            }
        }
        throw new CarNotExistException("There is no Car against this Token Number");
    }

    public void notifyObservers(){
        for(ParkingLotObserver observer : observers.keySet()){
            if(observers.get(observer).apply(event)){
                observer.notifyHandler(this);
            }
        }
    }


    public ParkingLotNotification getEvent(){
        return event;
    }
}
