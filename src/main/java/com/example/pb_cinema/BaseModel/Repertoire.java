package com.example.pb_cinema.BaseModel;


public class Repertoire {


    private Integer id;
    private Integer idmovie;
    private String moviename;
    private String displayday;
    private String displaytime;

    private String idroom;

    private String nofreeplaces;

    private String ticketprice;

    public Repertoire(Integer id, Integer idmovie, String moviename, String displayday, String displaytime, String idroom, String nofreeplaces, String ticketprice) {
        this.id = id;
        this.idmovie = idmovie;
        this.moviename = moviename;
        this.displayday = displayday;
        this.displaytime = displaytime;
        this.idroom = idroom;
        this.nofreeplaces = nofreeplaces;
        this.ticketprice = ticketprice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(Integer idmovie) {
        this.idmovie = idmovie;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getDisplayday() {
        return displayday;
    }

    public void setDisplayday(String displayday) {
        this.displayday = displayday;
    }

    public String getDisplaytime() {
        return displaytime;
    }

    public void setDisplaytime(String displaytime) {
        this.displaytime = displaytime;
    }

    public String getIdroom() {
        return idroom;
    }

    public void setIdroom(String idroom) {
        this.idroom = idroom;
    }

    public String getNofreeplaces() {
        return nofreeplaces;
    }

    public void setNofreeplaces(String nofreeplaces) {
        this.nofreeplaces = nofreeplaces;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }
}