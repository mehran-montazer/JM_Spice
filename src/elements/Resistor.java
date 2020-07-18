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

        }
        else {
            y = positiveNode.getY();
            if (positiveNode.getX() < negativeNode.getX())
                x = positiveNode.getX();
            else
                x = negativeNode.getX();
            Line line1 = new Line(x, y, x + 55, y);
            Line line2 = new Line(x + 55, y, x + 65, y - 10);
            Line line3 = new Line(x + 65, y - 10, x + 75, y + 10);
            Line line4 = new Line(x + 75, y + 10, x + 85, y - 10);
            Line line5 = new Line(x + 85, y - 10, x + 95, y + 10);
            Line line6 = new Line(x + 95, y + 10, x + 105, y - 10);
            Line line7 = new Line(x + 105, y - 10, x + 115, y + 10);
            Line line8 = new Line(x + 115, y + 10, x + 125, y - 10);
            Line line9 = new Line(x + 125, y - 10, x + 135, y + 10);
            Line line10 = new Line(x + 135,y + 10, x + 145, y);
            Line line11 = new Line(x + 145, y, x + 200, y);
            pane.getChildren().addAll(line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11);
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
