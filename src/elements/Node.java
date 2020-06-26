package elements;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    private String name;
    private double voltage;
    private int union = -1;
    double I_p,I_n,V_p,V_n,I,V;
    private Queue<Node> saf = new LinkedList<>();
    ////////////////////////////////constructor////////////////////////
    public Node(String name){
        this.name = name;
        voltage = 0;
        this.I_p=0;
        this.I_n=0;
        this.V_p=0;
        this.V_n=0;
        this.I =0 ;
        this.V = 0;
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

    public Queue<Node> getSaf() {
        return saf;
    }

    public int getUnion() {
        return union;
    }

    /////////////////////////////////setter////////////////////////////
    public void setName(String name) {
        this.name = name;
    }
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
    public void setUnion(int union) {
        this.union = union;
    }
    public void setSaf(Queue<Node> saf) {
        this.saf = saf;
    }
    ///////////////////////////////////////////////////////////////////
    public void updateVoltage (double dv){
        voltage += dv;
    }
}
