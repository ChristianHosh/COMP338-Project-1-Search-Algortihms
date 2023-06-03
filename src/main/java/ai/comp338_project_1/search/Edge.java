package ai.comp338_project_1.search;

public class Edge {
    CityNode cityNode;
    double roadDistance;
    double airDistance;


    public Edge(CityNode cityNode, double roadDistance, double airDistance) {
        this.cityNode = cityNode;
        this.roadDistance = roadDistance;
        this.airDistance = airDistance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "cityNode=" + cityNode +
                ", roadDistance=" + roadDistance +
                ", airDistance=" + airDistance +
                '}';
    }
}
