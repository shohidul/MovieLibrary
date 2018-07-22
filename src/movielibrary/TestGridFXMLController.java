/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class TestGridFXMLController implements Initializable {

    @FXML
    private GridPane gridpane;
    private StackPane gridpanesTACK;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridpane = new GridPane();
        gridpanesTACK.getChildren().add(gridpane);
        gridpane.setPadding(new Insets(5));
gridpane.setHgap(5);
gridpane.setVgap(5);

//Declaring variables for Row Count and Column Count
int imageCol = 0;
int imageRow = 0;
        
        for (int i = 0; i < 10; i++) {
//     System.out.println(filelist2.get(i).getName());
            Image image = new Image("https://images-na.ssl-images-amazon.com/images/M/MV5BMjMxOTM1OTI4MV5BMl5BanBnXkFtZTgwODE5OTYxMDI@._V1_UX182_CR0,0,182,268_AL_.jpg");

    ImageView pic = new ImageView();
     pic.setFitWidth(130);
     pic.setFitHeight(130);

     pic.setImage(image);
     HBox hb = new HBox();
     gridpane.add(pic, imageCol, imageRow );
     imageCol++;

     // To check if all the 4 images of a row are completed
     if(imageCol > 3){
          // Reset Column
          imageCol=0;
          // Next Row
          imageRow++;
     }
}
        // TODO
    }    
    
}
