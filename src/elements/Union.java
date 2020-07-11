package elements;

import handmadeExceptions.Minus2Exception;
import handmadeExceptions.Minus3Exception;
import handmadeExceptions.Minus4Exception;

import java.util.ArrayList;

public class Union {
    private int number;
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<VoltageSource> voltageSources = new ArrayList<>();
    private boolean visited;
    private Node mainNode;
    private double I_p;
    private double I_n;
    /////////////////////////////////constructor///////////////////////////
    public Union(int number){
        this.number = number;
        I_p = 0;
        I_n = 0;
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
    public void setI_n(double i_n) {
        I_n = i_n;
    }
    public void setI_p(double i_p) {
        I_p = i_p;
    }
    public void setVoltageSources(ArrayList<VoltageSource> voltageSources) {
        this.voltageSources = voltageSources;
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
    public double getI_n() {
        return I_n;
    }
    public double getI_p() {
        return I_p;
    }
    public ArrayList<VoltageSource> getVoltageSources() {
        return voltageSources;
    }
    //////////////////////////////////////////////////////////////////
    public void addNode(Node node){
        nodes.add(node);
    }
    public void addVoltageSource (VoltageSource voltageSource){
        voltageSources.add(voltageSource);
    }
    public void updateVoltages (double dv, double t){
        mainNode.updateVoltageMain(dv);
        for (Node node : nodes){
            node.updateVoltage(t);
        }
    }
    public void updateVoltages (double t){
        for (Node node : nodes){
            node.updateVoltage(t);
        }
    }
    public void checkKCL(double t) throws Minus2Exception {
        mainNode.checkForMinus2Exception(t);
    }
    public void checkKVL(double t) throws Minus3Exception, Minus4Exception{
        boolean isKVLObjected = false;
        for (VoltageSource voltageSource : voltageSources){
            if (!voltageSource.isSetAsConnector()){
                Node positiveTerminal = voltageSource.getPositiveTerminal();
                Node negativeTerminal = voltageSource.getNegativeTerminal();
                updateVoltages(0,t);
                if (positiveTerminal.getV() - negativeTerminal.getV() != voltageSource.getVoltage())
                    isKVLObjected = true;
            }
        }
        if (isKVLObjected){
            if (number == 0)
                throw new Minus4Exception();
            else
                throw new Minus3Exception();
        }
    }
}
