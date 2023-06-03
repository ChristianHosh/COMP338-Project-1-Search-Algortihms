package ai.comp338_project_1.search;

import java.util.ArrayList;

public class CityNode implements Comparable<CityNode> {
    private final String name;
    private final double x_coordinate;
    private final double y_coordinate;
    private final ArrayList<Edge> neighbours = new ArrayList<>();

    public int state;
    public double cost;
    public CityNode previous;


    public CityNode(String name, double x_coordinate, double y_coordinate, int state) {
        this.name = name.strip();
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;

        this.state = state;
        this.cost = 0;
        this.previous = null;
    }

    public double[] getCoordinates() {
        return new double[]{x_coordinate, y_coordinate};
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<Edge> getEdges() {
        return this.neighbours;
    }

    @Override
    public int compareTo(CityNode other) {
        return Double.compare(this.cost, other.cost);
    }

    public void addEdgeToVertex(CityNode destination, double roadDistance) {
        double airDistance = Math.sqrt(Math.pow(destination.getX_coordinate() - this.x_coordinate, 2) +
                (Math.pow(destination.getX_coordinate() - this.getY_coordinate(), 2)));
        Edge newEdge = new Edge(destination, roadDistance, airDistance * 111);
        neighbours.add(newEdge);
    }

}
