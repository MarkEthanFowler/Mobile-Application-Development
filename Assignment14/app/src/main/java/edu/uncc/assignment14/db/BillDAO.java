package edu.uncc.assignment14.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.uncc.assignment14.models.Bill;

//Assignment14
//BillDAO.java
//Ethan Fowler and Raziuddin Syed Khaja

@Dao
public interface BillDAO
{
    @Query("SELECT * FROM bill")
    List<Bill> getAll();

    @Update
    void update(Bill bill);

    @Insert
    void insertAll(Bill... bills);

    @Delete
    void delete(Bill... bill);
}
