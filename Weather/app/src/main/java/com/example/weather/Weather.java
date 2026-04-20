package com.example.weather;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Weather {

    @PrimaryKey (autoGenerate = true)
    public int wid;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "celsius")
    public double celsius;

    @ColumnInfo(name = "fahrenheit")
    public double fahrenheit;

    public Weather(String city, String date, String photo, double celsius, double fahrenheit) {
        this.city = city;
        this.date = date;
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.photo = photo;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCelsius() {
        return celsius;
    }

    public void setCelsius(double celsius) {
        this.celsius = celsius;
    }

    public double getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
