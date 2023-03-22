package com.example.pb_cinema;

import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Service.CinemaRoomsService;
import com.example.pb_cinema.Service.RepertoireService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketInfo {

    String Email;
    String FirstName;
    String LastName;
    String Title;
    String Date;
    String DayTime;
    String RoomName;
    float Price;
    //int SeatRow;
    //int SeatColumn;
    int Seat;
    int IDmovie;
    int IDRepertoire;
    public TicketInfo() {
    }

    public TicketInfo(String email, String firstName, String lastName, String title, String date, String dayTime, float price, int seat, int IDmovie, int IDRepertoire) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Title = title;
        Date = date;
        DayTime = dayTime;
        Price = price;
        Seat = seat;
        this.IDmovie = IDmovie;
        this.IDRepertoire = IDRepertoire;
    }
    static public ObservableList<TicketInfo> observeTicketList = FXCollections.observableArrayList();
    static public List<String> TiList = new ArrayList<>();
    public void AddTicket(TicketInfo info) throws SQLException, ClassNotFoundException {
        String Bilecik = new String();
        Bilecik+= "| FILM: " + info.getTitle() + " | ";
        Bilecik+=  info.getDayTime()+ " | ";
        Bilecik+= "DATA: " + info.getDate()+ " | ";
        RepertoireService repertoireService = new RepertoireService();
        CinemaRoomsService cinemaRoomsService = new CinemaRoomsService();

        RoomName = cinemaRoomsService.RoomNamebyID(repertoireService.get_Idroom(info.getIDmovie(),info.getDate(), info.getDayTime()));
        Bilecik+= "W sali: " + RoomName  + " | ";
        Bilecik+= "Miejsce: " + info.getSeat()  + " | ";
        Bilecik+= "Cena: " + repertoireService.get_Price(info.getIDRepertoire())  + "ZŁ |";
        info.setPrice(repertoireService.get_Price(info.getIDRepertoire()));
        observeTicketList.add(info);
        TiList.add(Bilecik);

        //System.out.println(TicketList.size());
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDayTime() {
        return DayTime;
    }

    public void setDayTime(String dayTime) {
        DayTime = dayTime;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getSeat() {
        return Seat;
    }

    public void setSeat(int seat) {
        Seat = seat;
    }

    public int getIDmovie() {
        return IDmovie;
    }

    public void setIDmovie(int IDmovie) {
        this.IDmovie = IDmovie;
    }

    public int getIDRepertoire() {
        return IDRepertoire;
    }

    public void setIDRepertoire(int IDRepertoire) {
        this.IDRepertoire = IDRepertoire;
    }
}
