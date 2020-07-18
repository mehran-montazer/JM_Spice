package elements;

public class moshakhassat {
    public static moshakhassat vizhegi;
    public double voltage;
    public double current;
    public double power ;
    public double t;
    public moshakhassat(double voltage, double current, double power,double t) {
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.t = t;
        moshakhassat.vizhegi = this;
    }
    public moshakhassat(double voltage,double t) {
        this.voltage = voltage;
        this.t = t;
    }
    public static moshakhassat getMoshakhassat(){
        return vizhegi;
    }
}
