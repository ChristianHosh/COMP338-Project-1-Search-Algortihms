package ai.comp338_project_1.search;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Drawable {
    private static Line createPathLine(double[] start, double[] end, Color color) {
        Line line = new Line();

        line.setStartX(start[0]);
        line.setStartY(start[1]);
        line.setEndX(end[0]);
        line.setEndY(end[1]);
        line.setFill(color);
        line.setStroke(color);
        line.setStrokeWidth(1.5);

        return line;
    }

    private static Circle createPathCircle(double[] start, Color color) {
        Circle circle = new Circle();

        circle.setFill(color);
        circle.setLayoutX(start[0]);
        circle.setLayoutY(start[1]);
        circle.setRadius(3);

        return circle;
    }

    public static Group drawPathFromDestination(CityNode destination, CityNode source, Color pathColor) {
        ArrayList<CityNode> path = new ArrayList<>();

        CityNode current = destination;
        while (current != null) {
            path.add(current);
            current = current.previous;
        }

        if ((path.get(0)) != destination || path.get(path.size() - 1) != source) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't find a path to your destination (in drawable)", ButtonType.CLOSE);
            alert.show();
            return null;
        }
        Group currentTrip = new Group();

        double[] destinationPoint = path.get(0).getCoordinates();


        for (int i = 1; i < path.size(); i++) {
            double[] point1 = path.get(i - 1).getCoordinates();
            double[] point2 = path.get(i).getCoordinates();
            Line line = createPathLine(point1, point2, pathColor);
            Circle endCircle = createPathCircle(point2, pathColor);

            currentTrip.getChildren().addAll(line, endCircle);
        }
        Circle destinationCircle = createPathCircle(destinationPoint, Color.RED);
        currentTrip.getChildren().add(destinationCircle);
        currentTrip.toFront();

        return currentTrip;
    }
}
