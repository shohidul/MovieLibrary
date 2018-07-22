/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jdbc.HibernateDao;
import jdbc.Movie;
import jdbc.MovieGenre;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class TestFXMLController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private Button button;
    private static byte[] myImg;
    @FXML
    private FlowPane hdp;
    @FXML
    private Button button1;
    Image image;
    private String str;
    private int num;
    private ObservableList<Movie> mvelist;
    private String posters;
    ArrayList<String> arrayList;
    @FXML
    private StackPane stacktest;
    private ImageView imageView1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        HibernateDao hd = (HibernateDao)context.getBean("hibernateDao");
           List <Movie> list = hd.sortByLastAdded();
            for (Movie e : list) {
               
                System.out.println( e.getMovieId()+" "+e.getAddedDate());
            }
        
    }

    @FXML
    private void btnActionMethod(ActionEvent event) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
        mvelist = FXCollections.observableArrayList(hd.getMovies());
        for (Movie e : mvelist) {
            posters = e.getPoster();
            String[] strValues = posters.split(" ");
            arrayList = new ArrayList<String>(Arrays.asList(strValues));
//            System.out.println("in"+aListNumbers);
            for (int i = 0; i < arrayList.size(); i++) {

                image = new Image(e.getPoster());

                imageView1 = new ImageView();
                VBox vbox1 = new VBox();
                Label title1 = new Label();
                Label year1 = new Label();
                title1.setText(e.getTitle());
                year1.setText("Relesed in " + e.getYear());
                vbox1.setPrefSize(126, 180);
                imageView1.setFitWidth(106);
                imageView1.setFitHeight(140);

                imageView1.setImage(image);
                vbox1.getChildren().addAll(imageView1, title1, year1);
                imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
//                pic.getImage().get;
                        AnchorPane anchorPane2 = new AnchorPane();
                        anchorPane2.setStyle("-fx-background-color: white;");
                        ImageView imageView2 = new ImageView(e.getPoster());
                        imageView2.setFitHeight(300);
                        imageView2.setFitWidth(200);
                        Label title2 = new Label();
                        title2.setText(e.getTitle());
                        Label year2 = new Label();
                        year2.setText("Relesed in " + e.getYear());
                        Button b = new Button("close");
                        b.setOnAction((event) -> {
                            anchorPane2.toBack();
                        });
                        VBox vbox2 = new VBox();
                        vbox2.setPrefSize(200, 350);
                        vbox2.getChildren().addAll(imageView2, b, title2, year2);
                        anchorPane2.getChildren().addAll(vbox2);

                        stacktest.getChildren().add(anchorPane2);

                    }
                });
                hdp.getChildren().add(vbox1);

            }

        }
//        ArrayList list = new ArrayList();
//        list.add("posters/147365_1366_768.jpg");
//        list.add("posters/AL_.jpg");
//        list.add("posters/worm_bodies.jpg");
//        list.add("posters/WWW.YTS.AG.jpg");
//        
//          for (int i = 0; i < list.size(); i++) {
//                    
//             imagea = new Image(list.get(i).toString());
//
//                  ImageView  pic = new ImageView();
//                  Pane p = new Pane();
//                  p.setPrefSize(126, 160);
//                    pic.setFitWidth(106);
//                    pic.setFitHeight(140);
//                    
//                   
//                    pic.setImage(imagea);
//                    p.getChildren().add(pic);
//                    hdp.getChildren().add(p); 
//        
//    }
    }

    @FXML
    private void btnActionMethod1(ActionEvent event) {
//        for (int i = 0; i < 2; i++) {
//
//            image = new Image("posters/worm_bodies.jpg");
//
//            ImageView pic = new ImageView();
//            Pane p = new Pane();
//            p.setPrefSize(126, 160);
//            pic.setFitWidth(106);
//            pic.setFitHeight(140);
//
//            pic.setImage(image);
//            p.getChildren().add(pic);
//            hdp.getChildren().add(p);
//            // TODO
//        }

    ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        HibernateDao hd = (HibernateDao)context.getBean("hibernateDao");
           List <MovieGenre> list = hd.getAllMovieGenre("Mov004");
            for (MovieGenre e : list) {
                String  allId= String.valueOf(e.getId());
                String [] arrayId = allId.split(" ");
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arrayId));
                hd.deleteMovieGenre(Integer.parseInt(arrayList.get(0)));
                System.out.println(arrayList);
            }
            
    }
}
