package com.example.crewinfo.RoomArchitecture;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.crewinfo.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CrewRepository {
    private static final String TAG = "CREW_INFO";
    private final CrewDao crewDao;
    private final LiveData<List<CrewEntity>> allCrews;
    private final List<CrewEntity> newCrew = new ArrayList<>();
    private List<CrewEntity> roomDatabaseCrew;
    private final Application application;
    CrewRepository(Application application){
        this.application = application;
        CrewDatabase database = CrewDatabase.getInstance(application);
        crewDao = database.crewDao();
        allCrews = crewDao.getAllCrew();
    }
    LiveData<List<CrewEntity>> getAllCrews(){
        return allCrews;
    }
    void insert(CrewEntity entity){
        CrewDatabase.databaseWriteExecutor.execute(() -> crewDao.insert(entity));
    }
    void update(CrewEntity entity){
        CrewDatabase.databaseWriteExecutor.execute(() -> crewDao.update(entity));
    }
    void delete(CrewEntity entity){
        CrewDatabase.databaseWriteExecutor.execute(() -> crewDao.delete(entity));
    }
    void deleteAll(){
        Log.d(TAG, "deleteAll: deleted all data after swiping");
        CrewDatabase.databaseWriteExecutor.execute(crewDao::deleteAll);
    }
    void fetchJSONData(){
        CrewDatabase.databaseWriteExecutor.execute(this::fetchData);
    }
    void fetchData(){
        newCrew.clear();
        String url = "https://api.spacexdata.com/v4/crew";
        // get request queue
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject crew = response.getJSONObject(i);
                    String name, agency, status, id, wikipedia, image;
                    name = crew.getString("name");
                    agency = crew.getString("agency");
                    image = crew.getString("image");
                    wikipedia = crew.getString("wikipedia");
                    status = crew.getString("status");
                    id = crew.getString("id");
                    CrewEntity crewEntity = new CrewEntity(id, name, agency, wikipedia, status, image);
                    Log.d(TAG, "fetchJSONData: "+crewEntity);
                        insert(crewEntity);
                    newCrew.add(crewEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Throwable::getLocalizedMessage);
        MySingleton.getInstance(application.getApplicationContext()).addToRequestQueue(request);
    }
    void swipeRefreshing(){
        Log.d(TAG, "swipeRefreshing: all data deleted");
        CrewDatabase.databaseWriteExecutor.execute(crewDao::deleteAll);
        fetchJSONData();
        Log.d(TAG, "swipeRefreshing: data re fetched");
    }
}
