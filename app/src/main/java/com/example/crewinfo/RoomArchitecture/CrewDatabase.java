package com.example.crewinfo.RoomArchitecture;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CrewEntity.class}, version = 1, exportSchema = false)
public abstract class CrewDatabase extends RoomDatabase {
    public abstract CrewDao crewDao();

    private static volatile CrewDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 6;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static CrewDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (CrewDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, CrewDatabase.class, "CREW_DATABASE")
                            .addCallback(sRoomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            CrewDao crewDao = INSTANCE.crewDao();
            databaseWriteExecutor.execute(crewDao::deleteAll);
        }
    };
}
