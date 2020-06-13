package elements;

public class VoltageSource extends Element {
    VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, double voltage) {
        super(name, positiveTerminal, negativeTerminal);
        this.voltage = voltage;
    }

    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }
}
