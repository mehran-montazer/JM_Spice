
package elements;

public class Diode extends Element {
    boolean isON;
    public Diode(String name, Node positiveTerminal, Node negativeTerminal) {
        super(name, positiveTerminal, negativeTerminal,0,'d');
    }
    /////////////////////////////////getter///////////////////////////
    public boolean isON() {
        return isON;
    }
    /////////////////////////////////setter///////////////////////////
    public void setON(boolean ON) {
        isON = ON;
    }
    ///////////////////////////////////////////////////////////////////
    public void checkState(){
        if (positiveTerminal.getV() > negativeTerminal.getV())
            isON = true;
        else if (positiveTerminal.getV() < negativeTerminal.getV())
            isON = false;
    }
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

