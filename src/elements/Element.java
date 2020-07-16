package elements;

import java.util.ArrayList;

public abstract class Element {
    protected static double di;
    protected static double dv;
    protected static double dt;
    public ArrayList<moshakhassat> moshakhassats = new ArrayList<moshakhassat>();
    protected Node positiveTerminal;
    protected Node negativeTerminal;
    protected double current;
    protected double voltage;
    protected String name;
    protected double power;
    protected double value ;
    protected char  type;
    protected double initialCurrent;
    double I_p,I_n,V_p,V_n,I,V;

    public ArrayList<moshakhassat> getMoshakhassats() {
        return moshakhassats;
    }

    public abstract void calculateVoltage();
    public abstract void calculateCurrent();
    public abstract double calculateCurrentR();
    public abstract double calculateCurrentRplus();
    public abstract double calculateCurrentRminus();
    public abstract double calculateCurrentC();
    public abstract double calculateCurrentCplus();
    public abstract double calculateCurrentCminus();
    public abstract double calculateCurrentL();
    public abstract double calculateCurrentLminus();
    public abstract double calculateCurrentLplus();
    public void setstepcurrent(double cuurent){this.initialCurrent=  cuurent;}

    //////////////////////////////////////constructor//////////////////////////////
//    Element(String name, Node positiveTerminal, Node negativeTerminal){
//        this.positiveTerminal = positiveTerminal;
//        this.negativeTerminal = negativeTerminal;
//        this.name = name;
//    }
    //constuctor jadid
    Element(String name,Node positiveTerminal,Node negativeTerminal,double value,char type){
        this.positiveTerminal = positiveTerminal;
        this.negativeTerminal = negativeTerminal;
        this.name = name;
        this.type = type;
        this.value = value;
        this.I_p=0;
        this.I_n=0;
       // this.V_p=0;
       // this.V_n=0;
        this.I =0 ;
       // this.V = 0;
    }
//    public void update_element(double dt,double dv){
//        if (this.type == 'i') {
//            this.I = current;
//            this.I_p = this.I;
//        }
//        else if (this.type == 'r') {
//            this.I = (this.positiveTerminal.V - this.negativeTerminal.V) / this.value;
//            this.I_p = (this.positiveTerminal.V - this.negativeTerminal.V + dv) / this.value ;
//        }
//        else if (this.type == 'l') {
//            this.I += ((this.positiveTerminal.V -  - this.negativeTerminal.V ) * dt) / this.value;
//            this.I_p += ((this.positiveTerminal.V -  - this.negativeTerminal.V  + dv) * dt) / this.value;
//        }
//        else if (this.type == 'c') {
//            this.I = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p)) / dt;
//            this.I_p = (this.value * (this.positiveTerminal.V - this.positiveTerminal.V_p - this.negativeTerminal.V + this.negativeTerminal.V_p + dv)) / dt;
//        }
//        else if (this.type == 'v'){
//        }
//    }

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
    public static double getDt() {
        return dt;
    }
    public static double getDv() {
        return dv;
    }
    public static double getDi() {
        return di;
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
    public static void setDi(double di) {
        Element.di = di;
    }
    public static void setDt(double dt) {
        Element.dt = dt;
    }
    public static void setDv(double dv) {
        Element.dv = dv;
    }
}
