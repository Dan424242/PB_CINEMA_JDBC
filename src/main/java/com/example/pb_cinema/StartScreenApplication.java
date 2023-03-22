package com.example.pb_cinema;

import com.example.pb_cinema.LoginAdmin.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartScreenApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("hello-start-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        StartScreenController controller = (StartScreenController) loader.getController();

        controller.setPrimaryStage(stage);

        stage.setHeight(800);
        stage.setWidth(1200);
        stage.setTitle("CINEMA ASSISTANT");
        stage.setX(400);
        stage.setY(120);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}