package com.example.restaurantmap;

import android.os.Parcel;
import android.os.Parcelable;

public class AddPlace implements Parcelable {
    private String name;
    private double latitude;
    private double longitude;


    public AddPlace(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected AddPlace(Parcel in) {
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<AddPlace> CREATOR = new Creator<AddPlace>() {
        @Override
        public AddPlace createFromParcel(Parcel in) {
            return new AddPlace(in);
        }

        @Override
        public AddPlace[] newArray(int size) {
            return new AddPlace[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
