package org.test;

import org.Events.ParkingLotEvent;
import org.junit.Test;
import org.source.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
        ParkingAttendant attendant = new ParkingAttendant(new MaximumTotalRemainingSize());
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


    @Test
    public void testMaximumSizeStrategy() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        ParkingAttendant attendant = new ParkingAttendant(new MaximumTotalSizeParkingLot());
        ParkingLotOwner owner = new ParkingLotOwner() {
            @Override
            public void notifyHandler(ParkingLot parkingLot) {

            }
        };
        ParkingLot p1= new ParkingLot(2,1,owner);
        ParkingLot p2 =new ParkingLot(3,2,owner);
        attendant.addParkingLot(p1);
        attendant.addParkingLot(p2);

        attendant.parkCar(c1);
        Token token = attendant.parkCar(c2);
        attendant.parkCar(c3);
        assertTrue(token.getParkingLot().equals(p2));

    }

    @Test
    public void testMaximumRemainingSizeStrategy() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        ParkingAttendant attendant = new ParkingAttendant(new MaximumTotalRemainingSize());
        ParkingLotOwner owner = new ParkingLotOwner() {
            @Override
            public void notifyHandler(ParkingLot parkingLot) {

            }
        };
        ParkingLot p1= new ParkingLot(2,1,owner);
        ParkingLot p2 =new ParkingLot(3,2,owner);
        attendant.addParkingLot(p1);
        attendant.addParkingLot(p2);

        attendant.parkCar(c1);
        Token token = attendant.parkCar(c2);
        attendant.parkCar(c3);
        assertTrue(token.getParkingLot().equals(p1));

    }


    @Test
    public void testMaximumRemainingSizeStrategyByMocking() throws Exception{

        Car c1 = new Car(1,"Maruti");
        Car c2 = new Car(2,"Audi");
        Car c3 = new Car(3,"Mercedes");
        Car c4 = new Car(4,"Porsche");
        ParkingLotSelectStrategy strategy = mock(MaximumTotalSizeParkingLot.class);
        ParkingAttendant attendant = new ParkingAttendant(strategy);
        ParkingLotOwner owner = new ParkingLotOwner() {
            @Override
            public void notifyHandler(ParkingLot parkingLot) {

            }
        };

        ParkingLot mockedParkingLot1 = mock(ParkingLot.class);
        ParkingLot mockedParkingLot2 = mock(ParkingLot.class);


        attendant.addParkingLot(mockedParkingLot1);
        attendant.addParkingLot(mockedParkingLot2);
        attendant.parkCar(c1);
        attendant.parkCar(c2);

    }
}
