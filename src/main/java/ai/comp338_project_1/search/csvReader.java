package ai.comp338_project_1.search;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class csvReader {
    private static final File CITIES_FILE = new File("./search/files/Cities.csv");
    private static final File ROADS_FILE = new File("./search/files/Roads.csv");
    private static final HashMap<Integer, GraphNode> CITY_NODES = new HashMap<>();

    @NotNull
    public static HashMap<Integer, GraphNode> getCities() {
        try {
            Scanner scannerCityFile = new Scanner(CITIES_FILE);
            while (scannerCityFile.hasNext()){
                String row = scannerCityFile.nextLine();
                String[] column = row.split(",");

                int city_id = Integer.parseInt(column[0]);
                String city_name = column[1];
                double city_xLocation = Double.parseDouble(column[2]);
                double city_yLocation = Double.parseDouble(column[3]);
                double city_latitude = Double.parseDouble(column[4]);
                double city_longitude = Double.parseDouble(column[5]);

                GraphNode graphNode = new GraphNode(city_name, city_xLocation, city_yLocation, city_latitude, city_longitude);
                CITY_NODES.put(city_id, graphNode);

            }

        } catch (FileNotFoundException exception) {
            System.out.println("FILE ERROR: " + exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("PARSING ERROR: " + exception.getMessage());
        } catch (Exception others) {
            System.out.println("ERROR: " + others.getMessage());
        }
        getEdges();
        return CITY_NODES;
    }

    private static void getEdges() {
        try {
            Scanner scannerRoadFile = new Scanner(ROADS_FILE);
            int row = 1;
            while (scannerRoadFile.hasNext()){
                String rowString = scannerRoadFile.nextLine();
                String[] colsString = rowString.split(",");

                GraphNode currentRowCity = CITY_NODES.get(row);
                for (int col = 1; col < colsString.length + 1; col++) {
                    if (col == row) continue;

                    GraphNode currentColCity = CITY_NODES.get(col);

                    String scannedColumn = colsString[col-1];

                    if (scannedColumn.equalsIgnoreCase("NONE")) continue;

                    double scannedDistance = Double.parseDouble(scannedColumn);
                    currentRowCity.addBranch(scannedDistance, currentColCity);
                }
                row++;
            }

        } catch (FileNotFoundException exception) {
            System.out.println("FILE ERROR: " + exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("PARSING ERROR: " + exception.getMessage());
        } catch (Exception others) {
            System.out.println("ERROR: " + others.getMessage());
        }
    }

}
