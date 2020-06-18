package elements;

public class CurrentSource extends Element {
    private double IoffSet = 0;
    private double Iamp = 0;
    private double frequency = 0;
    private double phase = 0;
    /////////////////////////////////constructor///////////////////////////
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double IoffSet, double Iamp, double frequency, double phase){
        super(name, positiveTerminal, negativeTerminal);
        this.IoffSet = IoffSet;
        this.frequency = frequency;
        this.Iamp = Iamp;
        this.phase = phase;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double current) {
        super(name, positiveTerminal, negativeTerminal);
        this.current = current;
    }
    /////////////////////////////////getter///////////////////////////
    public double getPhase() {
        return phase;
    }
    public double getFrequency() {
        return frequency;
    }
    public double getIamp() {
        return Iamp;
    }
    public double getIoffSet() {
        return IoffSet;
    }
    /////////////////////////////////setter///////////////////////////
    public void setIamp(double iamp) {
        Iamp = iamp;
    }
    public void setIoffSet(double ioffSet) {
        IoffSet = ioffSet;
    }
    public void setPhase(double phase) {
        this.phase = phase;
    }
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    //////////////////////////////////////////////////////////////////
    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }

    public void calculateCurrent(double time){
        current = IoffSet + Iamp * Math.cos(2 * Math.PI * frequency + phase);
    }
}
