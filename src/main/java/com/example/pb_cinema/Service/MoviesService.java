package com.example.pb_cinema.Service;

import com.example.pb_cinema.BaseModel.Movies;

import java.sql.*;

import com.example.pb_cinema.Help_functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MoviesService {

    private static Connection connection;

    public MoviesService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PB_CINEMA",
                "postgres", "postgres");
    }

    public void addMovie(String title,String duration,String description) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Movies (title,duration,description) VALUES(?,?,?)");
        stmt.setString(1,title);
        stmt.setTime(2,Time.valueOf(duration));
        stmt.setString(3,description);
        stmt.executeUpdate();
        stmt.close();
    }
    public void delMovie(String title) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Movies WHERE title = ?");
        stmt.setString(1,title);
        stmt.executeUpdate();
        stmt.close();
    }
    public void updateMovie(int idmovie, String title,String duration,String description) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Movies SET title = ?,duration = ?,description = ? WHERE idmovie = ?");
        stmt.setString(1,title);
        stmt.setTime(2,Time.valueOf(duration));
        stmt.setString(3,description);
        stmt.setInt(4,idmovie);
        stmt.executeUpdate();
        stmt.close();
    }

    public ObservableList<Movies> getMoviesAll() throws SQLException {
        ObservableList<Movies> observableList = FXCollections.observableArrayList();
        PreparedStatement stmt;
        //ResultSet result = stmt.executeQuery("SELECT description FROM Movies WHERE title = :MovieName;");
        stmt =connection.prepareStatement("SELECT * FROM Movies;");
        ResultSet result = stmt.executeQuery();
        int idmovie;
        String title;
        String duration;
        String description;
        List<Movies> uList= new ArrayList<Movies>();
        while(result.next()) {
                idmovie = result.getInt("idmovie");
                title = result.getString("title");
                duration = Help_functions.TimeFormatRemover(result.getString("duration"));
                description = result.getString("description");
                Movies movie = new Movies(idmovie,title,duration,description,"");
                uList.add(movie);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }

    public ObservableList<Movies> getMovies(String date) throws SQLException {
        ObservableList<Movies> observableList = FXCollections.observableArrayList();
        PreparedStatement stmt;
        //ResultSet result = stmt.executeQuery("SELECT description FROM Movies WHERE title = :MovieName;");
        stmt =connection.prepareStatement("SELECT * FROM Movies WHERE idmovie in (SELECT Distinct(m.idmovie) FROM Movies m LEFT JOIN Repertoire r ON r.displayday=?);");
        stmt.setDate(1,Date.valueOf(date));
        ResultSet result = stmt.executeQuery();
        String title;
        String duration;
        String description;
        List<Movies> uList= new ArrayList<Movies>();
        while(result.next()) {
            int   idmovie = result.getInt("idmovie");
            String Times = get_Hours(result.getInt("idmovie"),date);
            if(Times.equals(""))
            {
                title = null;
                duration = null;
                description = null;
            }
            else
            {
                title = result.getString("title");
                duration =  Help_functions.TimeFormatRemover(result.getString("duration"));
                description = result.getString("description");
                Movies movie = new Movies(idmovie,title,duration,description, Times);
                uList.add(movie);
            }
            //System.out.println("" + idmovie + " "title+ " "duration+ " " description + " ");
        }
        observableList.addAll(uList);
        stmt.close();
        /*for(Movies b:uList){
            System.out.println(b.getIdmovie() +" "+b.getTitle()+" "+b.getDescription()+" "+b.getDuration());
        }*/
        return observableList;
    }
    public static String get_Hours(Integer idmovie,String date) throws SQLException {
        PreparedStatement stmt;
        //ResultSet result = stmt.executeQuery("SELECT description FROM Movies WHERE title = :MovieName;");
        stmt =connection.prepareStatement("select displaytime FROM Repertoire  WHERE idmovie = ? AND displayday = ?");
        stmt.setInt(1,idmovie);
        stmt.setDate(2,Date.valueOf(date));
        ResultSet result = stmt.executeQuery();
        String hour= new String();
        while(result.next()) {
            hour+= Help_functions.TimeFormatRemover(result.getString("displaytime"));
            hour+="  ";
            //System.out.println("" + idmovie + " "title+ " "duration+ " " description + " ");
        }
        stmt.close();
        return (String) hour;
    }
    public ObservableList<String> get_HoursList(Integer idmovie,String date) throws SQLException {
        PreparedStatement stmt;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        stmt =connection.prepareStatement("select displaytime FROM Repertoire WHERE idmovie = ? AND displayday = ?");
        stmt.setInt(1,idmovie);
        stmt.setDate(2,Date.valueOf(date));
        ResultSet result = stmt.executeQuery();
        List<String> uList= new ArrayList<>();
        while(result.next()) {
            String Times =  Help_functions.TimeFormatRemover(result.getString("displaytime"));
            uList.add(Times);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public int get_IdByTitle(String title) throws SQLException {
        PreparedStatement stmt;
        stmt =connection.prepareStatement("select idmovie FROM Movies WHERE title = ?");
        stmt.setString(1,title);
        ResultSet result = stmt.executeQuery();
        result.next();
        int id = result.getInt("idmovie");
        stmt.close();
        return id;
    }
    public static boolean get_SameTitle(String title) throws SQLException {
        PreparedStatement stmt;
        stmt =connection.prepareStatement("select count(title) AS ans FROM Movies WHERE title = ?");
        stmt.setString(1,title);
        ResultSet result = stmt.executeQuery();
        result.next();
        int count = result.getInt("ans");
        stmt.close();
        if(count>1)
        {return true;
        } else
        {return false;
        }
    }
    public ObservableList<String> get_Title(String date) throws SQLException {
        PreparedStatement stmt;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        stmt =connection.prepareStatement("SELECT * FROM Movies WHERE idmovie in (SELECT Distinct(m.idmovie) FROM Movies m LEFT JOIN Repertoire r ON r.displayday=?);");
        stmt.setDate(1, Date.valueOf(date));
        ResultSet result = stmt.executeQuery();
        String title;
        List<String> uList= new ArrayList<>();
        while(result.next()) {
            String Times = get_Hours(result.getInt("idmovie"),date);
            if(Times.equals(""))
            {
                title = null;
            }
            else
            {
                title = result.getString("title");
                uList.add(title);
            }
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public ObservableList<String> get_TitleNoDate() throws SQLException {
        PreparedStatement stmt;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        stmt =connection.prepareStatement("SELECT title FROM Movies;");
        ResultSet result = stmt.executeQuery();
        String title;
        List<String> uList= new ArrayList<>();
        while(result.next()) {
                title = result.getString("title");
                uList.add(title);
        }
        observableList.addAll(uList);
        stmt.close();
        return observableList;
    }
    public String get_Description(String MovieName) throws SQLException {
        PreparedStatement stmt;
        //ResultSet result = stmt.executeQuery("SELECT description FROM Movies WHERE title = :MovieName;");
        stmt =connection.prepareStatement("SELECT description from Movies WHERE  title = ?");
        stmt.setString(1, MovieName);
        ResultSet result = stmt.executeQuery();
        result.next();
        return (String) result.getString("description");
    }



}
