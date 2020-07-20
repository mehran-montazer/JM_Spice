package graphic;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertPathValidatorException;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane root;
    private AnchorPane parent;
    @FXML
    private Button button;
//        private Label mymessage;
//        public void random(ActionEvent event) {
//            Random rand = new Random();
//            int randi = rand.nextInt(100) + 1;
//            mymessage.setText(Integer.toString(randi));
////            System.out.println(Integer.toString(randi));
//        }
        public void initialize(URL url , ResourceBundle rb){

        }
//        public void loadsplash() throws IOException {
//            StackPane pane = FXMLLoader.loa d(getClass().getResource("fxml.fxml"));
//
//        }
//}
    public void start(ActionEvent event) throws Exception{
            Parent app_page = FXMLLoader.load(getClass().getResource("fxml2.fxml"));
        Scene app_page_scene  = new Scene(app_page);
        Stage App_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        App_stage.setScene(app_page_scene);
        App_stage.setTitle("JM_Spice");
        App_stage.show();
    }
}
