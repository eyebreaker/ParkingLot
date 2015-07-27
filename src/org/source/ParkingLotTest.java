package org.source;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class ParkingLotTest {

    @Test
    public void testParkingNotFull() throws Exception {

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");

        ParkingLot p1 = new ParkingLot(2);
        assertEquals(1,p1.park(c1));
        assertEquals(2,p1.park(c2));

    }

    @Test(expected = ParkingLotFullException.class)
    public void testParkingFull() throws  Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");

        ParkingLot p1 = new ParkingLot(2);
        assertEquals(1,p1.park(c1));
        assertEquals(2,p1.park(c2));
        assertEquals(3,p1.park(c3));
    }
}