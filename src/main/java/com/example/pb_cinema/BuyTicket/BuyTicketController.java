package com.example.pb_cinema.BuyTicket;

import com.example.pb_cinema.BaseModel.Movies;
import com.example.pb_cinema.Service.MoviesService;
import com.example.pb_cinema.Service.RepertoireService;
import com.example.pb_cinema.StartScreenApplication;
import com.example.pb_cinema.StartScreenController;
import com.example.pb_cinema.TicketInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ResourceBundle;

public class BuyTicketController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton;
    @FXML
    HBox WindowBar;
    @FXML
    DatePicker DatePick;
    @FXML
    Spinner<Integer> AmountTicketSpinner;
    @FXML
    TableView<Movies> MoviesTableView;
    @FXML
    private TableColumn<Movies, String> MovieNameColumn;
    @FXML
    private TableColumn<Movies, String> HoursColumn;
    @FXML
    private TableColumn<Movies, String> DurationColumn;
    @FXML
    private TableColumn<Movies, String> IdMovieColumn;

    private String ActualDate= String.valueOf(java.time.LocalDate.now());
    @FXML
    ChoiceBox MoviesChoiceBox,HoursMovieChoiceBox;

    private static double xOffset = 0;
    private static double yOffset = 0;


    private Stage primaryStage;


    private int selectMovieId;
    private int maxAmountTicket;
    private int selectAmountTicket;

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
        maxAmountTicket=7;
        IdMovieColumn.setCellValueFactory(new PropertyValueFactory<>("idmovie"));
        MovieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        DurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        HoursColumn.setCellValueFactory(new PropertyValueFactory<>("displaytimes"));
        MovieNameColumn.setSortType(        TableColumn.SortType.ASCENDING   );
        MoviesTableView.getSortOrder().add(IdMovieColumn);
        MoviesTableView.getSortOrder().add(MovieNameColumn);
        MoviesTableView.getSortOrder().add(DurationColumn);
        MoviesTableView.getSortOrder().add(HoursColumn);
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxAmountTicket);
        spinnerValueFactory.setValue(1);
        AmountTicketSpinner.setValueFactory(spinnerValueFactory);
        selectAmountTicket = AmountTicketSpinner.getValue();
        spinnerValueFactory.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                selectAmountTicket = AmountTicketSpinner.getValue();
                //System.out.println(selectAmountTicket);
            }
        });

        try {
            MoviesChoiceBox.setItems(moviesService.get_Title(ActualDate));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MoviesChoiceBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(MoviesChoiceBox.getValue());
                MoviesService moviesService = null;
                RepertoireService repertoireService = null;
                try {
                    moviesService = new MoviesService();
                    repertoireService = new RepertoireService();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    selectMovieId = moviesService.get_IdByTitle((String) MoviesChoiceBox.getValue());
                    try {
                        HoursMovieChoiceBox.setItems(moviesService.get_HoursList(selectMovieId,ActualDate));
                        String temp_hour;
                        if(HoursMovieChoiceBox.getValue()==null)
                        {
                            temp_hour="00:00";
                        }
                        else
                        {
                            temp_hour= (String) HoursMovieChoiceBox.getValue();
                        }
                        maxAmountTicket = repertoireService.get_FreePlaces(selectMovieId);
                        SpinnerValueFactory<Integer> spinnerValueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,maxAmountTicket);
                        if(AmountTicketSpinner.getValue()>maxAmountTicket)
                        {
                            spinnerValueFactory3.setValue(1);
                        }
                        AmountTicketSpinner.setValueFactory(spinnerValueFactory3);
                        spinnerValueFactory3.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                                selectAmountTicket = AmountTicketSpinner.getValue();
                                //System.out.println(selectAmountTicket);
                            }
                        });
                        //System.out.println("max: " + maxAmountTicket);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/hello-start-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        StartScreenController controller = (StartScreenController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void onDateChanged(ActionEvent event) throws SQLException, ClassNotFoundException {

        ActualDate = DatePick.getValue().toString();
        MoviesService moviesService = new MoviesService();
        MoviesTableView.setItems(moviesService.getMovies(ActualDate));
        MoviesChoiceBox.setItems(moviesService.get_Title(ActualDate));
    }
    @FXML
    public void onNextButtonClicked() throws SQLException, ClassNotFoundException, IOException {
        RepertoireService repertoireService = new RepertoireService();
        int it_amount_tickets=0;
        //System.out.println(selectAmountTicket);
        //System.out.println("ilosc: " + selectAmountTicket);
        while (it_amount_tickets<selectAmountTicket) {
            TicketInfo ticketInfo = new TicketInfo();
            ticketInfo.setIDRepertoire(repertoireService.get_Id(selectMovieId, ActualDate, (String) HoursMovieChoiceBox.getValue() + ":00",
                    repertoireService.get_Idroom(selectMovieId, ActualDate, (String) HoursMovieChoiceBox.getValue() + ":00")));
            ticketInfo.setIDmovie(selectMovieId);
            ticketInfo.setTitle(String.valueOf(MoviesChoiceBox.getValue()));
            ticketInfo.setDayTime((String) HoursMovieChoiceBox.getValue()+ ":00");
            ticketInfo.setDate(ActualDate);
            ticketInfo.setSeat(Integer.valueOf(maxAmountTicket)-it_amount_tickets);
            ticketInfo.AddTicket(ticketInfo);
            /*System.out.println("ID: " +ticketInfo.getIDRepertoire() + " FILM: " +ticketInfo.getTitle() + " MIEJSCE: "+ (Integer.valueOf(maxAmountTicket)-it_amount_tickets)
                    + " DZIEN: "+ ticketInfo.getDate() + " GODZINA: " + ticketInfo.getDayTime());*/
            it_amount_tickets++;
        }
        FXMLLoader loader = new FXMLLoader(StartScreenApplication.class.getResource("/com/example/pb_cinema/BuyTicket/final-ticket-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        FinalTicketController controller = (FinalTicketController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
