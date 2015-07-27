package org.test;

import org.junit.Test;
import org.source.*;

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

    @Test(expected = CarParkedAgainException.class)
    public void testParkingCarAgain() throws  Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(1,"Maruti");

        ParkingLot p1 = new ParkingLot(2);
        assertEquals(1,p1.park(c1));
        assertEquals(2,p1.park(c2));
    }


    @Test
    public void testRetrieveCarWhenCarExists() throws Exception{

        Car c1 = new Car(1,"Maruti");
        ParkingLot p1 = new ParkingLot(2);
        int token = p1.park(c1);
        Car c2 = p1.retriveCar(token);
        assertTrue(c2.equals(c1));

    }

    @Test(expected = CarNotExistException.class)
    public void testRetrieveCarWhenCarNotExists()throws Exception{

        ParkingLot p1 = new ParkingLot(2);
        Car c2 = p1.retriveCar(1);
    }

    @Test
    public void testOnFullNotification() throws Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        ParkingLotOwnerTest owner = new ParkingLotOwnerTest();
        ParkingLot p1 = new ParkingLot(2,owner);
        p1.park(c1);
        p1.park(c2);
        assertTrue(owner.getIsFull());

    }

    @Test
    public void testOnBecomingNotFullNotification() throws Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        ParkingLotOwnerTest owner = new ParkingLotOwnerTest();
        ParkingLot p1 = new ParkingLot(2,owner);
        int token = p1.park(c1);
        p1.park(c2);
        p1.retriveCar(token);
        assertFalse(owner.getIsFull());
    }



}