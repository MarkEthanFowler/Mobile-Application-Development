package edu.uncc.assignment13.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.uncc.assignment13.models.Contact;


//Assignment13
//AppDatabase.java
//Ethan Fowler and Raziuddin Syed Khaja

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ContactDAO contactDAO();
}
