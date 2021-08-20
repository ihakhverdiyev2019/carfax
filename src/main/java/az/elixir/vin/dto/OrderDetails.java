package az.elixir.vin.dto;

import java.util.Date;

public class OrderDetails {

    private String vinCode;

    private double price;

    private String date;

    public OrderDetails() {
    }


    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
