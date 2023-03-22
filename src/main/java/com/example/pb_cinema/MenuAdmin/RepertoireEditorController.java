package com.example.pb_cinema.MenuAdmin;

import com.example.pb_cinema.BaseModel.Cinema_Rooms;
import com.example.pb_cinema.BaseModel.Movies;
import com.example.pb_cinema.BaseModel.Repertoire;
import com.example.pb_cinema.Help_functions;
import com.example.pb_cinema.Service.CinemaRoomsService;
import com.example.pb_cinema.Service.MoviesService;
import com.example.pb_cinema.Service.RepertoireService;
import com.example.pb_cinema.StartScreenApplication;
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
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RepertoireEditorController implements Initializable {
    @FXML
    Button MinimizeButton,ExitButton;
    @FXML
    HBox WindowBar;
    @FXML
    DatePicker DatePicker;
    @FXML
    TableView<Repertoire> RepertoiresTableView;
    /*@FXML
    private TableColumn<Repertoire,Integer> IDColumn;
    @FXML
    private TableColumn<Repertoire, Integer> MovieIdColumn;*/
    @FXML
    private TableColumn<Repertoire, String> MovieNameColumn;
    @FXML
    private TableColumn<Repertoire, String> DayColumn;
    @FXML
    private TableColumn<Repertoire, String> HoursColumn;
    @FXML
    private TableColumn<Repertoire, String> IdRoomColumn;
    @FXML
    private TableColumn<Repertoire, String> FreePlacesColumn;
    @FXML
    private TableColumn<Repertoire, String> PriceColumn;



    @FXML
    private ChoiceBox<String> NameMovieChoiceBox,RoomIDChoiceBox;
    @FXML
    Spinner<Integer> HourSelectField,MinutesSelectField;
    @FXML
    TextField PriceTextField,PriceTextField2;



    private static double xOffset = 0;
    private static double yOffset = 0;
    private Stage primaryStage;

    int SelectPlaces;
    int SelectIDroom;
    int InputHourSpinner=0,InputMinutesSpinner=0;
    float InputPrice;
    private String ActualDate= String.valueOf(java.time.LocalDate.now());

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatePicker.setValue(LocalDate.parse(ActualDate));
        RepertoireService repertoireService = null;
        try {
            repertoireService = new RepertoireService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            RepertoiresTableView.setItems(repertoireService.getRepertoiresAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RepertoiresTableView.setEditable(true);
        MovieNameColumn.setCellValueFactory(new PropertyValueFactory<>("moviename"));
        MovieNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        DayColumn.setCellValueFactory(new PropertyValueFactory<>("displayday"));
        DayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        HoursColumn.setCellValueFactory(new PropertyValueFactory<>("displaytime"));
        HoursColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        IdRoomColumn.setCellValueFactory(new PropertyValueFactory<Repertoire, String>("idroom"));
        IdRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        FreePlacesColumn.setCellValueFactory(new PropertyValueFactory<Repertoire, String>("nofreeplaces"));
        FreePlacesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Repertoire, String>("ticketprice"));
        PriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //RepertoiresTableView.getSortOrder().add(IDColumn);
        //RepertoiresTableView.getSortOrder().add(MovieIdColumn);
        RepertoiresTableView.getSortOrder().add(MovieNameColumn);
        RepertoiresTableView.getSortOrder().add(DayColumn);
        RepertoiresTableView.getSortOrder().add(HoursColumn);
        RepertoiresTableView.getSortOrder().add(IdRoomColumn);
        RepertoiresTableView.getSortOrder().add(FreePlacesColumn);
        RepertoiresTableView.getSortOrder().add(PriceColumn);


        MovieNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                //Repertoire repertoire = event.getRowValue();
                //repertoire.setMoviename(event.getNewValue());
            }
        });
        DayColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                Repertoire repertoire = event.getRowValue();
                if (event.getNewValue().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
                    repertoire.setDisplayday(event.getNewValue());

                }
                RepertoiresTableView.refresh();
            }
        });
        HoursColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                Repertoire repertoire = event.getRowValue();
                if (event.getNewValue().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")) {
                    repertoire.setDisplaytime(event.getNewValue());
                }
                RepertoiresTableView.refresh();
            }
        });
        IdRoomColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                Repertoire repertoire = event.getRowValue();
                if (event.getNewValue().matches("^\\d+$")) {
                    repertoire.setIdroom(event.getNewValue());
                }
                RepertoiresTableView.refresh();
            }
        });
        FreePlacesColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                Repertoire repertoire = event.getRowValue();
                if (event.getNewValue().matches("^\\d+$")) {
                    repertoire.setNofreeplaces(event.getNewValue());
                }
                RepertoiresTableView.refresh();
            }
        });
        PriceColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Repertoire, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Repertoire, String> event) {
                Repertoire repertoire = event.getRowValue();
                if (event.getNewValue().matches("^[0-9]+(?:\\.[0-9]+)?$")) {
                    repertoire.setTicketprice(event.getNewValue());
                }
                RepertoiresTableView.refresh();
            }
        });

        SpinnerValueFactory<Integer> spinnerHoursValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> spinnerMinutesValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        spinnerHoursValueFactory.setValue(0);
        spinnerMinutesValueFactory.setValue(0);
        HourSelectField.setValueFactory(spinnerHoursValueFactory);
        MinutesSelectField.setValueFactory(spinnerMinutesValueFactory);
        InputHourSpinner = HourSelectField.getValue();
        InputMinutesSpinner = MinutesSelectField.getValue();
        spinnerHoursValueFactory.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                InputHourSpinner = HourSelectField.getValue();
            }
        });
        spinnerHoursValueFactory.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                InputMinutesSpinner = MinutesSelectField.getValue();
            }
        });
        MoviesService moviesService = null;
        try {
            moviesService = new MoviesService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            NameMovieChoiceBox.setItems(moviesService.get_TitleNoDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CinemaRoomsService cinemaRoomsService = null;
        try {
            cinemaRoomsService = new CinemaRoomsService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PriceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if(!newValue.matches("[0-9]+"))
                {
                    PriceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        PriceTextField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if(!newValue.matches("[0-9]*[0-9]*"))
                {
                        System.out.println(newValue.length());
                        PriceTextField2.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if(newValue.length()>2)
                {
                    PriceTextField2.setText("0");
                }
            }
        });
        try {
            RoomIDChoiceBox.setItems(cinemaRoomsService.RoomNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
    public void onDateChanged() {
        ActualDate = DatePicker.getValue().toString();
    }

    @FXML
    public void onSaveEditButtonClicked() throws SQLException, ClassNotFoundException {
        ObservableList<Repertoire> items = RepertoiresTableView.getItems();
        int list_it=0;

        while(list_it<items.size())
        {
            //System.out.println("IT: " +list_it);
            RepertoireService repertoireService  = new RepertoireService();
            MoviesService moviesService = new MoviesService();

            String day = items.get(list_it).getDisplayday();
            String time = items.get(list_it).getDisplaytime()+":00";
            int IDmovie = moviesService.get_IdByTitle(items.get(list_it).getMoviename());
            int IDroom = Integer.parseInt(items.get(list_it).getIdroom());
            int FreePlaces = Integer.parseInt(items.get(list_it).getNofreeplaces());
            float TicketPrice = Float.parseFloat(items.get(list_it).getTicketprice());
            int ID = repertoireService.get_Id(IDmovie,day,time,IDroom);

            /*System.out.println(ID);
            System.out.println(ID + " | " +IDmovie+ " | " +
                    day + " | " +
                    time + " | " +
                    IDroom+ " | " +
                    FreePlaces+ " | " +
                    TicketPrice+ " | ");*/
            repertoireService.updateRepertoire(
                    ID,
                    IDmovie,
                    items.get(list_it).getDisplayday(),
                    items.get(list_it).getDisplaytime()+":00",
                    IDroom,
                    FreePlaces,
                    TicketPrice);
                    RepertoiresTableView.setItems(repertoireService.getRepertoiresAll());
            list_it++;
        }


    }
    @FXML
    public void onAddMovieButtonClicked() throws SQLException, ClassNotFoundException {
        String NameMovie = NameMovieChoiceBox.getSelectionModel().getSelectedItem();
        String Price_temp;
        if(NameMovie == null)
        {
            NameMovie = NameMovieChoiceBox.getItems().get(0);
            //System.out.println(NameMovie);
        }
        String time = Help_functions.StringBoxToTimeFormatConvert(InputHourSpinner,InputMinutesSpinner);
        if(PriceTextField.getText().equals(""))
        {
            PriceTextField.setText("00");
        }
        if(PriceTextField2.getText().equals(""))
        {
            PriceTextField2.setText("00");
        }
        Price_temp = PriceTextField.getText() + "." + PriceTextField2.getText();
        //System.out.println(Price);
        InputPrice = Float.valueOf(Price_temp);
        //System.out.println(InputPrice);
        String NameRoom = RoomIDChoiceBox.getSelectionModel().getSelectedItem();
        if(NameRoom == null)
        {
            NameRoom = RoomIDChoiceBox.getItems().get(0);
        }
        CinemaRoomsService cinemaRoomsService = new CinemaRoomsService();
        SelectIDroom = cinemaRoomsService.RoomIDbyName(NameRoom);
        SelectPlaces = cinemaRoomsService.RoomPlacesbyID(SelectIDroom);
        MoviesService moviesService = new MoviesService();
        int SelectIDMovie = moviesService.get_IdByTitle(NameMovie);
        RepertoireService repertoireService = new RepertoireService();
        repertoireService.addRepertoire(SelectIDMovie,ActualDate,time,SelectIDroom,SelectPlaces,InputPrice);
        RepertoiresTableView.setItems(repertoireService.getRepertoiresAll());
    }
    @FXML
    public void onDeleteMovieButtonClicked() throws SQLException, ClassNotFoundException {
        Repertoire repertoire = RepertoiresTableView.getSelectionModel().getSelectedItem();
        int DeleteRowid;
        if(repertoire == null)
        {
            ObservableList<Repertoire> items = RepertoiresTableView.getItems();
            //System.out.println(items.size());
            Repertoire selectrep= items.get(items.size()-1);
            DeleteRowid = selectrep.getId();
        }
        else
        {
            DeleteRowid = repertoire.getId();
        }
        RepertoireService repertoireService = new RepertoireService();
        repertoireService.delRepertoirebyid(DeleteRowid);
        RepertoiresTableView.setItems(repertoireService.getRepertoiresAll());
    }
}
