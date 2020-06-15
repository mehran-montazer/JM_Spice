package elements;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    private String name;
    private double voltage;
    Queue<Node> saf = new LinkedList<Node>();
    ////////////////////////////////constructor////////////////////////
    Node (String name){
        this.name = name;
        voltage = 0;
    }
    public void adder(Node node){
        saf.add(node);
    }
    /////////////////////////////////getter///////////////////////////
    public String getName() {
        return name;
    }
    public double getVoltage() {
        return voltage;
    }
    /////////////////////////////////setter////////////////////////////
    public void setName(String name) {
        this.name = name;
    }
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
}
