# PB_CINEMA_JDBC
Project for completing the subject of Databases about Reservations in Cinema.


Simple database project to help buying tickets in Cinema online.

# In UserService.java must be appropriate values [] for Database Server connection.

Class.forName("org.[Database_Server_name].Driver");
        connection = DriverManager.getConnection("jdbc:[Database_Server_name]://[IP:HOST]/[Database_name]",
                ["nick"], ["password_to_databse"]);

