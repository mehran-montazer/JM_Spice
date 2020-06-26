package elements;

public abstract class Element {
    protected Node positiveTerminal;
    protected Node negativeTerminal;
    protected double current;
    protected double voltage;
    protected String name;
    protected double power;
    double value ;
    String  type;
    double I_p,I_n,V_p,V_n,I,V;
    double dv,dt;
    public abstract void calculateVoltage();
    public abstract void calculateCurrent();
    //////////////////////////////////////constructor//////////////////////////////
//    Element(String name, Node positiveTerminal, Node negativeTerminal){
//        this.positiveTerminal = positiveTerminal;
//        this.negativeTerminal = negativeTerminal;
//        this.name = name;
//    }
    //constuctor jadid
    Element(String name,Node positiveTerminal,Node negativeTerminal,double value,String type){
        this.positiveTerminal = positiveTerminal;
        this.negativeTerminal = negativeTerminal;
        this.name = name;
        this.value = value;
        this.type = type;
        this.I_p=0;
        this.I_n=0;
       // this.V_p=0;
       // this.V_n=0;
        this.I =0 ;
       // this.V = 0;
    }
    public void update_element(double dt,double dv){
        if (this.type.equals("I")) {
            this.I = this.value;
            this.I_p = this.I;
        }
        else if (this.type.equals("R")) {
            this.I = (this.positiveTerminal.V - this.negativeTerminal.V) / this.value;
            this.I_p = (this.positiveTerminal.V - this.negativeTerminal.V + dv) / this.value ;
        }
        else if (this.type == "L") {
            this.I += ((this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p) * dt) / this.value;
            this.I_p += ((this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p + dv) * dt) / this.value;
        }
        else if (this.type == "C") {
            this.I = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p)) / dt;
            this.I_p = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p + dv)) / dt;
        }
        else if (this.type == "V"){
        }
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
