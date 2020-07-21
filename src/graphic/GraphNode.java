package graphic;

public class GraphNode {
    private int number;
    private int x;
    private int y;
    ///////////////////////////////constructor//////////////////////////
    public GraphNode(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;
    }
    //////////////////////////////getter////////////////////////////////
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getNumber() {
        return number;
    }
    //////////////////////////////setter/////////////////////////////////
    public void setNumber(int number) {
        this.number = number;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
