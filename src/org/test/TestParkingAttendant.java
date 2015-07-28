package org.test;

import org.Events.ParkingLotEvent;
import org.junit.Test;
import org.source.*;
import static org.junit.Assert.*;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public class TestParkingAttendant {

    @Test
    public void testPark() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        ParkingAttendant attendant = new ParkingAttendant();
        ParkingLotOwner owner = new ParkingLotOwner() {
            @Override
            public void notifyHandler(ParkingLotEvent event, String parkingLotName) {

            }
        };
        ParkingLot p1= new ParkingLot(2,"A",owner);
        ParkingLot p2 =new ParkingLot(3,"B",owner);
        attendant.addParkingLot(p1);
        attendant.addParkingLot(p2);
        p1.subscribeObserver(attendant, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
        p2.subscribeObserver(attendant, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });

        String token = attendant.parkCar(c1);
        attendant.parkCar(c2);
        attendant.parkCar(c3);
        attendant.parkCar(c4);
        System.out.println(token);
        assertTrue(attendant.unParkCar(token).equals(c1));
    }
}
