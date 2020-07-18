package elements;

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
    public void draw(){

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
