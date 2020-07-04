package elements;

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
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrentI() {

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
        this.I = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_Step - this.negativeTerminal.V + this.negativeTerminal.V_Step)) / dt;
        return I;
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
