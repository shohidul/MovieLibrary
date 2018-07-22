/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import jdbc.HibernateDao;
import jdbc.Movie;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class MovieTableController implements Initializable {

    @FXML
    private AnchorPane tableAnchor;
    @FXML
    private Button closeTable;
    @FXML
    private TableView<Movie> movieTable;

    ObservableList<Movie> mvelist;
    @FXML
    private TextField filterInput;
    @FXML
    private TableColumn<Movie, String> posterColumn;
    @FXML
    private TableColumn<Movie, String> idColumn;
    @FXML
    private TableColumn<Movie, String> titleColumn;
    @FXML
    private TableColumn<Movie, String> descColumn;
    @FXML
    private TableColumn<Movie, String> runTimeColumn;
    @FXML
    private TableColumn<Movie, Integer> yearColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, Double> ratingColumn;
    @FXML
    private TableColumn<Movie, String> watchColumn;
    @FXML
    private TableColumn<Movie, String> favouriteColumn;
    @FXML
    private TableColumn<Movie, Date> dateColumn;
    @FXML
    private ImageView img;
    @FXML
    private Button resetFormBtn;
    int count = 0;
    @FXML
    private StackPane movieTableStackPane;
    @FXML
    private Label foundItemLabel;
    private WritableImage image;
//    private String allPosters;
//    ArrayList<String> aListNumbers;
    @FXML
    private Button deleteBtn;
    private String id;
    private String path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        filterInput.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterMovieList((String) oldValue, (String) newValue);
            }

        });
//        createColumns();
//        fillTable();
            movieTable.setId("movieTable");
    }

    @FXML
    private void closeTableAction(ActionEvent event) {
        tableAnchor.toBack();
    }

    public void filterMovieList(String oldValue, String newValue) {
        ObservableList<Movie> filteredListIncrease = FXCollections.observableArrayList();
        ObservableList<Movie> filteredListDecrese = FXCollections.observableArrayList();
        if (filterInput.equals("") || newValue.equals("")) {
            movieTable.setItems(mvelist);
            //counts records
            int totalRecord = mvelist.size();
            if (totalRecord <= 1) {
                foundItemLabel.setText("Found " + totalRecord + " movie");
            } else {
                foundItemLabel.setText("Found " + totalRecord + " movies");
            }

        } else if (newValue.length() < oldValue.length()) {
            newValue = newValue.toUpperCase();
            for (Movie movie : mvelist) {
                String filterName = movie.getTitle();
                String filterId = movie.getMovieId();
                if (filterName.toUpperCase().contains(newValue) || filterId.toUpperCase().contains(newValue)) {
                    filteredListDecrese.add(movie);
                }
            }
            movieTable.setItems(filteredListDecrese);
            //counts records
            int totalRecord = filteredListDecrese.size();
            if (totalRecord <= 1) {
                foundItemLabel.setText("Found " + totalRecord + " movie");
            } else {
                foundItemLabel.setText("Found " + totalRecord + " movies");
            }

        } else {
            newValue = newValue.toUpperCase();
            for (Movie movie : mvelist) {
                String filterName = movie.getTitle();
                String filterId = movie.getMovieId();
                if (filterName.toUpperCase().contains(newValue) || filterId.toUpperCase().contains(newValue)) {
                    filteredListIncrease.add(movie);

                }
            }
            movieTable.setItems(filteredListIncrease);
            //counts records
            int totalRecord = filteredListIncrease.size();
            if (totalRecord <= 1) {
                foundItemLabel.setText("Found " + totalRecord + " movie");
            } else {
                foundItemLabel.setText("Found " + totalRecord + " movies");
            }
        }
    }

    public void createColumns() throws BeansException {

        posterColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("poster"));
        posterColumn.setStyle( "-fx-alignment: CENTER;");
        posterColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
            @Override
            public TableCell<Movie, String> call(TableColumn<Movie, String> param) {
                return new TableCell<Movie, String>() {

                    ImageView iv;
                    VBox vbox;

                    {
                        vbox = new VBox();
                        vbox.setAlignment(Pos.CENTER);
                        iv = new ImageView();
                        iv.setFitHeight(155);
                        iv.setFitWidth(105);
                        vbox.getChildren().add(iv);
                        vbox.setMaxSize(120, 170);
                        setGraphic(vbox);

                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (item != null) {

                            iv.setImage(new Image(item));
                        }
                    }
                };

            }
        });

//        TableColumn<Movies, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieId"));
        idColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        titleColumn.setStyle( "-fx-alignment: CENTER;");
        titleColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {

            @Override
            public TableCell<Movie, String> call(
                    TableColumn<Movie, String> param) {
                TableCell<Movie, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(titleColumn.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });
//        TableColumn<Movies, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("description"));
        descColumn.setStyle( "-fx-alignment: CENTER;");
        descColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {

            @Override
            public TableCell<Movie, String> call(
                    TableColumn<Movie, String> param) {
                TableCell<Movie, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(descColumn.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });

//        TableColumn<Movies, String> runTimeColumn = new TableColumn<>("Running Time");
        runTimeColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("runningTime"));
        runTimeColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        yearColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, String> genreColumn = new TableColumn<>("All Genres");
        genreColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("allMovieGenres"));
        genreColumn.setStyle( "-fx-alignment: CENTER;");
        genreColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {

            @Override
            public TableCell<Movie, String> call(
                    TableColumn<Movie, String> param) {
                TableCell<Movie, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_PREF_SIZE);
                text.wrappingWidthProperty().bind(genreColumn.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });

//        TableColumn<Movies, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Movie, Double>("rating"));
        ratingColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, String> watchColumn = new TableColumn<>("Watch Status");
        watchColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("watchStatus"));
        watchColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, String> favouriteColumn = new TableColumn<>("Favourite");
        favouriteColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("favourite"));
        favouriteColumn.setStyle( "-fx-alignment: CENTER;");

//        TableColumn<Movies, Date> dateColumn = new TableColumn<>("Added Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Movie, Date>("addedDate"));
        dateColumn.setStyle( "-fx-alignment: CENTER;");

//        movieTable.getColumns().addAll(idColumn, titleColumn, descColumn, runTimeColumn, yearColumn, 
//                genreColumn, ratingColumn, watchColumn, favouriteColumn, dateColumn);
    }

    private void fillTable() {

        movieTable.setItems(getMoviesTable());
    }

    public ObservableList<Movie> getMoviesTable() throws BeansException {
        // TODO
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
        mvelist = FXCollections.observableArrayList(hd.getMovies());

        //counts records
        int totalRecord = mvelist.size();
        if (totalRecord <= 1) {
            foundItemLabel.setText("Found " + totalRecord + " movie");
        } else {
            foundItemLabel.setText("Found " + totalRecord + " movies");
        }

        return mvelist;
    }

    @FXML
    private void resetFormBtnAction(MouseEvent e) {

//        if (e.getClickCount() >= 1) {
//            count += 1;
//                System.out.println(count);
//        } else {
//        }
//        if (count <= 1) {
//            MaskerPane masker = new MaskerPane();
//            movieTableStackPane.getChildren().add(masker);
            fillTable();
            createColumns();
            filterInput.clear();

//            masker.setVisible(false);
//        } else {
//            fillTable();
////            createColumns();
//            filterInput.clear();
//            System.out.println("else");
//        }
    }

    @FXML
    private void deleteBtnAction(ActionEvent event) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
        hd.deleteMovie(id);
            
            String currentDirectory = System.getProperty("user.dir");
            Path to = Paths.get(currentDirectory, "\\src", path);
            Files.deleteIfExists(to);
        System.out.println(to);
        fillTable();
        createColumns();
        filterInput.clear();

    }

    @FXML
    private void selectTableRowListener(MouseEvent event) {
        if (event.getClickCount() >= 1) {

            id = movieTable.getItems().get(movieTable.getSelectionModel().getSelectedIndex()).getMovieId();
            path = movieTable.getItems().get(movieTable.getSelectionModel().getSelectedIndex()).getPoster();

        }
    }

}
