package ai.comp338_project_1.search;

import java.util.PriorityQueue;
import java.util.Stack;

public class DepthFirstSearch {
    public static GraphNode findPath(GraphNode source, GraphNode destination) {
        Stack<GraphNode> openList = new Stack<>();
        PriorityQueue<GraphNode> closedList = new PriorityQueue<>();

        openList.add(source);

        while (!openList.isEmpty()){
            GraphNode currentNode = openList.pop();
            closedList.add(currentNode);

            if (currentNode == destination) return currentNode;

            for(Edge edge: currentNode.neighbours){
                GraphNode currentEdgeTarget = edge.target;
                if (!openList.contains(currentEdgeTarget) && !closedList.contains(currentEdgeTarget)){
                    currentEdgeTarget.parent = currentNode;
                    openList.push(currentEdgeTarget);
                }
            }
        }

        return null;
    }
}
