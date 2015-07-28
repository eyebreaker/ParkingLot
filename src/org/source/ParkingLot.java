package org.source;

import org.Events.ParkingLotEvent;
import org.Exceptions.CarNotExistException;
import org.Exceptions.CarParkedAgainException;
import org.Exceptions.ParkingLotFullException;

import java.util.*;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLot {

    private String name;
    private int token = 0;
    private int currentNumCars = 0;
    private int parkingLotSize = 2;
    private ParkingLotOwner owner;
    private Map<ParkingLotObserver,SubscribeStrategy> observers;
    private Map<Integer,Car> parkingSpace ;
    private ParkingLotEvent event = ParkingLotEvent.INITIAL;



    public ParkingLot(int ParkingLotSize){
        this.parkingLotSize = ParkingLotSize;
        parkingSpace = new HashMap<Integer,Car>();
        owner = new Owner();
        observers = new HashMap<ParkingLotObserver,SubscribeStrategy>();
    }

    public ParkingLot(int ParkingLotSize,String name,ParkingLotOwner owner){
        this.parkingLotSize = ParkingLotSize;
        this.owner = owner;
        this.name = name;
        parkingSpace = new HashMap<Integer,Car>();
        observers = new HashMap<ParkingLotObserver,SubscribeStrategy>();
        subscribeObserver(owner, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
    }

    public String getName(){
        return name;
    }

    public void subscribeObserver(ParkingLotObserver observer,SubscribeStrategy strategy){
        observers.put(observer, strategy);
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

        if(currentNumCars > parkingLotSize) {
            throw new ParkingLotFullException("There is no Space in the Parking Lot");
        }
        if(!checkCarUnique(car)){
            throw new CarParkedAgainException("The Car Already Exists");
        }
        parkingSpace.put(++token, car);
        ++currentNumCars;
        if(checkIfEvent())
            notifyObservers();
        return token;
    }

    public Car retriveCar(int token) {
        Iterator it = parkingSpace.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey() == token) {
                currentNumCars--;
                if(checkIfEvent())
                    notifyObservers();
                return (Car) pair.getValue();
            }
        }
        throw new CarNotExistException("There is no Car against this Token Number");
    }

    public void notifyObservers(){
        for(ParkingLotObserver observer : observers.keySet()){
            if(observers.get(observer).apply(event)){
                observer.notifyHandler(event,name);
            }
        }
    }

    private boolean checkIfEvent() {

        boolean retVal = false;
        if(currentNumCars == parkingLotSize && event!=ParkingLotEvent.FULL){
            event = ParkingLotEvent.FULL;
            retVal = true;
        }else if(event == ParkingLotEvent.FULL && (currentNumCars == parkingLotSize-1)){
            event = ParkingLotEvent.ONAVAILABLE;
            retVal = true;
        }else if (currentNumCars == (0.8 * parkingLotSize)){
            event = ParkingLotEvent.EIGHTY;
            retVal = true;
        }

        return retVal;
    }

}
