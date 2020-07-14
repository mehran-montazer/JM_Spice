package elements;

public class moshakhassat {
    public double voltage;
    public double current;
    public double power ;
    public moshakhassat(double voltage, double current, double power) {
        this.voltage = voltage;
        this.current = current;
        this.power = power;
    }
    public moshakhassat(double voltage) {
        this.voltage = voltage;
    }
}
