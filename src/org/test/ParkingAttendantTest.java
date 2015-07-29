package org.test;

import org.Events.ParkingLotEvent;
import org.junit.Test;
import org.source.*;
import static org.junit.Assert.*;

/**
 * Created by Bhushan on 28-Jul-15.
 */
public class ParkingAttendantTest {

    @Test
    public void testPark() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        ParkingAttendant attendant = new ParkingAttendant();
        ParkingLotOwner owner = new ParkingLotOwner() {
            @Override
            public void notifyHandler(ParkingLot parkingLot) {

            }
        };
        ParkingLot p1= new ParkingLot(2,1,owner);
        ParkingLot p2 =new ParkingLot(3,2,owner);
        attendant.addParkingLot(p1);
        attendant.addParkingLot(p2);

        Token token1 = attendant.parkCar(c1);
        Token token2 = attendant.parkCar(c2);
        assertFalse(token1.getParkingLot()==token2.getParkingLot());
    }
}
