package graphic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphNode {
    private int number;
    private Set<GraphNode> connectedNodes = new HashSet<>();
    private ArrayList<GraphNode> neighbors = new ArrayList<>();
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
    //////////////////////////////setter/////////////////////////////////
    public void setNumber(int number) {
        this.number = number;
    }
    public void setConnectedNodes(Set<GraphNode> connectedNodes) {
        this.connectedNodes = connectedNodes;
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
        neighbors.add(node);
    }
    public void checkSpecial(){
        if (connectedNodes.size() > 1){
            switch (neighbors.size()){
                case 2:
                    if ((number != 1 && Math.abs(neighbors.get(0).getNumber() - number) != Math.abs(neighbors.get(1).getNumber() - number)) || (number == 1 && Math.abs(neighbors.get(0).getNumber() - neighbors.get(1).getNumber()) == 2)){

                    }
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    }
}
