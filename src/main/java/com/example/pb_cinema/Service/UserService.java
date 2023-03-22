package com.example.pb_cinema.Service;

import java.sql.*;

public class UserService {


    private static Connection connection;

    public UserService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PB_CINEMA",
                "postgres", "postgres");
    }
    public void addUser(String login,String password,Timestamp CreateDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users " +
                "(login,pass,createdate,group_name) VALUES(?,?,?,?)");
        stmt.setString(1,login);
        stmt.setString(2,password);
        stmt.setTimestamp(3,CreateDate);
        stmt.setString(4,"Client");
        stmt.executeUpdate();
        stmt.close();
    }
    public int get_UserID(Timestamp CreateDate) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select iduser FROM users WHERE createdate = ?");
        stmt.setTimestamp(1,CreateDate);
        ResultSet result = stmt.executeQuery();
        result.next();
        int id = result.getInt("iduser");
        stmt.close();
        return id;
    }
    public boolean isCorrect(String username, String password) throws SQLException {
        boolean correct = false;
        if(getPassword(username).equals(password)&&isAdmin(username))
        {
            correct = true;
        }
        return correct;
    }
    public boolean isAdmin(String username) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select group_name FROM users WHERE login = ?");
        stmt.setString(1,username);
        ResultSet result = stmt.executeQuery();
        result.next();
        String group = result.getString("group_name");
        stmt.close();
        if(group.equals("Admin"))
            return true;
        else
            return false;
    }
    public String getPassword(String username) throws SQLException {
        PreparedStatement stmt;
        stmt = connection.prepareStatement("select pass FROM users WHERE login = ?");
        stmt.setString(1,username);
        ResultSet result = stmt.executeQuery();
        result.next();
        String password = result.getString("pass");
        stmt.close();
        return password;
    }
}
