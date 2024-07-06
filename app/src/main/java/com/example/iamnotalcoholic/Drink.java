package com.example.iamnotalcoholic;

public class Drink {
    public String type;
    public Integer amount;
    public Integer price;
    public Integer promile;
    public String date;

    public Drink(String type, int val, int pr, int pro, String dat) {
        this.type = type;
        this.amount = val;
        this.price = pr;
        this.promile = pro;
        this.date = dat;
    }
    @Override
    public String toString() {
        return "Type: " + type + "\n" +
                "Volume: " + amount + "\n" +
                "Strength: " + promile + "\n" +
                "Price: " + price + "\n" +
                "Date: " + date;
    }
}
