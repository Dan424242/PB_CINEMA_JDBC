package com.example.pb_cinema.BaseModel;


public class Cinema_Rooms {

    private Integer id;
    private String name;
    private Integer noplaces;
    private String facilities;

    public Cinema_Rooms(Integer id, String name, Integer noplaces, String facilities) {
        this.id = id;
        this.name = name;
        this.noplaces = noplaces;
        this.facilities = facilities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoplaces() {
        return noplaces;
    }

    public void setNoplaces(Integer noplaces) {
        this.noplaces = noplaces;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
}
