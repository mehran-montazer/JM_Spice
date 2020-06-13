package elements;

public class Inductor extends Element {
    double inductance;
    double initialCurrent;
    //////////////////////////////////////constructor//////////////////////////////
    Inductor(String name, Node positiveTerminal, Node negativeTerminal, double inductance, double initialCurrent) {
        super(name, positiveTerminal, negativeTerminal);
        this.inductance = inductance;
        this.initialCurrent = initialCurrent;
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

    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }
}
