package ai.comp338_project_1.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class UniformCostSearch {
    public static GraphNode findPath(GraphNode source, GraphNode destination) {
        PriorityQueue<GraphNode> openList = new PriorityQueue<>();
        PriorityQueue<GraphNode> closedList = new PriorityQueue<>();

        openList.add(source);
        boolean found = false;

        while (!openList.isEmpty()){
            GraphNode currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode == destination) return currentNode;

            for(Edge edge: currentNode.neighbours){
                GraphNode currentEdgeTarget = edge.target;
                currentEdgeTarget.g_function = currentNode.g_function + edge.cost;

                if(!closedList.contains(currentEdgeTarget) && !openList.contains(currentEdgeTarget)){
                    currentEdgeTarget.parent = currentNode;
                    openList.add(currentEdgeTarget);

                }
                else if((openList.contains(currentEdgeTarget))&&(currentEdgeTarget.g_function>currentNode.g_function+ edge.cost)){
                    currentEdgeTarget.parent=currentNode;
                    currentEdgeTarget.g_function = currentNode.g_function+ edge.cost;
                    openList.remove(currentEdgeTarget);
                    openList.add(currentEdgeTarget);

                }
            }
        }

        return null;
    }
}
