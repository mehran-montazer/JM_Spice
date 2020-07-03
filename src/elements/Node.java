package elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Node implements Comparable{
    private String name;
    private double voltage;
    private int union;
    private int nameNumber;
    double I_p,I_n,V_p,V_n,I,V;
    private ArrayList <VoltageSource> voltageSources = new ArrayList<>();
    private Queue<Node> saf = new LinkedList<>();
    private boolean hasVoltageSource = false;
    private Node parentNode;
    private VoltageSource connector;
    private boolean isConnectorNormal;
    private boolean isDependent;
    ////////////////////////////////constructor////////////////////////
    public Node(String name){
        this.name = name;
        this.union = Integer.parseInt(name);
        nameNumber = union;
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
    public ArrayList<VoltageSource> getVoltageSources() {
        return voltageSources;
    }
    public boolean hasVoltageSource() {
        return hasVoltageSource;
    }
    public Node getParentNode() {
        return parentNode;
    }
    public int getNameNumber() {
        return nameNumber;
    }
    public VoltageSource getConnector() {
        return connector;
    }
    public boolean isConnectorNormal() {
        return isConnectorNormal;
    }
    public boolean isDependent() {
        return isDependent;
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
    public void setVoltageSources(ArrayList<VoltageSource> voltageSources) {
        this.voltageSources = voltageSources;
    }
    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    public void setNameNumber(int nameNumber) {
        this.nameNumber = nameNumber;
    }
    public void setConnector(VoltageSource connector) {
        this.connector = connector;
    }
    public void setConnectorNormal(boolean connectorNormal) {
        isConnectorNormal = connectorNormal;
    }
    public void setDependent(boolean dependent) {
        isDependent = dependent;
    }
    ///////////////////////////////////////////////////////////////////
    public void updateDependency(){
        isDependent = union != nameNumber;
    }
    public void updateVoltageMain (double dv){
        voltage += dv;
    }
    public void addVoltageSource (VoltageSource voltageSource){
        voltageSources.add(voltageSource);
        hasVoltageSource = true;
    }
    public void updateVoltage(){
        //if (connector.isAC()) {
         //   connector.calculateVoltage(t);
        //}
        //else {
            connector.calculateVoltage();
        //}
        if (isConnectorNormal) {
            V = parentNode.getVoltage() + connector.getVoltage();
        }
        else {
            V = parentNode.getVoltage() - connector.getVoltage();
        }
    }

    @Override
    public int compareTo(Object obj) {
        Node compareNode = (Node) obj;
        return this.nameNumber - compareNode.getNameNumber();
    }
}
