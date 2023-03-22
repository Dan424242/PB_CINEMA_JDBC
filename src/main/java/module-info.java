module com.example.pb_cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.pb_cinema.BaseModel to javafx.base;
    exports com.example.pb_cinema.BaseModel;
    opens com.example.pb_cinema.BuyTicket to javafx.fxml;
    exports com.example.pb_cinema.BuyTicket;
    opens com.example.pb_cinema.LoginAdmin to javafx.fxml;
    exports com.example.pb_cinema.LoginAdmin;
    opens com.example.pb_cinema.MenuAdmin to javafx.fxml;
    exports com.example.pb_cinema.MenuAdmin;
    opens com.example.pb_cinema.MoviesList to javafx.fxml;
    exports com.example.pb_cinema.MoviesList;
    opens com.example.pb_cinema to javafx.fxml;
    exports com.example.pb_cinema;
}