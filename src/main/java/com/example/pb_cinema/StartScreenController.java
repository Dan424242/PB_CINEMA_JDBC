package com.example.pb_cinema;

import com.example.pb_cinema.BuyTicket.BuyTicketController;
import com.example.pb_cinema.LoginAdmin.LoginController;
import com.example.pb_cinema.MoviesList.MoviesListController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {

@FXML
Button MinimizeButton,ExitButton,AdminButton,CheckRepertuarButton,BuyTicketButton;
@FXML
HBox WindowBar;

    private static double xOffset = 0;
    private static double yOffset = 0;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    public void onAdminButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/LoginAdmin/login-admin-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        LoginController controller = (LoginController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onCheckRepertuarButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/MoviesList/movies-list-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MoviesListController controller = (MoviesListController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onBuyTicketButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/BuyTicket/buy-ticket-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        BuyTicketController controller = (BuyTicketController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}