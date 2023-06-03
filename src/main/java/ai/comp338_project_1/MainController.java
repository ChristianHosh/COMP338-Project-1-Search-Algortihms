package ai.comp338_project_1;

import ai.comp338_project_1.search.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;

public class MainController {

    // COMPONENTS
    @FXML
    private ComboBox<CityNode> combo_select_destination;

    @FXML
    private ComboBox<CityNode> combo_select_source;

    @FXML
    private AnchorPane image_canvas;

    @FXML
    private Button button_run;

    @FXML
    private ImageView image_map;

    @FXML
    private TextField textfield_AS_time;

    @FXML
    private TextField textfield_AS_time_complex;

    @FXML
    private TextField textfield_UCS_time;

    @FXML
    private TextField textfield_UCS_time_complex;

    // OTHER VARIABLES
    private static HashMap<Integer, CityNode> CITIES_MAP;
    private static boolean CHOSE_SOURCE_FLAG;
    private static boolean CHOSE_DESTINATION_FLAG;
    private CityNode selectedSource = null;
    private CityNode selectedDestination = null;
    private final Label hoverLabel = new Label();
    private static Group LAST_UCS_TRIP_GROUP = new Group();
    private static Group LAST_AS_TRIP_GROUP = new Group();



    // ACTION EVENTS
    @FXML
    void clearASPath(MouseEvent event) {
        LAST_AS_TRIP_GROUP.setVisible(false);
    }

    @FXML
    void clearUCSPath(MouseEvent event) {
        LAST_UCS_TRIP_GROUP.setVisible(false);
    }

    @FXML
    void showASPath(MouseEvent event) {
        LAST_AS_TRIP_GROUP.setVisible(true);
    }

    @FXML
    void showUCSPath(MouseEvent event) {
        LAST_UCS_TRIP_GROUP.setVisible(true);
    }

    @FXML
    void clearAll(ActionEvent event) {
        clearMap();
    }

    @FXML
    void run(ActionEvent event) {
        CityNode source = combo_select_source.getSelectionModel().getSelectedItem();
        CityNode destination = combo_select_destination.getSelectionModel().getSelectedItem();
        if (source == null || destination == null) {
            source = selectedSource;
            destination = selectedDestination;
            if (source == null || destination == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid source and destination!", ButtonType.CLOSE);
                alert.show();
                return;
            }
        }

        clearMap();
        System.out.println("CLEARED MAP 1");

        System.out.println("STARTING A* SEARCH WITH " + source + " TO " + destination);
        cleanNodeData();
        long time_asStart = System.nanoTime();
        AStarSearch starSearch = new AStarSearch(CITIES_MAP);
        CityNode starPathDestination = starSearch.findPath(source, destination);
        long time_asEnd = System.nanoTime();
//        drawASPath(source, starPathDestination);
        System.out.println("FINISHED A* SEARCH");


        System.out.println("STARTING UC SEARCH  " + source + " TO " + destination);
        cleanNodeData();
        long time_ucsStart = System.nanoTime();
        UniformCostSearch UCSearch = new UniformCostSearch(CITIES_MAP);
        CityNode ucPathDestination = UCSearch.findPath(source, destination);
        long time_ucsEnd = System.nanoTime();

        drawUCSPath(source, ucPathDestination);
        System.out.println("FINISHED UC SEARCH");

        long time_ucsDuration = (time_ucsEnd - time_ucsStart) / 1000;  //divide by 1000000 to get milliseconds.
        long time_asDuration = (time_asEnd - time_asStart) / 1000;

        textfield_UCS_time.setText(time_ucsDuration + " μs");
        textfield_AS_time.setText(time_asDuration + " μs");

    }



    // SETUP
    private void combosSetItems() {
        ObservableList<CityNode> cityNodeObservableList = FXCollections.observableArrayList(CITIES_MAP.values());

        combo_select_source.setItems(cityNodeObservableList);
        combo_select_destination.setItems(cityNodeObservableList);
    }

    private void setUpStatics() {
        CITIES_MAP = csvReader.getCities();

        CHOSE_SOURCE_FLAG = false;
        CHOSE_DESTINATION_FLAG = false;
    }

    private void drawPointsOnMap() {
        for (CityNode city : CITIES_MAP.values()) {
            Circle cityDot = createCityDot(city);
            image_canvas.getChildren().add(cityDot);
        }
    }

    private Circle createCityDot(CityNode city) {
        double[] coordinates = city.getCoordinates();

        Circle cityDot = new Circle();
        cityDot.setFill(Color.TRANSPARENT);
        cityDot.setLayoutX(coordinates[0]);
        cityDot.setLayoutY(coordinates[1]);
        cityDot.setRadius(10);
        cityDot.setStyle("-fx-cursor: hand");
        cityDot.setOnMouseClicked(mouseEvent -> clickOnCity(city));
        cityDot.setOnMouseEntered(mouseEvent -> {
            hoverLabel.setText(city.getName());
            hoverLabel.setLayoutX(coordinates[0] - 20);
            hoverLabel.setLayoutY(coordinates[1] - 20);
            hoverLabel.setVisible(true);
            hoverLabel.toFront();
        });

        cityDot.setOnMouseExited(mouseEvent -> hoverLabel.setVisible(false));
        return cityDot;
    }

    private void clickOnCity(CityNode selectedCity) {
        if (!CHOSE_SOURCE_FLAG) {
            CHOSE_SOURCE_FLAG = true;
            selectedSource = selectedCity;
            combo_select_source.setValue(selectedCity);
            System.out.println("SOURCE: " + selectedCity);
        } else if (!CHOSE_DESTINATION_FLAG) {
            CHOSE_DESTINATION_FLAG = true;
            selectedDestination = selectedCity;
            combo_select_destination.setValue(selectedCity);

            System.out.println("DESTINATION: " + selectedCity);
        }
    }

    // MAP INTERACTIONS
    private void drawASPath(CityNode source, CityNode destination) {
        if (destination == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't find a path to your destination", ButtonType.CLOSE);
            alert.show();
            return;
        }

        Group drawableTrip = Drawable.drawPathFromDestination(destination, source, Color.LIGHTBLUE);
        clearMap();

        LAST_AS_TRIP_GROUP = drawableTrip;
        image_canvas.getChildren().add(drawableTrip);
    }

    private void drawUCSPath(CityNode source, CityNode destination) {
        if (destination == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't find a path to your destination", ButtonType.CLOSE);
            alert.show();
            return;
        }

        Group drawableTrip = Drawable.drawPathFromDestination(destination, source, Color.RED);
        clearMap();

        LAST_UCS_TRIP_GROUP = drawableTrip;
        image_canvas.getChildren().add(drawableTrip);
    }

    private void clearMap() {
        CHOSE_DESTINATION_FLAG = false;
        CHOSE_SOURCE_FLAG = false;
        if (LAST_UCS_TRIP_GROUP != null) {
            image_canvas.getChildren().remove(LAST_UCS_TRIP_GROUP);
        }
    }

    @FXML
    public void initialize() {
        hoverLabel.setStyle("-fx-background-color: WHITE ; -fx-border-radius: 10px");
        hoverLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        image_canvas.getChildren().add(hoverLabel);

        setUpStatics();

        combosSetItems();

        drawPointsOnMap();

        cleanNodeData();
    }

    private void cleanNodeData() {
        for (CityNode node : CITIES_MAP.values()) {
            node.cost = 0;
            node.previous = null;
        }
    }
}
