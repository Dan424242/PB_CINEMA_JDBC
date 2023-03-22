package com.example.pb_cinema.Service;

import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Help_functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private static Connection connection;

    public ReservationService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PB_CINEMA",
                "postgres", "postgres");
    }
    public void addReservation(int idusr,Timestamp Time,int noplaces,int idrep,float cost) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO reservation " +
                "(iduser,timestamp,noplaces,idrep,cost) VALUES(?,?,?,?,?)");
        stmt.setInt(1,idusr);
        stmt.setTimestamp(2,Time);
        stmt.setInt(3, noplaces);
        stmt.setInt(4,idrep);
        stmt.setFloat(5,cost);
        stmt.executeUpdate();
        stmt.close();
    }
    public void getReservation() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservation");
            ObservableList<Repertoire> observableList = FXCollections.observableArrayList();
            ResultSet result = stmt.executeQuery();
            int idres;
            int idusr;
            int idrep;
            int noplaces;
            float cost;
            String Time;
            while(result.next()) {
                idrep = result.getInt("idrep");
                idusr=result.getInt("iduser");
                idres = result.getInt("idures");
                noplaces = result.getInt("noplaces");
                cost = result.getFloat("cost");
                Time = result.getString("timestamp");
                System.out.println(idres + " " + idrep + " " + idusr + " " + noplaces + " " + cost + " " + Time);
            }
        stmt.close();

    }

}
