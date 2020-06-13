package elements;

public class Capacitor extends Element{
    private double initialVoltage;
    private double capacity;
    //////////////////////////////////////constructor//////////////////////////////
    Capacitor(String name, Node positiveTerminal, Node negativeTerminal, double capacity, double initialVoltage){
        super(name, positiveTerminal, negativeTerminal);
        this.capacity = capacity;
        this.initialVoltage = initialVoltage;
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
    public void calculateCurrent() {

    }
}
