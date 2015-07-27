package org.source;

import java.util.Objects;

/**
 * Created by Bhushan on 27-Jul-15.
 */
public class Car {

    private int id;
    private String carName;

    public Car(int id, String carName){
        this.id = id;
        this.carName = carName;
    }

    @Override
    public boolean equals(Object c) {
        Car car = (Car) c;
        return carName.equals(car.carName);
    }
}
