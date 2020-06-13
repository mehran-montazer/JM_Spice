package elements;

public class CurrentSource extends Element {

    CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double current) {
        super(name, positiveTerminal, negativeTerminal);
        this.current = current;
    }

    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }
}
