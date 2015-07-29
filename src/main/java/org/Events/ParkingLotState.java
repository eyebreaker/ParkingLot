package org.Events;

/**
 * Created by Bhushan on 29-Jul-15.
 */
public abstract class ParkingLotState {
    private int capacity;
    private int size;

    public ParkingLotState(int capacity,int size){
        this.size = size;
        this.capacity = capacity;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getSize(){
        return size;
    }
}
