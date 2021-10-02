package com.example.project_1cie2;

public class Fuel {
    int id;
    String date;
    float volume;
    float price;
    float odoMeter;
    float avg;
    float rsPrKM;

    public Fuel(int id, String date, float volume, float price, float odoMeter) {
        this.id = id;
        this.date = date;
        this.volume = volume;
        this.price = price;
        this.odoMeter = odoMeter;
    }

    public float getRsPrKM() {
        return rsPrKM;
    }

    public void setRsPrKM(float rsPrKM) {
        this.rsPrKM = rsPrKM;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getVolume() {
        return volume;
    }

    public float getPrice() {
        return price;
    }

    public float getOdoMeter() {
        return odoMeter;
    }

}