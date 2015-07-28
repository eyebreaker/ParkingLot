package org.test;

import org.Events.ParkingLotEvent;
import org.Exceptions.CarNotExistException;
import org.Exceptions.CarParkedAgainException;
import org.Exceptions.ParkingLotFullException;
import org.junit.Test;
import org.source.*;

import java.util.ArrayList;
import java.util.List;

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
        TestParkingLotOwner owner = new TestParkingLotOwner();
        ParkingLot p1 = new ParkingLot(2,owner);
        p1.park(c1);
        p1.park(c2);
        assertTrue(owner.getStatus() == ParkingLotEvent.FULL);

    }

    @Test
    public void testOnBecomingAvailableNotification() throws Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        TestParkingLotOwner owner = new TestParkingLotOwner();
        ParkingLot p1 = new ParkingLot(2,owner);
        int token = p1.park(c1);
        p1.park(c2);
        p1.retriveCar(token);
        assertTrue(owner.getStatus() == ParkingLotEvent.ONAVAILABLE);
    }


    @Test
    public void testOnFullNotificationForObservers() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        List<TestParkingLotObserver> observers = new ArrayList<TestParkingLotObserver>();
        TestParkingLotOwner owner = new TestParkingLotOwner();
        ParkingLot p1 = new ParkingLot(2,owner);
        observers.add(owner);
        TestParkingLotObserver agent1 = new TestParkingLotObserver();
        TestParkingLotObserver agent2 = new TestParkingLotObserver();
        observers.add(agent1);
        observers.add(agent2);

        p1.subscribeObserver(agent1,  new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
        p1.subscribeObserver(agent2, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
        p1.park(c1);
        p1.park(c2);
        for(TestParkingLotObserver observer : observers) {
            assertTrue(observer.getStatus()== ParkingLotEvent.FULL);
        }
    }

    @Test
    public void testOnAvailabilityNotificationForObservers() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        List<TestParkingLotObserver> observers = new ArrayList<TestParkingLotObserver>();
        TestParkingLotOwner owner = new TestParkingLotOwner();
        ParkingLot p1 = new ParkingLot(2,owner);
        observers.add(owner);
        TestParkingLotObserver agent1 = new TestParkingLotObserver();
        TestParkingLotObserver agent2 = new TestParkingLotObserver();
        observers.add(agent1);
        observers.add(agent2);

        p1.subscribeObserver(agent1, new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
        p1.subscribeObserver(agent2,new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if (event == ParkingLotEvent.FULL || event == ParkingLotEvent.ONAVAILABLE)
                    return true;
                return false;
            }
        });
        int token = p1.park(c1);
        p1.park(c2);
        p1.retriveCar(token);
        for(TestParkingLotObserver observer : observers) {
            assertTrue(observer.getStatus() == ParkingLotEvent.ONAVAILABLE);
        }
    }

    @Test
    public void testOnEightyPercentAvailabilityForFbi() throws Exception{
        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        List<TestParkingLotObserver> observers = new ArrayList<TestParkingLotObserver>();
        TestParkingLotOwner owner = new TestParkingLotOwner();
        ParkingLot p1 = new ParkingLot(5,owner);
        TestParkingLotObserver agent1 = new TestParkingLotObserver();
        TestParkingLotObserver agent2 = new TestParkingLotObserver();
        observers.add(agent1);
        observers.add(agent2);

        SubscribeStrategy strategy = new SubscribeStrategy() {
            @Override
            public boolean apply(ParkingLotEvent event) {
                if(event == ParkingLotEvent.EIGHTY){
                    return true;
                }
                return false;
            }
        };
        p1.subscribeObserver(agent1,strategy);
        p1.subscribeObserver(agent2,strategy);
        int token = p1.park(c1);
        p1.park(c2);
        p1.park(c3);
        p1.park(c4);
        p1.retriveCar(token);

        for(TestParkingLotObserver observer : observers) {
            assertTrue(observer.getStatus() == ParkingLotEvent.EIGHTY);
        }
    }
}