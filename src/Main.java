import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import elements.*;
//har gooneh copy bardari as in code shar'an haram ast :)
public class Main {
    public static void main (String[] arg){
        ArrayList<Element> elements = null;
        ArrayList<Node> nodes = null;
        ArrayList<Union> unions = null;
        double dv = 0;
        double dt = 0;
        double di = 0;
        double t=0;
        //Reading File Section
        File file = new File("test/Test2.txt");
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
            Element.setDi(di);
            Element.setDv(dv);
            Element.setDt(dt);
            t = reader.getT();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Solver solver = new Solver(elements, nodes, unions, dt, dv, di,t);
        solver.update_nodes();
//        for (Node node:nodes){
//            System.out.println(node.getName() + ":" + "\t" + node.getV() );
//        }
    }
}
