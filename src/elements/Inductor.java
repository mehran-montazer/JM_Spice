package elements;

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
