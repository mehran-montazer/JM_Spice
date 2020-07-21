package elements;

import graphic.GraphNode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Capacitor extends Element{
    private double initialVoltage;
    private double capacity;
    //////////////////////////////////////constructor//////////////////////////////
    public Capacitor(String name, Node positiveTerminal, Node negativeTerminal, double capacity){
        super(name, positiveTerminal, negativeTerminal, capacity, 'c');
        this.capacity = capacity;
        this.initialVoltage = 0;
    }
    /////////////////////////////////getter////////////////////////////
    public double getCapacity() {
        return capacity;
    }
    public double getInitialVoltage() {
        return initialVoltage;
    }
    /////////////////////////////////setter////////////////////////////
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    public void setInitialVoltage(double initialVoltage) {
        this.initialVoltage = initialVoltage;
    }
    @Override
    public void draw(Pane pane, GraphNode positiveNode, GraphNode negativeNode){
        boolean isVertical = checkVertical(positiveNode, negativeNode);
        int x;
        int y;
        if (isVertical){
            x = positiveNode.getX();
            if (positiveNode.getY() < negativeNode.getY())
                y = positiveNode.getY();
            else
                y = negativeNode.getY();
            startX = x;
            endX = x;
            startY = y;
            endY = y + 100;
            Line line1 = new Line(x, y + 48, x, y);
            Line line2 = new Line(x, y + 52, x, y + 100);
            Line line3 = new Line(x - 6, y + 48, x + 6, y + 48);
            Line line4 = new Line(x  - 6, y + 52, x + 6, y + 52);
            pane.getChildren().addAll(line1,line2,line3,line4);
        }
        else {
            y = positiveNode.getY();
            if (positiveNode.getX() < negativeNode.getX())
                x = positiveNode.getX();
            else
                x = negativeNode.getX();
            startX = x;
            endX = x + 100;
            startY = y;
            endY = y;
            Line line1 = new Line(x, y, x + 48, y);
            Line line2 = new Line(x + 52, y, x + 100, y);
            Line line3 = new Line(x + 48, y - 6, x + 48, y + 6);
            Line line4 = new Line(x + 52, y - 6, x + 52, y + 6);
            pane.getChildren().addAll(line1,line2,line3,line4);
        }
        isDrawn = true;
    }
    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

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
        current = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_Step - this.negativeTerminal.V + this.negativeTerminal.V_Step)) / dt;
        return current;
    }
    public double calculateCurrentCplus() {
        this.I_p = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_Step - this.negativeTerminal.V + this.negativeTerminal.V_Step + dv)) / dt;
        return I_p;
    }
    public double calculateCurrentCminus() {
        this.I_p = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_Step - this.negativeTerminal.V + this.negativeTerminal.V_Step - dv)) / dt;
        return I_p;
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
}
