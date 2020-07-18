package graphic;

import com.sun.tools.javac.Main;
import elements.*;
import handmadeExceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.sound.sampled.Line;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class controller2 implements Initializable {
//    Solver solver = Solver.getSolver();
    ArrayList<Element> elements = null;
    ArrayList<Node> nodes = null;
    ArrayList<Union> unions = null;
      Solver solver ;
      ArrayList<moshakhassat> vizhegi;
      double stepof=0,stepan=0;
    @FXML
    private Button remover,plot,homepage,chart,circuitgraph,aboutus,load,run;
    @FXML
    private Pane pane1,pane2,pane3,pane4;
    @FXML
    private TextArea textArea;
    @FXML
    private LineChart<String,Number> lineChart,lineChart1,lineChart2,lineChart3;
    @FXML
    private XYChart.Series<String,Number> series,series1,series2,series3,series4,series5;
    @FXML
    private ComboBox<String> kachal ;
    @FXML
    private TextField time;
    @FXML
    private Button drawBtn;
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
        series.setName("Voltage(V)");
        series1 = new XYChart.Series<>();
        series1.setName("Current(A)");
        series2 = new XYChart.Series<>();
        series2.setName("Power(W)");
        series3 = new XYChart.Series<>();
        series3.setName("Voltage(V)");
        series4 = new XYChart.Series<>();
        series4.setName("Current(A)");
        series5 = new XYChart.Series<>();
        series5.setName("Power(W)");
        kachal.setItems(list);
//        series.getData().addAll(new XYChart.Data("1",20));
//        series.getData().addAll(new XYChart.Data("2",-00000.2));
//        series.getData().addAll(new XYChart.Data("3",20000000));
//        lineChart.getData().addAll(series);


    }
    public void read_text(javafx.event.ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.ERROR,"File directory is not valid",ButtonType.OK);
        String y = "";
        FileChooser fc = new FileChooser();
        File selectedfile = fc.showOpenDialog(null);
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);

        if (selectedfile != null){
            y = selectedfile.getAbsolutePath();
        }
        else
            alert.showAndWait();

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
    public void write_text(javafx.event.ActionEvent e) throws IOException, Minus3Exception, Minus2Exception, Minus4Exception {
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
    public void madar_solver() throws IOException, Minus4Exception, Minus2Exception, Minus3Exception {
        isDrawn = false;
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
        try {
            if (!isEnded) {
                stepof = (t / dt) / 1000;
                stepan = dt;
                solver = new Solver(elements, nodes, unions, dt, dv, di, t);
                solver.update_nodes();
                solver.print_console();
//            for (Node node : nodes) {
//                System.out.println(node.getName() + ":" + "\t" + node.getV());
//            }
            }
        }catch (Minus4Exception | Minus3Exception | Minus2Exception e){

        }
    }
    @FXML
    public void plotter(javafx.event.ActionEvent e){
        Alert alert1 = new Alert(Alert.AlertType.ERROR,"please enter the time of analyse!",ButtonType.OK);
        Alert alert2 = new Alert(Alert.AlertType.ERROR,"please load your input first and run it ",ButtonType.OK);
        Alert alert3 = new Alert(Alert.AlertType.ERROR, "please choose the element!", ButtonType.OK);
        String name = null;
        if (solver != null) {
            double analyse = 0;
            String y = kachal.getValue();
            if (kachal.getValue() != null) {
                name = kachal.getValue();
                if (!time.getText().equals("")) {
                    analyse = Double.parseDouble(time.getText());
                    ArrayList<Element> elements = solver.getElements();
                    ArrayList<Node> nodes = solver.getNodes();
                    ArrayList<Union> unions = solver.getUnions();
                    if (kachal != null) {
                        for (Element element : elements) {
                            if (element.getName().equals(name)) {
                                vizhegi = element.getMoshakhassats();
                                break;
                            }
                        }
                    } else {
                        alert2.showAndWait();
                    }
                    int p = -1;
                    for (moshakhassat vizh : vizhegi) {
                        p++;
                        if (p % 1000 == 0 && (vizh.t < analyse)) {
                            series.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.voltage));
                            series3.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.voltage));
                            series1.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.current));
                            series4.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.current));
                            series2.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.power));
                            series5.getData().addAll(new XYChart.Data(String.format("%.2f", vizh.t), vizh.power));
                        }
                    }
                    lineChart.getData().addAll(series3);
                    lineChart.getData().addAll(series4);
                    lineChart.getData().addAll(series5);
                    lineChart1.getData().addAll(series);
                    lineChart2.getData().addAll(series1);
                    lineChart3.getData().addAll(series2);

                }
                else
                    alert1.showAndWait();
            }
            else
                alert3.showAndWait();
        }
        else
            alert2.showAndWait();
    }
    @FXML
    public void reset_chart(javafx.event.ActionEvent q){
        series.getData().clear();
        series1.getData().clear();
        series2.getData().clear();
        series3.getData().clear();
        series4.getData().clear();
        series5.getData().clear();
        lineChart.getData().clear();
        lineChart2.getData().clear();
        lineChart1.getData().clear();
        lineChart3.getData().clear();
    }
    boolean isDrawn = false;
    //drawing section
    public void drawCircuit(ActionEvent event){
        if (!isDrawn){
            isDrawn = true;

        }
    }

}
