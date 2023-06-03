package ai.comp338_project_1.search;

import java.util.ArrayList;
import java.util.HashMap;

public class AStarSearch {

    private HashMap<Integer, CityNode> cities;

    public AStarSearch(HashMap<Integer, CityNode> cities) {
        this.cities = new HashMap<>(cities);
    }

    public CityNode findPath(CityNode source, CityNode destination) {

        return destination;
    }
}
