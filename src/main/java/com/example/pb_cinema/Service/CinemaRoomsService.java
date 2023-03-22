package com.example.pb_cinema.Service;

import java.sql.Connection;

import com.example.pb_cinema.BaseModel.Cinema_Rooms;
import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Help_functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CinemaRoomsService {


    private static Connection connection;

    public CinemaRoomsService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PB_CINEMA",
                "postgres", "postgres");
    }
    public void addRoom(String name,int noplaces,String facilities) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Cinema_rooms " +
                "(idroom,name,noplaces,facilities) VALUES(?,?,?,?)");
        stmt.setString(2,name);
        stmt.setInt(3,noplaces);
        stmt.setString(4,facilities);
        stmt.executeUpdate();
        stmt.close();
    }
    public void updateRoom(int idroom,String name,int noplaces,String facilities) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("Update Cinema_rooms SET " +
                "name = ?,noplaces = ?,facilities = ? WHERE idroom = ?");
        stmt.setString(1,name);
        stmt.setInt(2,noplaces);
        stmt.setString(3,facilities);
        stmt.setInt(4,idroom);
        stmt.executeUpdate();
        stmt.close();
    }
    public void delRoom(int idroom) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Movies WHERE idroom = ?");
        stmt.setInt(1,idroom);
        stmt.executeUpdate();
        stmt.close();
    }

    public ObservableList<Cinema_Rooms> getRoomAll() throws SQLException {
        ObservableList<Cinema_Rooms> observableList = FXCollections.observableArrayList();
        PreparedStatement stmt;
        stmt =connection.prepareStatement("SELECT * FROM Cinema_Rooms;");
        ResultSet result = stmt.executeQuery();
        int idroom;
        String name;
        int noplaces;
        String facilities;
        List<Cinema_Rooms> uList= new ArrayList<Cinema_Rooms>();
        while(result.next()) {
            idroom =result.getInt("idroom");
            name =result.getString("name");
            noplaces = result.getInt("noplaces");
            facilities = result.getString("facilities");

            Cinema_Rooms cinema_rooms= new Cinema_Rooms(idroom,name,noplaces,facilities);
            uList.add(cinema_rooms);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public ObservableList<String> RoomNameList() throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        PreparedStatement stmt = connection.prepareStatement("SELECT name FROM Cinema_Rooms");
        ResultSet result = stmt.executeQuery();
        String names;
        List<String> uList= new ArrayList<String>();
        while(result.next()) {
            names =result.getString("name");
            uList.add(names);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public int RoomIDbyName(String name) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT idroom FROM Cinema_Rooms WHERE name = ?");
        stmt.setString(1,name);
        ResultSet result = stmt.executeQuery();
        int id;
        result.next();
        id =result.getInt("idroom");
        stmt.close();
        return id;
    }
    public String RoomNamebyID(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT name FROM Cinema_Rooms WHERE idroom = ?");
        stmt.setInt(1,id);
        ResultSet result = stmt.executeQuery();
        String name;
        result.next();
        name =result.getString("name");
        stmt.close();
        return name;
    }
    public int RoomPlacesbyID(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT noplaces FROM Cinema_Rooms WHERE idroom = ?");
        stmt.setInt(1,id);
        ResultSet result = stmt.executeQuery();
        int places;
        result.next();
        places =result.getInt("noplaces");
        stmt.close();
        return places;
    }
}
