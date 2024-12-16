package edu.uncc.assignment13.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assignment13.models.Contact;

//Assignment13
//ContactDAO.java
//Ethan Fowler and Raziuddin Syed Khaja

@Dao
public interface ContactDAO
{
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Update
    void update(Contact contact);

    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact... contact);

}
