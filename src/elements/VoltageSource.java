package elements;

public class VoltageSource extends Element {
    private double VoffSet = 0;
    private double Vamp = 0;
    private double frequency = 0;
    private double phase = 0;
    private Node positiveDependent = null;
    private Node negativeDependent = null;
    private double gain = 0;
    private Element dependentCurrentElement = null;
    private boolean isAC;
    /////////////////////////////////constructor///////////////////////////
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, double VoffSet, double Vamp, double frequency, double phase){
        super(name, positiveTerminal, negativeTerminal, 0,'v');
        this.VoffSet = VoffSet;
        this.frequency = frequency;
        this.Vamp = Vamp;
        this.phase = phase;
        isAC = true;
    }
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, double voltage) {
        super(name, positiveTerminal, negativeTerminal, 0, 'v');
        this.voltage = voltage;
        isAC = false;
    }
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, Node positiveDependent, Node negativeDependent, double gain){
        super(name, positiveTerminal, negativeTerminal, 0, 'v');
        this.positiveDependent = positiveDependent;
        this.negativeDependent = negativeDependent;
        this.gain = gain;
        isAC = false;
    }
    public VoltageSource(String name, Node positiveTerminal, Node negativeTerminal, Element dependentCurrentElement, double gain){
        super(name, positiveTerminal, negativeTerminal,0, 'v');
        this.dependentCurrentElement = dependentCurrentElement;
        this.gain = gain;
        isAC = false;
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
    public void setDependentCurrentElement(Element dependentCurrentElement) {
        this.dependentCurrentElement = dependentCurrentElement;
    }
    public void setGain(double gain) {
        this.gain = gain;
    }
    public void setNegativeDependent(Node negativeDependent) {
        this.negativeDependent = negativeDependent;
    }
    public void setPositiveDependent(Node positiveDependent) {
        this.positiveDependent = positiveDependent;
    }
    public void setAC(boolean AC) {
        isAC = AC;
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
    public double getGain() {
        return gain;
    }
    public Node getPositiveDependent() {
        return positiveDependent;
    }
    public Node getNegativeDependent() {
        return negativeDependent;
    }
    public Element getDependentCurrentElement() {
        return dependentCurrentElement;
    }
    public boolean isAC() {
        return isAC;
    }
    ////////////////////////////////////////////////////////////////////////////////
    @Override
    public void calculateVoltage() {
        if (!(dependentCurrentElement == null)){
            voltage = gain * dependentCurrentElement.getCurrent();
        }
        else if (!(negativeDependent == null || positiveDependent == null)){
            voltage = gain * (positiveDependent.getV() - negativeDependent.getV());
        }
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


    public void calculateVoltage(double time){
        voltage = VoffSet + Vamp * Math.cos(2 * Math.PI * frequency * time + phase);
    }
}
