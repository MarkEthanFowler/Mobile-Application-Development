package com.example.assignment08;

import java.io.Serializable;

public class User implements Serializable
{
    private String name;
    private String email;
    private String phone;
    private String state;
    private String DoB;
    private String maritalStatus;
    private String eduLevel;

    public User(String name, String email, String phone, String state, String DoB, String maritalStatus, String eduLevel)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.DoB = DoB;
        this.maritalStatus = maritalStatus;
        this.eduLevel = eduLevel;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getDoB()
    {
        return DoB;
    }

    public void setDoB(String doB)
    {
        DoB = doB;
    }

    public String getMaritalStatus()
    {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    public String getEduLevel()
    {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel)
    {
        this.eduLevel = eduLevel;
    }
}
