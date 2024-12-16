package edu.uncc.assignment14.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import edu.uncc.assignment14.models.Bill;

//Assignment14
//AppDatabase.java
//Ethan Fowler and Raziuddin Syed Khaja

@Database(entities = {Bill.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract BillDAO billDAO();
}
