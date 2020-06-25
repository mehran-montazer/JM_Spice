package elements;

import java.util.ArrayList;

public class Union {
    private int number;
    private ArrayList<Node> nodes;
    private boolean visited;
    /////////////////////////////////constructor///////////////////////////
    public Union(int number){
        this.number = number;
    }
    /////////////////////////////////setter///////////////////////////
    public void setNumber(int number) {
        this.number = number;
    }
    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    /////////////////////////////////getter///////////////////////////
    public boolean isVisited() {
        return visited;
    }
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public int getNumber() {
        return number;
    }
    /////////////////////////////////getter///////////////////////////
    public void addNode(Node node){
        nodes.add(node);
    }
}
