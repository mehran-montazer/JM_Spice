package elements;

public abstract class Element {
    protected Node positiveTerminal;
    protected Node negativeTerminal;
    protected double current;
    protected double voltage;
    protected String name;
    protected double power;
    public abstract void calculateVoltage();
    public abstract void calculateCurrent();
    //////////////////////////////////////constructor//////////////////////////////
    Element(String name, Node positiveTerminal, Node negativeTerminal){
        this.positiveTerminal = positiveTerminal;
        this.negativeTerminal = negativeTerminal;
        this.name = name;
    }
    /////////////////////////////////getter///////////////////////////
    public double getCurrent() {
        return current;
    }
    public Node getNegativeTerminal() {
        return negativeTerminal;
    }
    public Node getPositiveTerminal() {
        return positiveTerminal;
    }
    public double getPower() {
        return power;
    }
    public double getVoltage() {
        return voltage;
    }
    public String getName() {
        return name;
    }
    /////////////////////////////////setter////////////////////////////
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCurrent(double current) {
        this.current = current;
    }
    public void setNegativeTerminal(Node negativeTerminal) {
        this.negativeTerminal = negativeTerminal;
    }
    public void setPositiveTerminal(Node positiveTerminal) {
        this.positiveTerminal = positiveTerminal;
    }
    public void setPower(double power) {
        this.power = power;
    }

}
