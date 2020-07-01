import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import elements.*;
//har gooneh copy bardari as in code shar'an haram ast :)
public class Main {
    public static void main (String[] arg){
        ArrayList<Element> elements;
        ArrayList<Node> nodes;
        ArrayList<Union> unions;
        double dv;
        double dt;
        double di;
        double t;
        //Reading File Section
        File file = new File("input.txt");
        Reader reader;
        try {
            reader = new Reader(file);
            reader.read();
            nodes = reader.getNodes();
            unions = reader.getUnions();
            elements = reader.getElements();
            dv = reader.getDv();
            di = reader.getDi();
            dt = reader.getDt();
            t = reader.getT();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
