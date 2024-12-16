package edu.uncc.assignment14.db;

import androidx.room.TypeConverter;

import java.util.Date;

//Assignment14
//Converter.java
//Ethan Fowler and Raziuddin Syed Khaja

public class Converter
{
    @TypeConverter
    public static Date convertToDate(Long dateAsLong)
    {
        return dateAsLong == null ? null : new Date(dateAsLong);
    }

    @TypeConverter
    public static Long convertToLong(Date dateAsDate)
    {
        return dateAsDate == null ? null : dateAsDate.getTime();
    }
}
