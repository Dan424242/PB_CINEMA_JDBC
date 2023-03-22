package com.example.pb_cinema.Service;

import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Help_functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepertoireService {


    private Connection connection;

    public RepertoireService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PB_CINEMA",
                "postgres", "postgres");
    }
    public void addRepertoire(int idmovie,String displayday,String displaytime,int idroom,int places,float price) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Repertoire " +
                "(idmovie,displayday,displaytime,idroom,nofreeplaces,ticketprice) VALUES(?,?,?,?,?,?)");
        stmt.setInt(1,idmovie);
        stmt.setDate(2,Date.valueOf(displayday));
        stmt.setTime(3,Time.valueOf(displaytime));
        stmt.setInt(4,idroom);
        stmt.setInt(5,places);
        stmt.setFloat(6,price);
        stmt.executeUpdate();
        stmt.close();
    }
    public void updateRepertoire(int idrep,int idmovie,String displayday,String displaytime,int idroom,int places,float price) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Repertoire SET idmovie = ?,displayday = ?, displaytime = ?, idroom = ?,nofreeplaces = ?,ticketprice = ? WHERE idrep = ?");
        stmt.setInt(1,idmovie);
        stmt.setDate(2,Date.valueOf(displayday));
        stmt.setTime(3,Time.valueOf(displaytime));
        stmt.setInt(4,idroom);
        stmt.setInt(5,places);
        stmt.setFloat(6,price);
        stmt.setInt(7,idrep);
        stmt.executeUpdate();
        stmt.close();
    }
    public void delRepertoire(String title,String displayday,String displaytime) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Repertoire WHERE title = ? AND displayday = ? AND displaytime = ?");
        stmt.setString(1,title);
        stmt.setDate(2,Date.valueOf(displayday));
        stmt.setTime(3,Time.valueOf(displaytime));
        stmt.executeUpdate();
        stmt.close();
    }
    public void delRepertoirebyid(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Repertoire WHERE idrep = ?");
        stmt.setInt(1,id);
        stmt.executeUpdate();
        stmt.close();
    }

    public ObservableList<Repertoire> getRepertoiresAll() throws SQLException {
        ObservableList<Repertoire> observableList = FXCollections.observableArrayList();
        PreparedStatement stmt;
        stmt =connection.prepareStatement("SELECT * FROM Repertoire;");
        ResultSet result = stmt.executeQuery();
        int idrep;
        int idmovie;
        String moviename;
        String displayday;
        String displaytime;
        String idroom;
        String nofreeplaces;
        String ticketprice;
        List<Repertoire> uList= new ArrayList<Repertoire>();
        while(result.next()) {
            idrep =result.getInt("idrep");
            idmovie =result.getInt("idmovie");
            moviename = get_TitlebyIdmovie(idmovie);
            displayday = result.getString("displayday");
            displaytime = Help_functions.TimeFormatRemover(result.getString("displaytime"));
            idroom = result.getString("idroom");
            nofreeplaces = result.getString("nofreeplaces");
            ticketprice = result.getString("ticketprice");
            Repertoire repertoire= new Repertoire(idrep,idmovie,moviename, displayday,displaytime,idroom,nofreeplaces,ticketprice);
            uList.add(repertoire);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public String get_TitlebyIdmovie(int idmovie) throws SQLException {
        PreparedStatement stmt;
        stmt =connection.prepareStatement("select title  FROM Movies WHERE idmovie = ?");
        stmt.setInt(1,idmovie);
        ResultSet result = stmt.executeQuery();
        result.next();
        String title = result.getString("title");
        stmt.close();
        return title;
    }

    public int get_FreePlaces(int idmovie) throws SQLException {
        PreparedStatement stmt;
        stmt =connection.prepareStatement("select nofreeplaces  FROM Repertoire WHERE idmovie = ?");
        stmt.setInt(1,idmovie);
        //stmt.setDate(2, java.sql.Date.valueOf(date));
        //stmt.setTime(3,Time.valueOf(time));
        ResultSet result = stmt.executeQuery();
        result.next();
        int freeplaces = result.getInt("nofreeplaces");
        stmt.close();
        return freeplaces;
    }
    public int get_FreePlacesRep(int idrep) throws SQLException {
        PreparedStatement stmt;
        stmt =connection.prepareStatement("select nofreeplaces FROM Repertoire WHERE idrep = ?");
        stmt.setInt(1,idrep);
        ResultSet result = stmt.executeQuery();
        result.next();
        int freeplaces = result.getInt("nofreeplaces");
        stmt.close();
        return freeplaces;
    }
    public int get_Id(int movieid,String displayday,String displaytime,int idroom) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select idrep FROM repertoire WHERE idmovie = ? AND displayday = ? AND displaytime = ? AND idroom = ?");
        stmt.setInt(1,movieid);
        stmt.setDate(2,Date.valueOf(displayday));
        stmt.setTime(3,Time.valueOf(displaytime));
        stmt.setInt(4,idroom);
        ResultSet result = stmt.executeQuery();
        result.next();
        int id = result.getInt("idrep");
        stmt.close();
        return id;
    }
    public int get_Idroom(int movieid,String displayday,String displaytime) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select idroom FROM repertoire WHERE idmovie = ? AND displayday = ? AND displaytime = ?");
        stmt.setInt(1,movieid);
        stmt.setDate(2,Date.valueOf(displayday));
        stmt.setTime(3,Time.valueOf(displaytime));
        ResultSet result = stmt.executeQuery();
        result.next();
        int id = result.getInt("idroom");
        stmt.close();
        return id;
    }
    public float get_Price(int id) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select ticketprice FROM repertoire WHERE idrep = ?");
        stmt.setInt(1,id);
        ResultSet result = stmt.executeQuery();
        result.next();
        float cena = result.getInt("ticketprice");
        stmt.close();
        return cena;
    }
    public void changePlaces(int idrep,int noplaces) throws SQLException, ClassNotFoundException {
        int actualnoplaces;
        RepertoireService repertoireService = new RepertoireService();
        actualnoplaces=repertoireService.get_FreePlacesRep(idrep);
        actualnoplaces-=noplaces;
        PreparedStatement stmt = connection.prepareStatement("UPDATE Repertoire SET nofreeplaces = ? WHERE idrep = ?");
        stmt.setInt(1,actualnoplaces);
        stmt.setInt(2,idrep);
        stmt.executeUpdate();
        stmt.close();
    }

}
