package com.example.pb_cinema.BuyTicket;

import com.example.pb_cinema.Service.RepertoireService;
import com.example.pb_cinema.Service.ReservationService;
import com.example.pb_cinema.Service.UserService;
import com.example.pb_cinema.StartScreenApplication;
import com.example.pb_cinema.StartScreenController;
import com.example.pb_cinema.TicketInfo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.UUID.randomUUID;

public class FinalTicketController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton;
    @FXML
    HBox WindowBar;
    @FXML
    ListView<String> TicketList;
    @FXML
    Label PriceLabel,InfoBuyLabel;
    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    TextField FirstNameTextField,LastNameTextField;
    int CountTicket;
    private Stage primaryStage;
    Date date = new Date();
    Timestamp ts;
    float allprice;
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        int list_it=0;
        //System.out.println(TicketInfo.TiList.size());
        while (list_it<TicketInfo.TiList.size())
        {
                TicketList.getItems().add(list_it,TicketInfo.TiList.get(list_it));
                list_it++;
        }
        allprice = TicketInfo.observeTicketList.get(0).getPrice();
        CountTicket = TicketInfo.TiList.size();
        allprice = allprice*CountTicket;
        PriceLabel.setText( allprice + " ZŁ");


        WindowBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        WindowBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        MinimizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setIconified(true);
            }
        });
        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

    }
    @FXML
    public void onBackButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/hello-start-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        StartScreenController controller = (StartScreenController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onBuyButtonClicked() throws IOException, SQLException, ClassNotFoundException {

        String FirstName = FirstNameTextField.getText();
        String LastName = LastNameTextField.getText();
        String Login,Password="user123";
        Password = String.valueOf(randomUUID());

        if(FirstName.isEmpty()&&LastName.isEmpty())
        {
            Login = "AnonimUser"+randomUUID();
        }
        else
        {
            Login=FirstName+LastName;
        }
        ts=new Timestamp(date.getTime());
        //System.out.println(ts);
        UserService userService = new UserService();
        userService.addUser(Login,Password,ts);
        //System.out.println(userService.get_UserID(ts));
        ReservationService reservationService = new ReservationService();
        RepertoireService repertoireService = new RepertoireService();
        ts=new Timestamp(date.getTime());
        reservationService.addReservation(userService.get_UserID(ts),
                ts,CountTicket,TicketInfo.observeTicketList.get(0).getIDRepertoire(),allprice);
        repertoireService.changePlaces(TicketInfo.observeTicketList.get(0).getIDRepertoire(),CountTicket);

        InfoBuyLabel.setText("ZAREZERWOWANO MIEJSCA!");
    }

}
