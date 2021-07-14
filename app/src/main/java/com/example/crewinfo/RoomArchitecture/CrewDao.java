package com.example.crewinfo.RoomArchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CrewDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CrewEntity crewEntity);

    @Delete
    void delete(CrewEntity crewEntity);

    @Transaction
    @Query("SELECT * FROM CREW_INFORMATION")
    LiveData<List<CrewEntity>> getAllCrew();

    @Query("DELETE FROM CREW_INFORMATION")
    void deleteAll();

}
