package com.example.crewinfo.RoomArchitecture;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CREW_INFORMATION")
public class CrewEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Id")
    private String id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Agency")
    private String agency;

    @ColumnInfo(name = "Wikipedia")
    private String wikipedia;

    @ColumnInfo(name = "Status")
    private String status;

    @ColumnInfo(name = "Image")
    private String image;

    @Ignore
    public CrewEntity() {
    }

    public CrewEntity(@NonNull String id, String name, String agency, String wikipedia, String status, String image) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.wikipedia = wikipedia;
        this.status = status;
        this.image = image;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
