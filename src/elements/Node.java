package elements;

public class Node {
    private String name;
    private double voltage;
    ////////////////////////////////constructor////////////////////////
    Node (String name){
        this.name = name;
        voltage = 0;
    }
    /////////////////////////////////getter///////////////////////////
    public String getName() {
        return name;
    }
    public double getVoltage() {
        return voltage;
    }
    /////////////////////////////////setter////////////////////////////
    public void setName(String name) {
        this.name = name;
    }
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
}
