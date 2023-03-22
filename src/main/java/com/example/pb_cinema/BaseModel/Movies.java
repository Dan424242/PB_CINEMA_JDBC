package com.example.pb_cinema.BaseModel;



public class Movies {
    private int idmovie;
    private String title;
    private String duration;
    private String description;
    private String displaytimes;



    public Movies(int idmovie, String title, String duration, String description, String displaytimes) {
        this.idmovie = idmovie;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.displaytimes = displaytimes;
    }
    public String getDisplaytimes() {
        return displaytimes;
    }

    public void setDisplaytimes(String displaytimes) {
        this.displaytimes = displaytimes;
    }
    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
