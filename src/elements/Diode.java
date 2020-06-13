package elements;

public class Diode extends Element {
    boolean isON;
    Diode(String name, Node positiveTerminal, Node negativeTerminal) {
        super(name, positiveTerminal, negativeTerminal);
    }
    /////////////////////////////////getter///////////////////////////
    public boolean isON() {
        return isON;
    }
    /////////////////////////////////setter///////////////////////////
    public void setON(boolean ON) {
        isON = ON;
    }

    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }
}
