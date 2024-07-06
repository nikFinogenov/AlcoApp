package com.example.iamnotalcoholic;

public class ReportItem {
    private String type;
    private String volume;
    private String strength;
    private String price;

    public ReportItem(String type, String volume, String strength, String price) {
        this.type = type;
        this.volume = volume;
        this.strength = strength;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getVolume() {
        return volume;
    }

    public String getStrength() {
        return strength;
    }

    public String getPrice() {
        return price;
    }
}

