package elements;

import graphic.GraphNode;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class CurrentSource extends Element {
    private double IoffSet = 0;
    private double Iamp = 0;
    private double frequency = 0;
    private double phase = 0;
    private Node positiveDependent = null;
    private Node negativeDependent = null;
    private double gain = 0;
    private Element dependentCurrentElement = null;
    private boolean isAC;
    /////////////////////////////////constructor///////////////////////////
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, Node positiveDependent, Node negativeDependent, double gain){
        super(name, positiveTerminal, negativeTerminal,0,'i');
        this.positiveDependent = positiveDependent;
        this.negativeDependent = negativeDependent;
        this.gain = gain;
        isAC = false;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, Element dependentCurrentElement, double gain){
        super(name, positiveTerminal, negativeTerminal,0,'i');
        this.dependentCurrentElement = dependentCurrentElement;
        this.gain = gain;
        isAC = false;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double IoffSet, double Iamp, double frequency, double phase){
        super(name, positiveTerminal, negativeTerminal,0, 'i');
        this.IoffSet = IoffSet;
        this.frequency = frequency;
        this.Iamp = Iamp;
        this.phase = phase;
        isAC = true;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double current) {
        super(name, positiveTerminal, negativeTerminal, current, 'i');
        this.current = current;
    }
    /////////////////////////////////getter///////////////////////////
    public double getPhase() {
        return phase;
    }
    public double getFrequency() {
        return frequency;
    }
    public double getIamp() {
        return Iamp;
    }
    public double getIoffSet() {
        return IoffSet;
    }
    public double getGain() {
        return gain;
    }
    public Node getPositiveDependent() {
        return positiveDependent;
    }
    public Node getNegativeDependent() {
        return negativeDependent;
    }
    public Element getDependentCurrentElement() {
        return dependentCurrentElement;
    }
    public boolean isAC() {
        return isAC;
    }
    /////////////////////////////////setter///////////////////////////
    public void setIamp(double iamp) {
        Iamp = iamp;
    }
    public void setIoffSet(double ioffSet) {
        IoffSet = ioffSet;
    }
    public void setPhase(double phase) {
        this.phase = phase;
    }
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    public void setDependentCurrentElement(Element dependentCurrentElement) {
        this.dependentCurrentElement = dependentCurrentElement;
    }
    public void setGain(double gain) {
        this.gain = gain;
    }
    public void setNegativeDependent(Node negativeDependent) {
        this.negativeDependent = negativeDependent;
    }
    public void setPositiveDependent(Node positiveDependent) {
        this.positiveDependent = positiveDependent;
    }
    public void setAC(boolean AC) {
        isAC = AC;
    }
    //////////////////////////////////////////////////////////////////
    @Override
    public void draw(Pane pane, GraphNode positiveNode, GraphNode negativeNode){
        boolean isVertical = checkVertical(positiveNode, negativeNode);
        boolean isDependent = checkDependant();
        int x;
        int y;
        if (isVertical){
            x = positiveNode.getX();
            if (positiveNode.getY() < negativeNode.getY())
                y = positiveNode.getY();
            else
                y = negativeNode.getY();
            if (!isDependent){
                Line line1 = new Line(x, y, x, y + 35);
                Line line2 = new Line(x, y + 65, x, y + 100);
                Line line3 = new Line(x, y + 42.5, x, y + 57.5);
                Line line4;
                Line line5;
                Arc arc = new Arc(x, y + 50, 15, 15, 0, 360);
                arc.setFill(Color.TRANSPARENT);
                arc.setStroke(Color.BLACK);
                if (positiveNode.getY() > negativeNode.getY()) {
                    line5 = new Line(x, y + 57.5, x - 5, y + 52.5);
                    line4 = new Line(x, y + 57.5, x + 5, y + 52.5);
                }
                else {
                    line5 = new Line(x, y + 42.5, x - 5, y + 47.5);
                    line4 = new Line(x, y + 42.5, x + 5, y + 47.5);
                }
                pane.getChildren().addAll(line1,line2,arc,line3,line4,line5);
            }
            else {
                Line line1 = new Line(x, y, x, y + 35);
                Line line2 = new Line(x, y + 65, x, y + 100);
                Line line3 = new Line(x, y + 42.5, x, y + 57.5);
                Line line4;
                Line line5;
                if (positiveNode.getY() > negativeNode.getY()) {
                    line5 = new Line(x, y + 57.5, x - 5, y + 52.5);
                    line4 = new Line(x, y + 57.5, x + 5, y + 52.5);
                }
                else {
                    line5 = new Line(x, y + 42.5, x - 5, y + 47.5);
                    line4 = new Line(x, y + 42.5, x + 5, y + 47.5);
                }
                Line line6 = new Line(x, y + 35, x - 15, y + 50);
                Line line7 = new Line(x, y + 35, x + 15, y + 50);
                Line line8 = new Line(x - 15, y + 50, x, y + 65);
                Line line9 = new Line(x + 15, y + 50, x, y + 65);
                Label label = new Label(Double.toString(gain) + " * ");
                if (dependentCurrentElement != null)
                    label.setText(label.getText() + "I" + dependentCurrentElement.getName());
                else
                    label.setText(label.getText() + "(V" + positiveDependent.getName() + " - " + "V" + negativeDependent.getName() + ")");
                label.setRotate(270);
                label.setFont(new Font(9));
                label.setLayoutX(x - 50);
                label.setLayoutY(y + 50);
                pane.getChildren().addAll(line1,line2,line3,line4,line5,line6,line7,line8,line9,label);
            }
        }
        else {
            y = positiveNode.getY();
            if (positiveNode.getX() < negativeNode.getX())
                x = positiveNode.getX();
            else
                x = negativeNode.getX();
            if (!isDependent){
                Line line1 = new Line(x, y, x + 35, y);
                Line line2 = new Line(x + 65, y, x + 100, y);
                Line line3 = new Line(x + 42.5, y, x + 57.5, y);
                Line line4;
                Line line5;
                Arc arc = new Arc(x + 50, y, 15, 15, 0, 360);
                arc.setFill(Color.TRANSPARENT);
                arc.setStroke(Color.BLACK);
                if (positiveNode.getX() > negativeNode.getX()) {
                    line5 = new Line(x + 57.5, y, x + 52.5, y - 5);
                    line4 = new Line(x + 57.5, y, x + 52.5, y + 5);
                }
                else {
                    line5 = new Line(x + 42.5, y, x + 47.5, y - 5);
                    line4 = new Line(x + 42.5, y, x + 47.5, y + 5);
                }
                pane.getChildren().addAll(line1,line2,arc,line3,line4,line5);
            }
            else {
                Line line1 = new Line(x, y, x + 35, y);
                Line line2 = new Line(x + 65, y, x + 100, y);
                Line line3 = new Line(x + 42.5, y, x + 57.5, y);
                Line line4;
                Line line5;
                if (positiveNode.getX() > negativeNode.getX()) {
                    line5 = new Line(x + 57.5, y, x + 52.5, y - 5);
                    line4 = new Line(x + 57.5, y, x + 52.5, y + 5);
                }
                else {
                    line5 = new Line(x + 42.5, y, x + 47.5, y - 5);
                    line4 = new Line(x + 42.5, y, x + 47.5, y + 5);
                }
                Line line6 = new Line(x + 35, y, x + 50, y - 15);
                Line line7 = new Line(x + 35, y, x + 50, y + 15);
                Line line8 = new Line(x + 50, y - 15, x + 65, y);
                Line line9 = new Line(x + 50, y + 15, x + 65, y);
                Label label = new Label(Double.toString(gain) + " * ");
                if (dependentCurrentElement != null)
                    label.setText(label.getText() + "I" + dependentCurrentElement.getName());
                else
                    label.setText(label.getText() + "(V" + positiveDependent.getName() + " - " + "V" + negativeDependent.getName() + ")");
                label.setFont(new Font(9));
                label.setLayoutX(x + 20);
                label.setLayoutY(y - 30);
                pane.getChildren().addAll(line1,line2,line3,line4,line5,line6,line7,line8,line9,label);
            }
        }
    }
    @Override
    public void calculateVoltage() {

    }

    @Override
    public double calculateCurrentR() {
        return 0;
    }

    @Override
    public double calculateCurrentRplus() {
        return 0;
    }

    @Override
    public double calculateCurrentRminus() {
        return 0;
    }

    @Override
    public double calculateCurrentC() {
        return 0;
    }

    @Override
    public double calculateCurrentCplus() {
        return 0;
    }

    @Override
    public double calculateCurrentCminus() {
        return 0;
    }

    @Override
    public double calculateCurrentL() {
        return 0;
    }

    @Override
    public double calculateCurrentLminus() {
        return 0;
    }

    @Override
    public double calculateCurrentLplus() {
        return 0;
    }

    @Override
    public void calculateCurrent() {
        if (!(dependentCurrentElement == null)){
            current = gain * dependentCurrentElement.getCurrent();
        }
        else if (!(negativeDependent == null || positiveDependent == null)){
            current = gain * (positiveDependent.getV() - negativeDependent.getV());
        }
    }

    public void calculateCurrent(double time){
        current = IoffSet + Iamp * Math.cos(2 * Math.PI * frequency * time + phase);
    }
    private boolean checkDependant(){
        if ( (negativeDependent != null && positiveDependent != null) || dependentCurrentElement != null)
            return true;
        else
            return false;
    }
}
