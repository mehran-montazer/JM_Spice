package elements;

public class VoltageSource extends Element {
    private double VoffSet = 0;
    private double Vamp = 0;
    private double frequency = 0;
    private double phase = 0;
    /////////////////////////////////constructor///////////////////////////
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, double VoffSet, double Vamp, double frequency, double phase){
        super(name, positiveTerminal, negativeTerminal);
        this.VoffSet = VoffSet;
        this.frequency = frequency;
        this.Vamp = Vamp;
        this.phase = phase;
    }
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, double voltage) {
        super(name, positiveTerminal, negativeTerminal);
        this.voltage = voltage;
    }
    /////////////////////////////////setter///////////////////////////
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
    public void setPhase(double phase) {
        this.phase = phase;
    }
    public void setVamp(double vamp) {
        Vamp = vamp;
    }
    public void setVoffSet(double voffSet) {
        VoffSet = voffSet;
    }
    /////////////////////////////////getter///////////////////////////
    public double getFrequency() {
        return frequency;
    }
    public double getPhase() {
        return phase;
    }
    public double getVamp() {
        return Vamp;
    }
    public double getVoffSet() {
        return VoffSet;
    }
    ////////////////////////////////////////////////////////////////////////////////
    @Override
    public void calculateVoltage() {

    }

    @Override
    public void calculateCurrent() {

    }
    public void calculateVoltage(double time){
        current = VoffSet + Vamp * Math.cos(2 * Math.PI * frequency + phase);
    }
}
