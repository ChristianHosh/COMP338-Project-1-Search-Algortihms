package ai.comp338_project_1.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GraphNode implements Comparable<GraphNode> {
    public final String city;
    public final double  xLocation;
    public final double yLocation;
    public final double latitude;
    public final double longitude;
    public final ArrayList<Edge> neighbours;

    public double f_function = Double.MAX_VALUE;
    public double g_function = Double.MAX_VALUE;
    public double totalCost = 0;
    public GraphNode parent;

    public GraphNode(String city, double xLocation, double yLocation, double latitude, double longitude) {
        this.city = city;
        this.xLocation = xLocation;
        this.yLocation = yLocation;

        this.latitude = latitude;
        this.longitude = longitude;

        this.neighbours = new ArrayList<>();

    }

    public double h_function(GraphNode target){
        return Math.abs(this.latitude - target.latitude) + Math.abs(this.longitude - target.longitude);
    }

    public String toString() {
        return city;
    }

    public void addBranch(double weight, GraphNode node){
        Edge newEdge = new Edge(node, weight);
        neighbours.add(newEdge);
    }

    public double[] getCoordinates() {
        return new double[]{xLocation, yLocation};
    }

    @Override
    public int compareTo(@NotNull GraphNode other) {
        return Double.compare(this.f_function, other.f_function);
    }
}
