package graphic;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {
    private int number;
    private int nodeNumber = -1;
    private Set<GraphNode> connectedNodes = new HashSet<>();
    private int x;
    private int y;
    ///////////////////////////////constructor//////////////////////////
    public GraphNode(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;
    }
    //////////////////////////////getter////////////////////////////////
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Set<GraphNode> getConnectedNodes() {
        return connectedNodes;
    }
    public int getNumber() {
        return number;
    }
    public int getNodeNumber() {
        return nodeNumber;
    }
    //////////////////////////////setter/////////////////////////////////
    public void setNumber(int number) {
        this.number = number;
    }
    public void setConnectedNodes(Set<GraphNode> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }
    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    //////////////////////////////////////////////////////////////////////
    public void addConnectedNode (GraphNode node){
        connectedNodes.add(node);
    }
}
