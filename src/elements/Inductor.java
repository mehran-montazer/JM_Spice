package elements;

public class Inductor extends Element {
    double inductance;
    double initialCurrent;
    //////////////////////////////////////constructor//////////////////////////////
    public Inductor(String name, Node positiveTerminal, Node negativeTerminal, double inductance) {
        super(name, positiveTerminal, negativeTerminal, inductance, 'l');
        this.inductance = inductance;
        this.initialCurrent = 0;
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
