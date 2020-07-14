package graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class controller2 implements Initializable {



    @FXML
    private Button homepage,chart,circuitgraph,aboutus;
    @FXML
    private Pane pane1,pane2,pane3,pane4;
    public void buttonactionhandler (javafx.event.ActionEvent e){
        if (e.getSource() == homepage){
            System.out.println("salam");
            pane1.toFront();
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
        }
        else if (e.getSource() == chart){
            pane3.toFront();
        }
        else if (e.getSource() == circuitgraph){
            pane2.toFront();
        }
        else {
            pane4.toFront();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
