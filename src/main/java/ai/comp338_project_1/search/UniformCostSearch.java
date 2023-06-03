package ai.comp338_project_1.search;

import java.util.HashMap;
import java.util.PriorityQueue;

public class UniformCostSearch {
    HashMap<Integer, CityNode> cities;

    public UniformCostSearch(HashMap<Integer, CityNode> cities) {
        this.cities = new HashMap<>(cities);
    }

    public CityNode findPath(CityNode source, CityNode destination) {
        PriorityQueue<CityNode> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[cities.size() + 1];

        queue.add(source);
        while (!queue.isEmpty()) {
            CityNode currentNode = queue.poll();
            visited[currentNode.state] = true;

            if (currentNode == destination) {
                return destination;
            }

            for (Edge neighbour : currentNode.getEdges()) {

                if (neighbour.roadDistance != 0 && !visited[neighbour.cityNode.state]) {
                    neighbour.cityNode.cost = currentNode.cost + neighbour.roadDistance;
                    neighbour.cityNode.previous = currentNode;
                    queue.add(neighbour.cityNode);
                }
            }
        }

        return null;
    }
}
