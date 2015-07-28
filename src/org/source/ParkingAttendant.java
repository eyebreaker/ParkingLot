package org.source;

import org.Events.ParkingLotEvent;
import org.Exceptions.CarNotExistException;
import org.Exceptions.ParkingLotFullException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public class ParkingAttendant implements ParkingLotObserver{


    private Map<ParkingLot,Boolean> parkingLotMap = new HashMap<>();

    public void addParkingLot(ParkingLot parkingLot){
        parkingLotMap.put(parkingLot,true);
    }

    public String parkCar(Car car){
        String attendantToken = null;
        for(Map.Entry<ParkingLot,Boolean> entry:parkingLotMap.entrySet()){
            if(entry.getValue()==true){
                int token = entry.getKey().park(car);
                attendantToken = entry.getKey().getName() + "-" + token;
                return attendantToken;
            }

        }

        throw new ParkingLotFullException("There is no space in the parking Lots");
    }

    public Car unParkCar(String attendantToken){
        if(attendantToken.matches("[a-zA-Z]+-[0-9]+")){
            String[] name_token = attendantToken.split("-");
            String parkingLotName = name_token[0];
            int token = Integer.parseInt(name_token[1]);
            for (Map.Entry<ParkingLot, Boolean> entry : parkingLotMap.entrySet()) {

                if(entry.getKey().getName().equals(parkingLotName)){
                    return entry.getKey().retriveCar(token);
                }

            }

            throw new CarNotExistException("There exists no such Car");
        }
        throw new CarNotExistException("There exists no such Car");
    }

    @Override
    public void notifyHandler(ParkingLotEvent event, String parkingLotName) {
        if(event == ParkingLotEvent.FULL) {
            for (Map.Entry<ParkingLot, Boolean> entry : parkingLotMap.entrySet()) {
                if (entry.getKey().getName().equals(parkingLotName)) {
                    entry.setValue(false);
                }
            }
        }else if(event == ParkingLotEvent.ONAVAILABLE){
            for (Map.Entry<ParkingLot, Boolean> entry : parkingLotMap.entrySet()) {
                if (entry.getKey().getName().equals(parkingLotName)) {
                    entry.setValue(true);
                }
            }
        }
    }
}
