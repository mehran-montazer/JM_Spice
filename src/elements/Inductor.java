package elements;

import graphic.GraphNode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

import java.util.List;

public class Inductor extends Element {
    double inductance;
    double initialCurrent;
    double initialCurrentplus;
    double initialCurrentminus;
    //////////////////////////////////////constructor//////////////////////////////
    public Inductor(String name, Node positiveTerminal, Node negativeTerminal, double inductance) {
        super(name, positiveTerminal, negativeTerminal, inductance, 'l');
        this.inductance = inductance;
        this.initialCurrent = 0;
        this.initialCurrentplus = 0;
        this.initialCurrentminus = 0;
    }
    /////////////////////////////////getter////////////////////////////
    public double getInductance() {
        return inductance;
    }
    public double getInitialCurrent() {
        return initialCurrent;
    }
    /////////////////////////////////setter////////////////////////////
    public void setInductance(double inductance) {
        this.inductance = inductance;
    }
    public void setInitialCurrent(double initialCurrent) {
        this.initialCurrent = initialCurrent;
    }
    public void setstepcurrent(double cuurent){this.initialCurrent=  cuurent;}
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
            Line line1 = new Line(x, y, x, y + 30);
            Line line2 = new Line(x, y + 70, x, y + 100);
            Arc arc1 = new Arc(x, y + 36.5, 4, 6.5, 90, 180);
            arc1.setFill(Color.TRANSPARENT);
            arc1.setStroke(Color.BLACK);
            Arc arc2 = new Arc(x, y + 40.5, 6, 2.5, 270, 180);
            arc2.setFill(Color.TRANSPARENT);
            arc2.setStroke(Color.BLACK);
            Arc arc3 = new Arc(x, y + 47.5, 4, 9.5, 90, 180);
            arc3.setFill(Color.TRANSPARENT);
            arc3.setStroke(Color.BLACK);
            Arc arc4 = new Arc(x, y + 54.5, 6, 2.5, 270, 180);
            arc4.setFill(Color.TRANSPARENT);
            arc4.setStroke(Color.BLACK);
            Arc arc5 = new Arc(x, y + 61, 4, 9, 90, 180);
            arc5.setFill(Color.TRANSPARENT);
            arc5.setStroke(Color.BLACK);
            pane.getChildren().addAll(line1,line2,arc1,arc2,arc3,arc4,arc5);
        }
        else {
            y = positiveNode.getY();
            if (positiveNode.getX() < negativeNode.getX())
                x = positiveNode.getX();
            else
                x = negativeNode.getX();
            Line line1 = new Line(x, y, x + 30, y);
            Line line2 = new Line(x + 70, y, x + 100, y);
            Arc arc1 = new Arc(x + 36.5, y, 6.5, 4, 0, 180);
            arc1.setFill(Color.TRANSPARENT);
            arc1.setStroke(Color.BLACK);
            Arc arc2 = new Arc(x + 40.5, y, 2.5, 6, 180, 180);
            arc2.setFill(Color.TRANSPARENT);
            arc2.setStroke(Color.BLACK);
            Arc arc3 = new Arc(x + 47.5, y, 9.5, 4, 0, 180);
            arc3.setFill(Color.TRANSPARENT);
            arc3.setStroke(Color.BLACK);
            Arc arc4 = new Arc(x + 54.5, y, 2.5, 6, 180, 180);
            arc4.setFill(Color.TRANSPARENT);
            arc4.setStroke(Color.BLACK);
            Arc arc5 = new Arc(x + 61, y, 9, 4, 0, 180);
            arc5.setFill(Color.TRANSPARENT);
            arc5.setStroke(Color.BLACK);
            pane.getChildren().addAll(line1,line2,arc1,arc2,arc3,arc4,arc5);
        }
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
        this.I_n = initialCurrent + ((this.positiveTerminal.V   - this.negativeTerminal.V ) * dt) / this.value;
        return I_n;
    }
    public double calculateCurrentLplus() {
        this.I_p = initialCurrent + ((this.positiveTerminal.V   - this.negativeTerminal.V  + dv) * dt) / this.value;
        initialCurrentplus = this.I_p;
        return I_p;
    }
    public double calculateCurrentLminus() {
        this.I_p = initialCurrent + ((this.positiveTerminal.V  - this.negativeTerminal.V  - dv) * dt) / this.value;
        initialCurrentminus = this.I_p;
        return I_p;
    }

}
