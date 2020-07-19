package elements;

import graphic.GraphNode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Resistor extends Element {
    private double resistance;
    //////////////////////////////////////constructor//////////////////////////////
    public Resistor(String name, Node positiveTerminal, Node negativeTerminal, double resistance) {
        super(name, positiveTerminal, negativeTerminal, resistance, 'r');
        this.resistance = resistance;
    }
    /////////////////////////////////getter////////////////////////////
    public double getResistance() {
        return resistance;
    }
    /////////////////////////////////setter////////////////////////////
    public void setResistance(double resistance) {
        this.resistance = resistance;
    }
    ///////////////////////////////////////////////////////////////////
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
            Line line1 = new Line(x, y, x, y + 32);
            Line line2 = new Line(x, y + 32, x - 3, y + 35);
            Line line3 = new Line(x - 3, y + 35, x + 3, y + 41);
            Line line4 = new Line(x + 3, y + 41, x - 3, y + 47);
            Line line5 = new Line(x - 3, y + 47, x + 3, y + 53);
            Line line6 = new Line(x + 3, y + 55, x - 3, y + 59);
            Line line7 = new Line(x - 3, y + 59, x + 3, y + 65);
/*            Line line8 = new Line(x + 75, y + 5, x + 125, y - 5);
            Line line9 = new Line(x + 125, y - 5, x + 135, y + 5);*/
            Line line10 = new Line(x + 3,y + 65, x, y + 68);
            Line line11 = new Line(x, y + 68, x, y + 100);
            pane.getChildren().addAll(line1,line2,line3,line4,line5,line6,line7,line10,line11);
        }
        else {
            y = positiveNode.getY();
            if (positiveNode.getX() < negativeNode.getX())
                x = positiveNode.getX();
            else
                x = negativeNode.getX();
            Line line1 = new Line(x, y, x + 32, y);
            Line line2 = new Line(x + 32, y, x + 35, y - 3);
            Line line3 = new Line(x + 35, y - 3, x + 41, y + 3);
            Line line4 = new Line(x + 41, y + 3, x + 47, y - 3);
            Line line5 = new Line(x + 47, y - 3, x + 53, y + 3);
            Line line6 = new Line(x + 53, y + 3, x + 59, y - 3);
            Line line7 = new Line(x + 59, y - 3, x + 65, y + 3);
/*            Line line8 = new Line(x + 75, y + 5, x + 125, y - 5);
            Line line9 = new Line(x + 125, y - 5, x + 135, y + 5);*/
            Line line10 = new Line(x + 65,y + 3, x + 68, y);
            Line line11 = new Line(x + 68, y, x + 100, y);
            pane.getChildren().addAll(line1,line2,line3,line4,line5,line6,line7,line10,line11);
        }
    }
    @Override
    public void calculateVoltage() {
        voltage = positiveTerminal.getVoltage() - negativeTerminal.getVoltage();
    }

    @Override
    public void calculateCurrent() {

    }

    @Override
    public double calculateCurrentR() {
        current = (this.positiveTerminal.V-this.negativeTerminal.V) / resistance;
        return current;
    }
    public double calculateCurrentRplus() {
        current = (this.positiveTerminal.V-this.negativeTerminal.V+dv) / resistance;
        return current;
    }
    public double calculateCurrentRminus() {
        current = (this.positiveTerminal.V-this.negativeTerminal.V-dv) / resistance;
        return current;
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

}
