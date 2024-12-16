package edu.uncc.assignment14.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import edu.uncc.assignment14.db.Converter;

//Assignment14
//Bill.java
//Ethan Fowler and Raziuddin Syed Khaja

@Entity(tableName = "bill")
@TypeConverters({Converter.class})
public class Bill implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int bid;

    public String category;

    public String name;

    public Date billDate;

    public double discount;

    public double amount;

    public Bill() {
    }

    public Bill(String category, String name, Date billDate, double discount, double amount) {
        this.category = category;
        this.name = name;
        this.billDate = billDate;
        this.discount = discount;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString()
    {
        return "Bill{" + "bid=" + bid + ", category='" + category + '\'' + ", name='" + name + '\'' + ", billDate=" + billDate + ", discount=" + discount + ", amount=" + amount + '}';
    }
}
