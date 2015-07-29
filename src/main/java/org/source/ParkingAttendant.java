package org.source;

import org.Events.*;
import org.Exceptions.CarNotExistException;
import org.Exceptions.ParkingLotFullException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public class ParkingAttendant implements ParkingLotObserver{


    private Map<ParkingLot,Integer> parkingLotMap = new HashMap<>();
    private ParkingLotSelectStrategy parkingLotSelectStrategy = null;

    public ParkingAttendant(ParkingLotSelectStrategy parkingLotSelectStrategy){
        this.parkingLotSelectStrategy = parkingLotSelectStrategy;

    }

    public void addParkingLot(ParkingLot parkingLot){
        parkingLotMap.put(parkingLot,0);
        //setStrategy();
        parkingLot.subscribeObserver(this, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotNotification event) {
                if (event instanceof ParkingLotParkNotification) {

                        return true;

                }
                else if(event instanceof ParkingLotUnParkNotification){

                        return true;
                }
                return false;
            }

        });
    }

    /*private void setStrategy(){
        parkingLotSelectStrategy = new ParkingLotSelectStrategy() {
            @Override
            public ParkingLot apply() {
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
        };
    }*/

    public Token parkCar(Car car){
        Token attendantToken = null;
        ParkingLot parkingLot = parkingLotSelectStrategy.apply(parkingLotMap);
        if(parkingLot != null){
            attendantToken = new Token(parkingLot,parkingLot.park(car));
            return attendantToken;
        }
        throw new ParkingLotFullException("There is no space in the parking Lots");
    }

    public Car unParkCar(Token attendantToken){
            for (Map.Entry<ParkingLot, Integer> entry : parkingLotMap.entrySet()) {

                if(entry.getKey().equals(attendantToken.getParkingLot())){
                    return entry.getKey().retrieveCar(attendantToken.getToken());
                }

            }

        throw new CarNotExistException("There exists no such Car");
    }

    @Override
    public void notifyHandler(ParkingLot parkingLot) {
        ParkingLotNotification event = parkingLot.getEvent();
        if(event instanceof ParkingLotState) {
            for (Map.Entry<ParkingLot, Integer> entry : parkingLotMap.entrySet()) {
                if (entry.getKey().equals(parkingLot)) {
                    entry.setValue(((ParkingLotState) event).getSize()-((ParkingLotState) event).getCapacity());
                }
            }
        }
    }
}
