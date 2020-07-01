package elements;

import java.util.ArrayList;

public class Union {
    private int number;
    private ArrayList<Node> nodes;
    private boolean visited;
    private Node mainNode;
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
    public void setMainNode(Node mainNode) {
        this.mainNode = mainNode;
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
    public Node getMainNode() {
        return mainNode;
    }
    //////////////////////////////////////////////////////////////////
    public void addNode(Node node){
        nodes.add(node);
    }
    public void updateVoltages (double dv){
        mainNode.updateVoltage(dv);
        for (Node node : nodes){
            node.updateVoltage();
        }
    }
}
