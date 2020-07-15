package elements;

public class moshakhassat {
    public double voltage;
    public double current;
    public double power ;
    public double t;
    public moshakhassat(double voltage, double current, double power,double t) {
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.t = t;
    }
    public moshakhassat(double voltage) {
        this.voltage = voltage;
    }
}
