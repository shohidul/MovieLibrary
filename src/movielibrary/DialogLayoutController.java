/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author B3
 */
public class DialogLayoutController implements Initializable {

    @FXML
    private Button closeDetailsBtn;
    @FXML
    private AnchorPane detailsAnchorPane;
    public Label lab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeDetailsBtnAction(ActionEvent event) {
        detailsAnchorPane.toBack();
    }
    
    
}
