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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        return carName.equals(car.carName);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + carName.hashCode();
        return result;
    }
}
