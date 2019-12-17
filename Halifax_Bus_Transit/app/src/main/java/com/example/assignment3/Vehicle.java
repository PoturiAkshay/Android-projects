package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

class Vehicle implements Parcelable {
    private String route_id;
    private double lat;
    private double lon;
    private List<Vehicle> bmarkers = new ArrayList<>();

    public Vehicle(List<Vehicle> busMarkers) {
        this.bmarkers = busMarkers;
    }

    public Vehicle(String vehicleID, double latitude, double longitude) {
        this.route_id = vehicleID;
        this.lat = latitude;
        this.lon = longitude;
    }

    protected Vehicle(Parcel in) {
        bmarkers = in.readArrayList(Vehicle.class.getClassLoader());
    }

    public String getVehicleID() {
        return route_id;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<Vehicle> getBusMarkers() {
        return bmarkers;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(bmarkers);
    }

    public final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
}