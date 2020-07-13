import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import elements.*;
import handmadeExceptions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//har gooneh copy bardari as in code shar'an haram ast :)
public class Main extends Application {
    public static Stage stage = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("graphic/fxml.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("my title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main (String[] arg){
        launch(arg);//in tikke ro be hamrah tabe start bala command koni eyne ghabl ejra mishe barname !
        ArrayList<Element> elements = null;
        ArrayList<Node> nodes = null;
        ArrayList<Union> unions = null;
        double dv = 0;
        double dt = 0;
        double di = 0;
        double t = 0;
        boolean isEnded = false;
        //Reading File Section
        File file = new File("test/Test1.txt");
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
            reader.findError();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Minus1Exception | Minus2Exception | ReadingException | Minus4Exception | Minus5Exception | Minus3Exception e){
            System.out.println(e.getMessage());
            isEnded = true;
        }
        if (!isEnded) {
            Solver solver = new Solver(elements, nodes, unions, dt, dv, di, t);
            solver.update_nodes();
//            for (Node node : nodes) {
//                System.out.println(node.getName() + ":" + "\t" + node.getV());
//            }
        }
    }


}
