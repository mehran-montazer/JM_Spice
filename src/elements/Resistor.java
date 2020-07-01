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
    public void calculateVoltage() {
        voltage = positiveTerminal.getVoltage() - negativeTerminal.getVoltage();
    }
    @Override
    public void calculateCurrent() {
        current = voltage / resistance;
    }
}
