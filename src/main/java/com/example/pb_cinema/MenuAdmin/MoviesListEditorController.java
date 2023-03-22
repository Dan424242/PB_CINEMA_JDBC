package com.example.pb_cinema.MenuAdmin;

import com.example.pb_cinema.BaseModel.Movies;
import com.example.pb_cinema.Help_functions;
import com.example.pb_cinema.Service.MoviesService;
import com.example.pb_cinema.StartScreenApplication;
import com.example.pb_cinema.StartScreenController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class MoviesListEditorController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton,AddMovieButton,DeleteMovieButton;
    @FXML
    HBox WindowBar;
    @FXML
    TableView<Movies> MoviesTableView;
    @FXML
    private TableColumn<Movies, String> MovieNameColumn;
    @FXML
    private TableColumn<Movies, String> DurationColumn;
    @FXML
    private TableColumn<Movies, String> DescriptionColumn;
    @FXML
    private TextField NameMovieTextField;
    @FXML
    private TextArea DescriptionTextArea;
    @FXML
    Spinner<Integer> HourDurationField,MinutesDurationField;

    private static double xOffset = 0;
    private static double yOffset = 0;

    private Stage primaryStage;


    int InputHourSpinner,InputMinutesSpinner;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MoviesService moviesService = null;
        try {
            moviesService = new MoviesService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            MoviesTableView.setItems(moviesService.getMoviesAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MoviesTableView.setEditable(true);
        MovieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        MovieNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        MovieNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Movies, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Movies, String> event) {
                Movies movies = event.getRowValue();
                movies.setTitle(event.getNewValue());
            }
        });
        DurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        DurationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        DurationColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Movies, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Movies, String> event) {
                Movies movies = event.getRowValue();
                //System.out.println("dl: " + event.getNewValue());
                if(event.getNewValue().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$"))
                {
                    movies.setDuration(event.getNewValue());
                }
                MoviesTableView.refresh();
            }
        });
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        DescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        DescriptionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Movies, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Movies, String> event) {
                Movies movies = event.getRowValue();
                movies.setDescription(event.getNewValue());
            }
        });

        MoviesTableView.getSortOrder().add(MovieNameColumn);
        MoviesTableView.getSortOrder().add(DurationColumn);
        MoviesTableView.getSortOrder().add(DescriptionColumn);


        SpinnerValueFactory<Integer> spinnerHoursValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,24);
        SpinnerValueFactory<Integer> spinnerMinutesValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);
        spinnerHoursValueFactory.setValue(0);
        spinnerMinutesValueFactory.setValue(0);
        HourDurationField.setValueFactory(spinnerHoursValueFactory);
        MinutesDurationField.setValueFactory(spinnerMinutesValueFactory);
        InputHourSpinner=HourDurationField.getValue();
        InputMinutesSpinner=MinutesDurationField.getValue();
        //selectAmountTicket = HourDutationField.getValue();
        spinnerHoursValueFactory.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                InputHourSpinner=HourDurationField.getValue();
                //selectAmountTicket = AmountTicketSpinner.getValue();
                //System.out.println(selectAmountTicket);
            }
        });
        spinnerHoursValueFactory.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                InputMinutesSpinner=MinutesDurationField.getValue();
                //selectAmountTicket = AmountTicketSpinner.getValue();
                //System.out.println(selectAmountTicket);
            }
        });


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

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/MenuAdmin/menu-admin-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MenuAdminController controller = (MenuAdminController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onSaveEditButtonClicked() throws SQLException, ClassNotFoundException {
        ObservableList<Movies> items = MoviesTableView.getItems();

        int list_it=0;
        int repeat_it=0;
        while(list_it<items.size())
        {
            if(items.get(list_it).getTitle().equals(""))
            {
                items.get(list_it).setTitle("Movie" + list_it);
                repeat_it++;
            }
            if(MoviesService.get_SameTitle(items.get(list_it).getTitle()))
            {
                repeat_it++;
                items.get(list_it).setTitle(items.get(list_it).getTitle() + repeat_it);

            }
            MoviesService moviesService = new MoviesService();
            moviesService.updateMovie(items.get(list_it).getIdmovie(),items.get(list_it).getTitle(),
                    items.get(list_it).getDuration()+":00",items.get(list_it).getDescription());
            MoviesTableView.setItems(moviesService.getMoviesAll());
            //System.out.println("|ID: " +items.get(list_it).getIdmovie() + "|FILM: " +items.get(list_it).getTitle()+ "|CZAS TRWANIA: "
            //        + items.get(list_it).getDuration() + "|OPIS: " + items.get(list_it).getDescription());
            list_it++;
        }
    }
    @FXML
    public void onAddMovieButtonClicked() throws SQLException, ClassNotFoundException {
        if(NameMovieTextField.getText().equals(""))
        {
            NameMovieTextField.setText("Movie");
        }
        ObservableList<Movies> items = MoviesTableView.getItems();
        int list_it=0;
        int repeat_it=0;
        while(list_it<items.size())
        {
            if(NameMovieTextField.getText().equals(""))
            {
                NameMovieTextField.setText("Movie" + repeat_it);
                repeat_it++;
            }
            if(NameMovieTextField.getText().equals(items.get(list_it).getTitle()))
            {
                repeat_it++;
                NameMovieTextField.setText(NameMovieTextField.getText() + repeat_it);
            }
            list_it++;
        }
        MoviesService moviesService = new MoviesService();
        String SelectTime = Help_functions.StringBoxToTimeFormatConvert(HourDurationField.getValue(),MinutesDurationField.getValue());
        moviesService.addMovie(NameMovieTextField.getText(),
                SelectTime,
                DescriptionTextArea.getText());
        MoviesTableView.setItems(moviesService.getMoviesAll());
        //MoviesTableView.refresh();
        //System.out.println("|FILM: " +NameMovieTextField.getText()+ "|CZAS TRWANIA: " + SelectTime + "|OPIS: " + DescriptionTextArea.getText());
    }
    @FXML
    public void onDeleteMovieButtonClicked() throws SQLException, ClassNotFoundException {
        Movies selectmovie = MoviesTableView.getSelectionModel().getSelectedItem();
        String DeleteRowTitle;
        if(selectmovie==null)
        {
            //System.out.println("nie wybrano");
            ObservableList<Movies> items = MoviesTableView.getItems();
            //System.out.println(items.size());
            Movies selectmovie2 = items.get(items.size()-1);
            DeleteRowTitle = selectmovie2.getTitle();
        }
        else
        {
            DeleteRowTitle = selectmovie.getTitle();
        }
        MoviesService moviesService = new MoviesService();
        moviesService.delMovie(DeleteRowTitle);
        MoviesTableView.setItems(moviesService.getMoviesAll());
        //MoviesTableView.refresh();


    }
}
