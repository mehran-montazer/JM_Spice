
package elements;

import graphic.GraphNode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Diode extends Element {
    boolean isON;
    public Diode(String name, Node positiveTerminal, Node negativeTerminal) {
        super(name, positiveTerminal, negativeTerminal,0,'d');
    }
    /////////////////////////////////getter///////////////////////////
    public boolean isON() {
        return isON;
    }
    /////////////////////////////////setter///////////////////////////
    public void setON(boolean ON) {
        isON = ON;
    }
    ///////////////////////////////////////////////////////////////////
    public void checkState(){
        if (positiveTerminal.getV() > negativeTerminal.getV())
            isON = true;
        else if (positiveTerminal.getV() < negativeTerminal.getV())
            isON = false;
    }
    @Override
    public void draw(Pane pane, GraphNode positiveNode, GraphNode negativeNode){
        boolean isVertical = checkVertical(positiveNode, negativeNode);
        isDrawn = true;
        int x;
        int y;
        Line line1;
        Line line2;
        Line line3;
        Line line4;
        Line line5;
        Line line6;
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
            line1 = new Line(x, y, x, y + 44);
            line2 = new Line(x, y + 56, x, y + 100);
            line3 = new Line(x - 7, y + 44, x + 7, y + 44);
            line4 = new Line(x - 7, y + 56, x + 7, y + 56);
            if (positiveNode.getY() < negativeNode.getY()) {
                line5 = new Line(x - 7, y + 56, x, y + 44);
                line6 = new Line(x + 7, y + 56, x, y + 44);
            }
            else {
                line5 = new Line(x, y + 56, x - 7, y + 44);
                line6 = new Line(x , y + 56, x + 7, y + 44);
            }
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
            line1 = new Line(x, y, x + 44, y);
            line2 = new Line(x + 56, y, x + 100, y);
            line3 = new Line(x + 44, y - 7, x + 44, y + 7);
            line4 = new Line(x + 56, y - 7, x + 56, y + 7);
            if (positiveNode.getX() > negativeNode.getX()) {
                line5 = new Line(x + 44, y - 7, x + 56, y);
                line6 = new Line(x + 44, y + 7, x + 56, y);
            }
            else {
                line5 = new Line(x + 56, y - 7, x + 44, y);
                line6 = new Line(x + 56, y + 7, x + 44, y);
            }
        }
        pane.getChildren().addAll(line1,line2,line3,line4,line5,line6);
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

