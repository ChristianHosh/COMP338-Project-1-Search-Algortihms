package ai.comp338_project_1.search;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class csvReader {
    private static final File CITIES_FILE = new File("./search/files/Cities.csv");
    private static final File ROADS_FILE = new File("./search/files/Roads.csv");
    private static HashMap<Integer, CityNode> CITY_NODES;

    @NotNull
    public static HashMap<Integer, CityNode> getCities() {
        try {
            Scanner scanner = new Scanner(CITIES_FILE);
            HashMap<Integer, CityNode> cityNodes = new HashMap<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] inline = line.split(",");

                int city_id = Integer.parseInt(inline[0].strip());
                String city_name = inline[1].strip();
                double city_latitude = Double.parseDouble(inline[2].strip());
                double city_longitude = Double.parseDouble(inline[3].strip());

                CityNode city = new CityNode(city_name, city_latitude, city_longitude, city_id);
                cityNodes.put(city_id, city);
            }
            CITY_NODES = cityNodes;
            getEdges();
            return CITY_NODES;

        } catch (FileNotFoundException exception) {
            System.out.println("FILE ERROR: " + exception.getMessage());
            return new HashMap<>();
        } catch (NumberFormatException exception) {
            System.out.println("PARSING ERROR: " + exception.getMessage());
            return new HashMap<>();
        } catch (Exception others) {
            System.out.println("ERROR: " + others.getMessage());
            return new HashMap<>();
        }
    }

    private static void getEdges() {
        try {
            Scanner roadsScanner = new Scanner(ROADS_FILE);
            while (roadsScanner.hasNext()) {
                String line = roadsScanner.nextLine();
                String[] lineSplit = line.split(",");
                CityNode currentCity = CITY_NODES.get(Integer.parseInt(lineSplit[0].strip()));

                if (currentCity == null)
                    continue;
                int i = 1;
                while (i < lineSplit.length - 1) {
                    i++;
                    CityNode currentBorder = CITY_NODES.get(Integer.parseInt(lineSplit[i].split(":")[0].strip()));
                    if (currentBorder == null)
                        continue;
                    currentCity.addEdgeToVertex(currentBorder, Double.parseDouble(lineSplit[i].split(":")[1].strip()));
                }
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
