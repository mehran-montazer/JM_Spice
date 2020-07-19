package graphic;

import com.sun.tools.javac.Main;
import elements.*;
import handmadeExceptions.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

public class controller2 implements Initializable {
    //    Solver solver = Solver.getSolver();
    ArrayList<Element> elements = null;
    ArrayList<Node> nodes = null;
    ArrayList<Union> unions = null;
    Solver solver;
    ArrayList<moshakhassat> vizhegi;
    double stepof = 0, stepan = 0;
    boolean isDrawn = true;
    boolean isReady = false;
    byte[][] matrix;
    @FXML
    private Pane circuitGraph;
    @FXML
    private Button remover, plot, homepage, chart, circuitgraph, aboutus, load, run;
    @FXML
    private Pane pane1, pane2, pane3, pane4;
    @FXML
    private TextArea textArea,percentage;
    @FXML
    private LineChart<String, Number> lineChart, lineChart1, lineChart2, lineChart3;
    @FXML
    private XYChart.Series<String, Number> series, series1, series2, series3, series4, series5;
    @FXML
    private ComboBox<String> kachal;
    @FXML
    private TextField time;
    @FXML
    private Button drawBtn;
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    ProgressBar progressBar;
    Thread thread;
    boolean continueable;


    @FXML
    public void buttonactionhandler(javafx.event.ActionEvent e) {
        if (e.getSource() == homepage) {
            pane1.setVisible(true);
            pane1.toFront();
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
        } else if (e.getSource() == chart) {
            pane3.setVisible(true);
            pane3.toFront();
            pane2.setVisible(false);
            pane1.setVisible(false);
            pane4.setVisible(false);
        } else if (e.getSource() == circuitgraph) {
            pane2.setVisible(true);
            pane2.toFront();
            pane1.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
        } else {
            pane4.setVisible(true);
            pane4.toFront();
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane1.setVisible(false);
            ;
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

    public void read_text(javafx.event.ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "File directory is not valid", ButtonType.OK);
        String y = "";
        FileChooser fc = new FileChooser();
        File selectedfile = fc.showOpenDialog(null);
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);

        if (selectedfile != null) {
            y = selectedfile.getAbsolutePath();
        } else
            alert.showAndWait();

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(y))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException q) {
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

        } catch (IOException q) {
            q.printStackTrace();
        } // Progress 1

        Timeline task = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(progressBar.progressProperty(), 1.0))
        );
        progressBar = new ProgressBar(0);


        madar_solver();
        ArrayList<Element> elements = solver.getElements();
        ArrayList<Node> nodes = solver.getNodes();
        ArrayList<Union> unions = solver.getUnions();
        for (Element element : elements) {
            kachal.getItems().addAll(element.getName());
        }


        task.playFromStart();

    }

    public void madar_solver() throws IOException, Minus4Exception, Minus2Exception, Minus3Exception {
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
        } catch (Minus1Exception | Minus2Exception | ReadingException | Minus4Exception | Minus5Exception | Minus3Exception e) {
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
        } catch (Minus4Exception | Minus3Exception | Minus2Exception e) {

        }
    }

    @FXML
    public void plotter(javafx.event.ActionEvent e) {
        Alert alert1 = new Alert(Alert.AlertType.ERROR, "please enter the time of analyse!", ButtonType.OK);
        Alert alert2 = new Alert(Alert.AlertType.ERROR, "please load your input first and run it ", ButtonType.OK);
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

                } else
                    alert1.showAndWait();
            } else
                alert3.showAndWait();
        } else
            alert2.showAndWait();
    }

    @FXML
    public void reset_chart(javafx.event.ActionEvent q) {
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
        kachal.getItems().clear();
    }
    //drawing section

    public void drawCircuit(ActionEvent event) {
        //cleaning Pane
        circuitGraph.getChildren().clear();
        circuitGraph.setLayoutX(pane2.getLayoutX());
        circuitGraph.setPrefSize(pane2.getWidth(), 500);
        //Nodes
        GraphNode[][] graphNodes = new GraphNode[6][6];
        HashMap <Integer, GraphNode>graphNodesHashMap = new HashMap();
        for (int j = 0; j < 6; j++) {
            graphNodes[0][j] = new GraphNode(325 + j * 100, 500, 0);
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                GraphNode graphNode = new GraphNode(325 + j * 100, 500 - 100 * i, (i - 1) * 6 + j + 1);
                graphNodes[i][j] = graphNode;
                graphNodesHashMap.put(graphNode.getNumber(), graphNode);
            }
        }
        //
        try {
            File file = new File("test/test.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textArea.getText());
            bw.close();
            if (!isReady)
                isReady = true;
            file = new File("./test/test.txt");
            Reader reader;
            reader = new Reader(file);
            reader.read();
            nodes = reader.getNodes();
            unions = reader.getUnions();
            elements = reader.getElements();
            matrix = reader.getMatrix();
            reader.findError();
        } catch (IOException q) {
            q.printStackTrace();
        }
        //Reading File Section
         catch (Minus1Exception | Minus2Exception | ReadingException | Minus4Exception | Minus5Exception | Minus3Exception e) {
            System.out.println(e.getMessage());
        }
        //
        if (!isDrawn || isReady){
            isDrawn = true;
            for (Node node : nodes) {
                elements = node.getElements();
                boolean isOK = true;
                byte[] connectedElements = matrix[node.getNameNumber()];
                ArrayList<Integer> parallelNeighbors = new ArrayList<>();
                for (int i = 0; i < 31; i++){
                    if (connectedElements[i] > 1){
                        isOK = false;
                        parallelNeighbors.add(i);
                    }
                }
                if (isOK) {
                    for (Element element : elements) {
                        if (!element.isDrawn()) {
                            GraphNode positive = null;
                            GraphNode negative = null;
                            if (element.getPositiveTerminal().getNameNumber() != 0) {
                                positive = graphNodesHashMap.get(element.getPositiveTerminal().getNameNumber());
                            } else {
                                positive = graphNodes[0][element.getNegativeTerminal().getNameNumber() % 6 - 1];
                            }
                            if (element.getNegativeTerminal().getNameNumber() != 0) {
                                negative = graphNodesHashMap.get(element.getNegativeTerminal().getNameNumber());
                            } else {
                                negative = graphNodes[0][element.getPositiveTerminal().getNameNumber() % 6 - 1];
                            }
                            element.draw(circuitGraph, positive, negative);
                        }
                    }
                }
                else {
                    for (int number : parallelNeighbors){
                        GraphNode mainFirst;
                        GraphNode mainSecond;
                        if (node.getNameNumber() != 0)
                            mainFirst = graphNodesHashMap.get(node.getNameNumber());
                        else
                            mainFirst = graphNodes[0][number % 6 -1];
                        if (number != 0)
                            mainSecond = graphNodesHashMap.get(number);
                        else
                            mainSecond = graphNodes[0][node.getNameNumber() % 6 - 1];
                        int cnt = 0;
                        GraphNode [] subFirst = new GraphNode[4];
                        GraphNode [] subSecond = new GraphNode[4];
                        if ((node.getNameNumber() == 0 || number == 0) || Math.abs(node.getNameNumber() - number) == 6){
                            subFirst[0] = new GraphNode(mainFirst.getX() - 10, mainFirst.getY(), mainFirst.getNumber());
                            subFirst[1] = new GraphNode(mainFirst.getX() + 10, mainFirst.getY(), mainFirst.getNumber());
                            subFirst[2] = new GraphNode(mainFirst.getX() - 30, mainFirst.getY(), mainFirst.getNumber());
                            subFirst[3] = new GraphNode(mainFirst.getX() + 30, mainFirst.getY(), mainFirst.getNumber());
                            subSecond[0] = new GraphNode(mainSecond.getX() - 10, mainSecond.getY(), mainSecond.getNumber());
                            subSecond[1] = new GraphNode(mainSecond.getX() + 10, mainSecond.getY(), mainSecond.getNumber());
                            subSecond[2] = new GraphNode(mainSecond.getX() - 30, mainSecond.getY(), mainSecond.getNumber());
                            subSecond[3] = new GraphNode(mainSecond.getX() + 30, mainSecond.getY(), mainSecond.getNumber());
                        }else {
                            subFirst[0] = new GraphNode(mainFirst.getX(), mainFirst.getY() - 10, mainFirst.getNumber());
                            subFirst[1] = new GraphNode(mainFirst.getX(), mainFirst.getY() + 10, mainFirst.getNumber());
                            subFirst[2] = new GraphNode(mainFirst.getX(), mainFirst.getY() - 30, mainFirst.getNumber());
                            subFirst[3] = new GraphNode(mainFirst.getX(), mainFirst.getY() + 30, mainFirst.getNumber());
                            subSecond[0] = new GraphNode(mainSecond.getX(), mainSecond.getY() - 10, mainSecond.getNumber());
                            subSecond[1] = new GraphNode(mainSecond.getX(), mainSecond.getY() + 10, mainSecond.getNumber());
                            subSecond[2] = new GraphNode(mainSecond.getX(), mainSecond.getY() - 30, mainSecond.getNumber());
                            subSecond[3] = new GraphNode(mainSecond.getX(), mainSecond.getY() + 30, mainSecond.getNumber());
                        }
                        if (matrix[node.getNameNumber()][number] < 5){
                            for (Element element : elements){
                                if (!element.isDrawn()){
                                    GraphNode positive = null;
                                    GraphNode negative = null;
                                    if (element.getPositiveTerminal().getNameNumber() == node.getNameNumber() && element.getNegativeTerminal().getNameNumber() == number) {
                                        positive = subFirst[cnt];
                                        negative = subSecond[cnt];
                                        cnt++;
                                    } else if (element.getPositiveTerminal().getNameNumber() == number && element.getNegativeTerminal().getNameNumber() == node.getNameNumber()){
                                        negative = subFirst[cnt];
                                        positive = subSecond[cnt];
                                        cnt++;
                                    }
                                    switch (cnt) {
                                        case 1:
                                            placeWire(mainFirst, subFirst[0]);
                                            placeWire(mainSecond, subSecond[0]);
                                            break;
                                        case 2:
                                            placeWire(mainFirst, subFirst[1]);
                                            placeWire(mainSecond, subSecond[1]);
                                            break;
                                        case 3:
                                            placeWire(subFirst[0], subFirst[3]);
                                            placeWire(subSecond[0], subSecond[3]);
                                            break;
                                        case 4:
                                            placeWire(subFirst[1], subFirst[4]);
                                            placeWire(subSecond[1], subSecond[4]);
                                            break;
                                        default:
                                            break;
                                    }
                                    if (positive != null && negative != null)
                                        element.draw(circuitGraph, positive, negative);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void placeWire(GraphNode a, GraphNode b){
        Line line = new Line(a.getX(), a.getY(), b.getX(), b.getY());
        circuitGraph.getChildren().add(line);
    }

}
