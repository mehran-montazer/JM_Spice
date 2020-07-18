package elements;

public class CurrentSource extends Element {
    private double IoffSet = 0;
    private double Iamp = 0;
    private double frequency = 0;
    private double phase = 0;
    private Node positiveDependent = null;
    private Node negativeDependent = null;
    private double gain = 0;
    private Element dependentCurrentElement = null;
    private boolean isAC;
    /////////////////////////////////constructor///////////////////////////
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, Node positiveDependent, Node negativeDependent, double gain){
        super(name, positiveTerminal, negativeTerminal,0,'i');
        this.positiveDependent = positiveDependent;
        this.negativeDependent = negativeDependent;
        this.gain = gain;
        isAC = false;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, Element dependentCurrentElement, double gain){
        super(name, positiveTerminal, negativeTerminal,0,'i');
        this.dependentCurrentElement = dependentCurrentElement;
        this.gain = gain;
        isAC = false;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double IoffSet, double Iamp, double frequency, double phase){
        super(name, positiveTerminal, negativeTerminal,0, 'i');
        this.IoffSet = IoffSet;
        this.frequency = frequency;
        this.Iamp = Iamp;
        this.phase = phase;
        isAC = true;
    }
    public CurrentSource(String name, Node positiveTerminal, Node negativeTerminal, double current) {
        super(name, positiveTerminal, negativeTerminal, current, 'i');
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
    //////////////////////////////////////////////////////////////////
    @Override
    public void draw(){

    }
    @Override
    public void calculateVoltage() {

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

    @Override
    public void calculateCurrent() {
        if (!(dependentCurrentElement == null)){
            current = gain * dependentCurrentElement.getCurrent();
        }
        else if (!(negativeDependent == null || positiveDependent == null)){
            current = gain * (positiveDependent.getV() - negativeDependent.getV());
        }
    }

    public void calculateCurrent(double time){
        current = IoffSet + Iamp * Math.cos(2 * Math.PI * frequency * time + phase);
    }
}
