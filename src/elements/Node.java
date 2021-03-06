package elements;

import handmadeExceptions.Minus2Exception;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Node implements Comparable{
    private String name;
    private double voltage;
    private int union;
    private int nameNumber;
    double I_p,I_n,V_p,V_n,I,V,V_Step;
    private ArrayList <VoltageSource> voltageSources = new ArrayList<>();
    private ArrayList <Element> elements = new ArrayList<>();
    private Queue<Node> saf = new LinkedList<>();
    private ArrayList<Node> neighbors = new ArrayList<>();
    private boolean hasVoltageSource = false;
    private Node parentNode;
    private ArrayList<Node> children = new ArrayList<>();
    private VoltageSource connector;
    private boolean isConnectorNormal;
    private boolean isDependent;
    private boolean isReval = false;
    public ArrayList<moshakhassat> moshakhassats  = new ArrayList<moshakhassat>();
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
        this.V_Step=0;
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
    public double getVoltage_Step() {
        return V_Step;
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
    public double getV() {
        return V;
    }
    public ArrayList<Element> getElements() {
        return elements;
    }
    public ArrayList<Node> getChildren() {
        return children;
    }
    public boolean isReval() {
        return isReval;
    }
    public ArrayList<Node> getNeighbors() {
        return neighbors;
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
    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }
    public void setReval(boolean reval) {
        isReval = reval;
    }
    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
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
    public void addChild (Node node){
        children.add(node);
    }
    public void updateVoltage(double t){
        if (connector.isAC()) {
            connector.calculateVoltage(t);
        }
        else {
            connector.calculateVoltage();
        }
        if (isConnectorNormal) {
            V = parentNode.getV() + connector.getVoltage();
        }
        else {
            V = parentNode.getV() - connector.getVoltage();
        }
    }
    public void addElement (Element element){
        elements.add(element);
    }
    public void checkForMinus2Exception(double t) throws Minus2Exception {
        boolean isKCLQuestionable = true;
        for (Element element : elements){
            if (element.type != 'i'){
                isKCLQuestionable = false;
                break;
            }
        }
        if (isKCLQuestionable){
            double sum = 0;
            for (Element element : elements){
                CurrentSource currentSource = (CurrentSource)element;
                if (currentSource.isAC()) {
                    currentSource.calculateCurrent(t);
                }
                else {
                    currentSource.calculateCurrent();
                }
                if (currentSource.getPositiveTerminal().getNameNumber() == nameNumber) {
                    sum += currentSource.getCurrent();
                }
                else if (currentSource.getNegativeTerminal().getNameNumber() == nameNumber) {
                    sum -= currentSource.getCurrent();
                }
            }
            if (sum != 0) {
                throw new Minus2Exception();
            }
        }
    }
    public void addNeighbor(Node node){
        neighbors.add(node);
    }
    @Override
    public int compareTo(Object obj) {
        Node compareNode = (Node) obj;
        return this.nameNumber - compareNode.getNameNumber();
    }
}
