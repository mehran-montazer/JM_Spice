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
    public void calculateCurrent() {

    }
}
