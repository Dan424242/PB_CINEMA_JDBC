package com.example.pb_cinema.MoviesList;

import com.example.pb_cinema.BaseModel.Movies;
import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Service.MoviesService;
import com.example.pb_cinema.Service.RepertoireService;
import com.example.pb_cinema.StartScreenApplication;
import com.example.pb_cinema.StartScreenController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MoviesListController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton;
    @FXML
    HBox WindowBar;
    @FXML
    Label DescriptionTextLabel;
    @FXML
    DatePicker DatePick;

    @FXML
    TableView<Movies> MoviesTableView;
    @FXML
    private TableColumn<Movies, String> MovieNameColumn;
    @FXML
    private TableColumn<Movies, String> HoursColumn;
    @FXML
    private TableColumn<Movies, String> DurationColumn;

    private String ActualDate= String.valueOf(java.time.LocalDate.now());

    private static double xOffset = 0;
    private static double yOffset = 0;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DatePick.setValue(LocalDate.parse(ActualDate));
        MoviesTableView.setPlaceholder(
                new Label("Brak Filmów tego dnia"));
        MoviesService moviesService = null;
        try {
            moviesService = new MoviesService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            MoviesTableView.setItems(moviesService.getMovies(ActualDate));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MovieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        DurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        HoursColumn.setCellValueFactory(new PropertyValueFactory<>("displaytimes"));
        MovieNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        MoviesTableView.getSortOrder().add(MovieNameColumn);
        MoviesTableView.getSortOrder().add(DurationColumn);
        MoviesTableView.getSortOrder().add(HoursColumn);
        //System.out.println(repertoireService.getHours(2));

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
    public void onSelectedRowClicked() throws SQLException, ClassNotFoundException {
        String name = MoviesTableView.getSelectionModel().getSelectedItem().getTitle();
        MoviesService moviesService = new MoviesService();
        DescriptionTextLabel.setText(moviesService.get_Description(name));
    }
    @FXML
    public void onDateChanged(ActionEvent event) throws SQLException, ClassNotFoundException {

        ActualDate = DatePick.getValue().toString();
        MoviesService moviesService = new MoviesService();
        MoviesTableView.setItems(moviesService.getMovies(ActualDate));
    }

}
