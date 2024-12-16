package edu.uncc.assignment13.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Assignment13
//Contact.java
//Ethan Fowler and Raziuddin Syed Khaja

@Entity(tableName = "contact")
public class Contact implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    public int cid;

    public String name;
    public String phone;
    public String email;
    public String phoneType;
    public String group;

    public Contact() {
    }

    public Contact(String name, String phone, String email, String phoneType, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.phoneType = phoneType;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "Contact{" + "cid=" + cid + ", name='" + name + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + ", phoneType='" + phoneType + '\'' + ", group='" + group + '\'' + '}';
    }
}
