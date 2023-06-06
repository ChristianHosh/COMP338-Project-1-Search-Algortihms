package ai.comp338_project_1.search;

import java.util.PriorityQueue;

public class AStarSearch {
    public static GraphNode findPath(GraphNode source, GraphNode destination) {
        PriorityQueue<GraphNode> closedList = new PriorityQueue<>();
        PriorityQueue<GraphNode> openList = new PriorityQueue<>();

        source.f_function = source.g_function + source.h_function(destination);
        openList.add(source);

        while (!openList.isEmpty()) {
            GraphNode currentNode = openList.peek();

            openList.remove(currentNode);
            closedList.add(currentNode);

            for (Edge edge : currentNode.neighbours) {
                GraphNode currentEdgeTarget = edge.target;
                double newWeight = currentNode.g_function + edge.cost;

                if (!openList.contains(currentEdgeTarget) && !closedList.contains(currentEdgeTarget)) {
                    currentEdgeTarget.parent = currentNode;
                    currentEdgeTarget.g_function = newWeight;
                    currentEdgeTarget.f_function = currentEdgeTarget.g_function + currentEdgeTarget.h_function(destination);
                    currentEdgeTarget.totalCost = edge.cost + currentNode.totalCost;
                    openList.add(currentEdgeTarget);
                } else {
                    if (newWeight < currentEdgeTarget.g_function) {
                        currentEdgeTarget.parent = currentNode;
                        currentEdgeTarget.g_function = newWeight;
                        currentEdgeTarget.totalCost = edge.cost + currentNode.totalCost;
                        currentEdgeTarget.f_function = currentEdgeTarget.g_function + currentEdgeTarget.h_function(destination);

                        if (closedList.contains(currentEdgeTarget)) {
                            closedList.remove(currentEdgeTarget);
                            openList.add(currentEdgeTarget);
                        }
                    }
                }
            }
        }
        return destination;
    }


}
