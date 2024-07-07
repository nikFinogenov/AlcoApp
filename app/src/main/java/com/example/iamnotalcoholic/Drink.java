package com.example.iamnotalcoholic;

public class Drink {
    private String type;
    private int volume;
    private int strength;
    private double price;
    private String date;

    public Drink(String type, int volume, int strength, double price, String date) {
        this.type = type;
        this.volume = volume;
        this.strength = strength;
        this.price = price;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public int getStrength() {
        return strength;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
