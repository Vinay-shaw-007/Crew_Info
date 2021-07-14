package com.example.crewinfo.RoomArchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrewViewModel extends AndroidViewModel {

    private final CrewRepository repository;
    private final LiveData<List<CrewEntity>> allCrews;

    public CrewViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new CrewRepository(application);
        allCrews = repository.getAllCrews();
    }
    public LiveData<List<CrewEntity>> getAllCrews(){
        return allCrews;
    }
    public void fetchJSONData(){repository.fetchJSONData();}
    public void swipeRefreshing(){repository.swipeRefreshing();}
    public void insert(CrewEntity entity){
        repository.insert(entity);
    }
    public void delete(CrewEntity entity){
        repository.delete(entity);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
}
