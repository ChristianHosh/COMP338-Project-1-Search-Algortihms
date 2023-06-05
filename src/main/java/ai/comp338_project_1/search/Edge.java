package ai.comp338_project_1.search;

public class Edge {
    public final double cost;
    public final GraphNode target;

    public Edge(GraphNode targetNode, double costVal){
        cost = costVal;
        target = targetNode;

    }

    @Override
    public String toString() {
        return "Edge{" +
                "cost=" + cost +
                ", target=" + target +
                '}';
    }
}
