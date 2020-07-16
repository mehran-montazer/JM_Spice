package graphic;

import com.sun.tools.javac.Main;
import elements.Element;
import elements.Node;
import elements.Solver;
import elements.Union;
import handmadeExceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.sound.sampled.Line;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class controller2 implements Initializable {
//    Solver solver = Solver.getSolver();
    ArrayList<Element> elements = null;
    ArrayList<Node> nodes = null;
    ArrayList<Union> unions = null;
      Solver solver ;
    @FXML
    private Button homepage,chart,circuitgraph,aboutus,load,run;
    @FXML
    private Pane pane1,pane2,pane3,pane4;
    @FXML
    private TextArea textArea;
    @FXML
    private LineChart<String,Number> lineChart;
    @FXML
    private XYChart.Series<String,Number> series,series1,series2;
    @FXML
    private ComboBox<String> kachal ;

    ObservableList<String> list = FXCollections.observableArrayList();


    @FXML
    public void buttonactionhandler (javafx.event.ActionEvent e){
        if (e.getSource() == homepage){
            pane1.setVisible(true);
            pane1.toFront();
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
        }
        else if (e.getSource() == chart){
            pane3.setVisible(true);
            pane3.toFront();
            pane2.setVisible(false);
            pane1.setVisible(false);
            pane4.setVisible(false);
        }
        else if (e.getSource() == circuitgraph){
            pane2.setVisible(true);
            pane2.toFront();
            pane1.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
        }
        else {
            pane4.setVisible(true);
            pane4.toFront();
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane1.setVisible(false);;
        }
//        Solver.getSolver();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        series = new XYChart.Series<>();
        series.setName("V");
        series1 = new XYChart.Series<>();
        series1.setName("I");
        series2 = new XYChart.Series<>();
        series2.setName("P");
        kachal.setItems(list);
    }
    public void read_text(javafx.event.ActionEvent e){
        String y = "";
        FileChooser fc = new FileChooser();
        File selectedfile = fc.showOpenDialog(null);
        if (selectedfile != null){
            y = selectedfile.getAbsolutePath();
        }
        else
            System.out.println("File directory is not valid !");

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(y)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException q)
        {
            q.printStackTrace();
        }
        textArea.setText(contentBuilder.toString());
    }
    @FXML
    public void write_text(javafx.event.ActionEvent e) throws IOException {
        try {
            File file = new File("test/test.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textArea.getText());
            bw.close();

        }
        catch (IOException q)
        {
            q.printStackTrace();
        }
        madar_solver();
        ArrayList<Element> elements = solver.getElements();
        ArrayList<Node> nodes = solver.getNodes();
        ArrayList<Union> unions = solver.getUnions();
        for (Element element: elements){
            kachal.getItems().addAll(element.getName()) ;
        }
    }
    public void madar_solver() throws IOException {
        ArrayList<Element> elements = null;
        ArrayList<Node> nodes = null;
        ArrayList<Union> unions = null;
        double dv = 0;
        double dt = 0;
        double di = 0;
        double t = 0;
        boolean isEnded = false;
        //Reading File Section
        File file = new File("./test/test.txt");
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
//        elements = solver.getElements();
//        nodes = solver.getNodes();
//        unions = solver.getUnions();
        if (!isEnded) {
            solver = new Solver(elements, nodes, unions, dt, dv, di, t);
            solver.update_nodes();
            solver.print_console();
//            for (Node node : nodes) {
//                System.out.println(node.getName() + ":" + "\t" + node.getV());
//            }
        }
    }


}
