package com.example.pb_cinema.MenuAdmin;

import com.example.pb_cinema.StartScreenApplication;
import com.example.pb_cinema.StartScreenController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuAdminController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton;
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
    public void onLogoutButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/hello-start-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        StartScreenController controller = (StartScreenController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onMoviesListClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/MenuAdmin/movies-list-editor-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MoviesListEditorController controller = (MoviesListEditorController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onRepertoireEditorClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/MenuAdmin/repertoire-editor-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        RepertoireEditorController controller = (RepertoireEditorController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
