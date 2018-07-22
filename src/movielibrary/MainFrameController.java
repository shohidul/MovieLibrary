/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import impl.org.controlsfx.skin.BreadCrumbBarSkin;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jdbc.HibernateDao;
import jdbc.Movie;
import org.controlsfx.control.Rating;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Hridoy
 */
public class MainFrameController implements Initializable {

    @FXML
    public HBox hbox;
    @FXML
    private BorderPane root;
    private ImageView closeIcon;
    @FXML
    private Button fullscreen;
    @FXML
    private Button minimize;
    @FXML
    private Button maximize;
    @FXML
    private Button close;

    Stage stage;
    Rectangle2D rec2;
    Double w, h;

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label label;
    @FXML
    private HBox hbox2;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    public AnchorPane mainAnchor;
    @FXML
    private Button addContent;
    @FXML
    private StackPane stackPane;
    int count = 0;
    int countInfo = 0;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private Button moviesBtn;
    @FXML
    private StackPane stack4DialogLayout;
    VBox vbox1;
    @FXML
    private Button infoBtn;
    AnchorPane form;
    AnchorPane info;
    @FXML
    private JFXComboBox<String> genreComboFilter;
    @FXML
    private JFXComboBox<String> sortByComboFilter;
    @FXML
    private JFXTextField searchFieldFilter;
    @FXML
    private Button watchedBtnFilter;
    @FXML
    private Button favouriteBtnFilter;
    private ObservableList<Movie> mvelist;
    ObservableList<Movie> globalMveList;
    ObservableList<Movie> sortedList;
    HibernateDao hd;
    private boolean seen = true;
    private ObservableList<Movie> updateMvelist;
    Duration duration;
    JFXSlider timeSlider;
    JFXSlider volumeSlider;
    Label playTime;
    MediaPlayer mediaPlayer;
    Duration currentTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMovieList();

        ObservableList<String> genreList = FXCollections.observableArrayList("All", "Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary",
                "Drama", "Family", "Fantasy", "Horror", "Mystery", "Romance", "Sci-Fi", "Thriler");
        genreComboFilter.setItems(genreList);
        genreComboFilter.getSelectionModel().selectFirst();
        ObservableList<String> sortBy = FXCollections.observableArrayList("Last Added", "Year", "Title", "Rating");
        sortByComboFilter.setItems(sortBy);
        sortByComboFilter.getSelectionModel().selectFirst();

        doubleClick();
        rec2 = Screen.getPrimary().getVisualBounds();
        w = 0.1;
        h = 0.1;
        Platform.runLater(() -> {
            stage = (Stage) maximize.getScene().getWindow();
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");

        });

        makeDragable(true);
        escEvent();
        loadMovies();
        moviesBtn.setStyle("-fx-background-color: null; -fx-background-image:url(img/movies.png); -fx-background-repeat:no-repeat;");
        searchFieldFilter.setStyle("-fx-text-fill:white;");
        searchFieldFilter.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterMovieList((String) oldValue, (String) newValue);
            }

        });

    }

    public void loadMovieList() throws BeansException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        hd = (HibernateDao) context.getBean("hibernateDao");
        mvelist = FXCollections.observableArrayList(hd.sortByLastAdded());
        globalMveList = mvelist;
    }

    public void filterMovieList(String oldValue, String newValue) {
        ObservableList<Movie> filteredListIncrease = FXCollections.observableArrayList();
        ObservableList<Movie> filteredListDecrese = FXCollections.observableArrayList();
        if (searchFieldFilter.equals("") || newValue.equals("")) {
            mainFlowPane.getChildren().clear();
            globalMveList = mvelist;
            loadMovies();
        } else if (newValue.length() < oldValue.length()) {
            newValue = newValue.toUpperCase();
            for (Movie movie : mvelist) {
                String filterName = movie.getTitle();
                String filterId = movie.getMovieId();
                String filterGenre = movie.getAllMovieGenres();
                if (filterName.toUpperCase().contains(newValue) || filterId.toUpperCase().contains(newValue) || filterGenre.toUpperCase().contains(newValue)) {
                    filteredListDecrese.add(movie);
                }
            }
            mainFlowPane.getChildren().clear();
            globalMveList = filteredListDecrese;
            loadMovies();

        } else {
            newValue = newValue.toUpperCase();
            for (Movie movie : mvelist) {
                String filterName = movie.getTitle();
                String filterId = movie.getMovieId();
                String filterGenre = movie.getAllMovieGenres();
                if (filterName.toUpperCase().contains(newValue) || filterId.toUpperCase().contains(newValue) || filterGenre.toUpperCase().contains(newValue)) {
                    filteredListIncrease.add(movie);

                }
            }

            mainFlowPane.getChildren().clear();
            globalMveList = filteredListIncrease;
            loadMovies();

        }
    }

    @FXML
    private void aksifullscreen(ActionEvent event) {
        stage = (Stage) fullscreen.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
            maximize.setDisable(false);
            makeDragable(true);
        } else {
            stage.setFullScreen(true);
            maximize.setDisable(true);
            makeDragable(false);

        }
    }

    @FXML
    private void aksiminimize(ActionEvent event) {
        stage = (Stage) minimize.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        } else {
            stage.setIconified(true);
        }
    }

    @FXML
    private void aksiMaximized(ActionEvent event) {
        stage = (Stage) maximize.getScene().getWindow();
        if (stage.isMaximized()) {
            if (w == rec2.getWidth() && h == rec2.getHeight()) {
                stage.setMaximized(false);
                stage.setHeight(600);
                stage.setWidth(900);
                stage.centerOnScreen();
                maximize.getStyleClass().remove("decoration-button-restore");
//                resize.setVisible(true);
            } else {
                stage.setMaximized(false);
                maximize.getStyleClass().remove("decoration-button-restore");
//                resize.setVisible(true);
            }

        } else {
            stage.setMaximized(true);
            stage.setHeight(rec2.getHeight());
            maximize.getStyleClass().add("decoration-button-restore");
//            resize.setVisible(false);
        }
    }

    @FXML
    private void aksiClose(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void doubleClick() {
        hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    stage = (Stage) maximize.getScene().getWindow();
                    if (stage.isMaximized()) {
                        if (w == rec2.getWidth() && h == rec2.getHeight()) {
                            stage.setMaximized(false);
                            stage.setHeight(600);
                            stage.setWidth(900);
                            stage.centerOnScreen();
                            maximize.getStyleClass().remove("decoration-button-restore");
                        } else {
                            stage.setMaximized(false);
                            maximize.getStyleClass().remove("decoration-button-restore");
                        }

                    } else {
                        stage.setMaximized(true);
                        stage.setHeight(rec2.getHeight());
                        maximize.getStyleClass().add("decoration-button-restore");
                    }

                }
            }
        });
    }

    private boolean makeDragable(boolean b) {
        if (b) {
            label.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            label.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
        } else {
            label.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            label.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
        }
        return b;
    }

    private void escEvent() {
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
//                    System.out.println("Key Pressed: " + ke.getCode());
                    makeDragable(true);
                    maximize.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void addContent_MouseClickCounter(MouseEvent e) {
        if (e.getClickCount() >= 1) {
            count += 1;
//                System.out.println(count);
        } else {
        }

        if (count <= 1) {

            try {
                form = FXMLLoader.load(getClass().getResource("Form.fxml"));
                stackPane.getChildren().add(form);
                stage.setMaximized(true);
            } catch (IOException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Form added");
        } else {
//            mainAnchor.toBack();
            form.toFront();
            stage.setMaximized(true);
            System.out.println("Form to Front");
        }
    }

    public void loadMovies() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
//        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
//        ObservableList<Movie> loadmvelist = FXCollections.observableArrayList(hd.getMovies());
//        globalMveList = loadmvelist;
        for (Movie e : globalMveList) {
            String posters = e.getPoster();
            String[] strValues = posters.split(" ");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(strValues));
            for (int i = 0; i < arrayList.size(); i++) {

                Image image = new Image(e.getPoster());

                ImageView imageView1 = new ImageView();
                vbox1 = new VBox();
                Label title1 = new Label();
                title1.setStyle("-fx-text-fill: #fff; -fx-font-weight: bold; -fx-padding: 1px, 0px, 1px, 0px; ");
                Label year1 = new Label();
                year1.setStyle("-fx-text-fill: #8B8F93; -fx-font-size: 11px;");
                title1.setText(e.getTitle());
                year1.setText(String.valueOf(e.getYear()));
                vbox1.setPrefSize(135, 237);
                imageView1.setFitWidth(135);
                imageView1.setFitHeight(200);

                imageView1.setImage(image);
                imageView1.setOnMouseEntered((event) -> {
                    imageView1.setEffect(new Glow(.5));
                });
                imageView1.setOnMouseExited((event) -> {
                    imageView1.setEffect(null);
                });
                vbox1.getChildren().addAll(imageView1, title1, year1);
                imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    private boolean p;

                    @Override
                    public void handle(MouseEvent t) {
//                pic.getImage().get;
                        AnchorPane anchorPane2 = new AnchorPane();
                        AnchorPane anchorPaneInside = new AnchorPane();
                        AnchorPane blurPane = new AnchorPane();
                        AnchorPane darkTransparent = new AnchorPane();
                        darkTransparent.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
                        anchorPane2.setMaxSize(1366, 700);
                        blurPane.setStyle("-fx-background-image:url(" + e.getPoster() + "); -fx-background-position: center center;  -fx-background-size: cover;");
                        anchorPane2.setStyle("-fx-background-image:url(" + e.getPoster() + "); -fx-background-position: center center;  -fx-background-size: cover;");
                        blurPane.setEffect(new GaussianBlur(30));
                        ImageView imageView2 = new ImageView(e.getPoster());
                        imageView2.setFitHeight(552);
                        imageView2.setFitWidth(370);
                        AnchorPane.setTopAnchor(imageView2, 55.0);
                        AnchorPane.setLeftAnchor(imageView2, 95.0);
//                        AnchorPane.setBottomAnchor(imageView2, 90.0);
                        Label title2 = new Label();
                        title2.setText(e.getTitle());
                        title2.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 38px; ");
                        title2.setMaxSize(756, 63);
                        AnchorPane.setTopAnchor(title2, 40.0);
                        AnchorPane.setLeftAnchor(title2, 477.0);

                        Label year2 = new Label();
                        year2.setText(String.valueOf(e.getYear()));
                        year2.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px; ");
                        year2.setMaxSize(35, 17);
                        AnchorPane.setTopAnchor(year2, 106.0);
                        AnchorPane.setLeftAnchor(year2, 477.0);

                        Label dot = new Label();
                        dot.setText(".");
                        dot.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 18px; ");
                        dot.setMaxSize(5, 17);
                        AnchorPane.setTopAnchor(dot, 97.0);
                        AnchorPane.setLeftAnchor(dot, 522.0);

                        Label runTime = new Label();
                        runTime.setText(e.getRunningTime());
                        runTime.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px; ");
                        runTime.setMaxSize(61, 17);
                        AnchorPane.setTopAnchor(runTime, 106.0);
                        AnchorPane.setLeftAnchor(runTime, 544.0);

                        Label dot2 = new Label();
                        dot2.setText(".");
                        dot2.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 18px; ");
                        dot2.setMaxSize(5, 17);
                        AnchorPane.setTopAnchor(dot2, 97.0);
                        AnchorPane.setLeftAnchor(dot2, 608.0);

                        Label genres = new Label();
                        genres.setText(e.getAllMovieGenres());
                        genres.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px; ");
                        genres.setMaxSize(300, 17);
                        AnchorPane.setTopAnchor(genres, 106.0);
                        AnchorPane.setLeftAnchor(genres, 626.0);

                        Label ratingLabel = new Label();
                        ratingLabel.setText(String.valueOf("Rating " + e.getRating()) + "/10");
                        ratingLabel.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px; ");
                        ratingLabel.setMaxSize(80, 17);
                        AnchorPane.setTopAnchor(ratingLabel, 106.0);
                        AnchorPane.setLeftAnchor(ratingLabel, 885.0);

                        Double d = new Double(e.getRating());
                        int i = d.intValue();
                        Rating rating = new Rating(5, i / 2);
                        rating.setPartialRating(true);
                        AnchorPane.setTopAnchor(rating, 95.0);
                        AnchorPane.setLeftAnchor(rating, 975.0);

                        Label descLabel = new Label();
                        descLabel.setText(e.getDescription());
                        descLabel.setWrapText(true);
                        descLabel.setStyle("-fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px; ");
                        descLabel.setMaxSize(668, 220);
                        AnchorPane.setTopAnchor(descLabel, 145.0);
                        AnchorPane.setLeftAnchor(descLabel, 477.0);

                        JFXButton watchOffline = new JFXButton("Watch Offline");
                        watchOffline.setStyle("-fx-background-color:  #094AAB; -fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
                        watchOffline.setMinSize(130, 25);
                        watchOffline.setEffect(new Glow(0.69));
                        watchOffline.setOnAction((event) -> {
                            AnchorPane playerAnchor = new AnchorPane();
                            playerAnchor.setMaxSize(1366, 736);
                            playerAnchor.setStyle("-fx-background-color:  #16171A");
                            String MEDIA_URL = new File(e.getOfflineLink()).toURI().toString();
                            Media media = new Media(MEDIA_URL);
                            mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);

                            MediaView mediaView = new MediaView(mediaPlayer);
                            mediaView.setFitWidth(1366);
                            AnchorPane.setLeftAnchor(mediaView, 0.0);
                            AnchorPane.setRightAnchor(mediaView, 0.0);
                            AnchorPane.setTopAnchor(mediaView, 60.0);
                            AnchorPane toolbar = new AnchorPane();
                            toolbar.setMinHeight(100);
                            toolbar.toFront();
//                            toolbar.setMaxWidth(50);
//                            toolbar.setStyle("-fx-background-color:  #333;");
                            toolbar.setOpacity(0);
                            toolbar.setOnMouseEntered((eventx) -> {
                                toolbar.setOpacity(1);
                            });
                            toolbar.setOnMouseExited((evo) -> {
                                toolbar.setOpacity(0);
                            });
                            AnchorPane.setLeftAnchor(toolbar, 0.0);
                            AnchorPane.setRightAnchor(toolbar, 0.0);
                            AnchorPane.setBottomAnchor(toolbar, 0.0);
                            Button backward = new Button();
                            backward.setMinSize(50, 50);
                            backward.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/backward.png); -fx-background-repeat:no-repeat;");
                            backward.setOnAction((e) -> {
                                currentTime = currentTime.add(Duration.seconds(5));
                            });
                            Button sound = new Button();
                            sound.setMinSize(50, 50);
                            sound.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/sound.png); -fx-background-repeat:no-repeat;");
                            AnchorPane.setLeftAnchor(sound, 10.0);
                            AnchorPane.setTopAnchor(sound, 30.0);
                            
                            Button play = new Button();
                            play.setMinSize(50, 50);
                            play.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/pause.png); -fx-background-repeat:no-repeat;");

                            play.setOnMouseClicked((evendst) -> {
                                if (p) {
                                    mediaPlayer.pause();
                                    play.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/play.png); -fx-background-repeat:no-repeat;");
                                    p = false;
                                } else {
                                    mediaPlayer.play();
                                    play.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/pause.png); -fx-background-repeat:no-repeat;");
                                    p = true;
                                }
                            });
                            Button forward = new Button();
                            forward.setMinSize(50, 50);
                            forward.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/forward.png); -fx-background-repeat:no-repeat;");
                            forward.setOnAction((e) -> {
                                currentTime = currentTime.add(Duration.seconds(5));
                            });
                            HBox middleBtnSet = new HBox(backward, play, forward);
                            AnchorPane.setLeftAnchor(middleBtnSet, 608.0);
                            AnchorPane.setTopAnchor(middleBtnSet, 30.0);

                            HBox sliderBox = new HBox();
                            AnchorPane.setLeftAnchor(sliderBox, 10.0);
                            AnchorPane.setRightAnchor(sliderBox, 10.0);
                            AnchorPane.setTopAnchor(sliderBox, 0.0);

                            mediaPlayer.setOnReady(new Runnable() {
                                public void run() {
                                    duration = mediaPlayer.getMedia().getDuration();
                                    updateValues();
                                }
                            });

                            volumeSlider = new JFXSlider();
                            volumeSlider.setPrefWidth(100);
                            volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
                            volumeSlider.setMinWidth(30);
                            AnchorPane.setLeftAnchor(volumeSlider, 65.0);
                            AnchorPane.setTopAnchor(volumeSlider, 48.0);
                            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                                public void invalidated(Observable ov) {
                                    if (volumeSlider.isValueChanging()) {
                                        mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                                    }
                                }
                            });
                            playTime = new Label();
                            playTime.setPrefWidth(130);
                            playTime.setMinWidth(50);
                            playTime.setStyle("-fx-text-fill: white;");
                            AnchorPane.setLeftAnchor(playTime, 10.0);
                            AnchorPane.setTopAnchor(playTime, 20.0);

                            timeSlider = new JFXSlider();
                            HBox.setHgrow(timeSlider, Priority.ALWAYS);
                            timeSlider.setMinWidth(50);
                            timeSlider.setMaxWidth(Double.MAX_VALUE);
                            timeSlider.valueProperty().addListener(new InvalidationListener() {
                                @Override
                                public void invalidated(Observable ov) {
                                    if (timeSlider.isValueChanging()) {
                                        // multiply duration by percentage calculated by slider position
                                        if (duration != null) {
                                            mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                                        }
                                        updateValues();
                                    }
                                }

                            });

                            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                                public void invalidated(Observable ov) {
                                    updateValues();
                                }
                            });

                            sliderBox.getChildren().add(timeSlider);

                            toolbar.getChildren().addAll(middleBtnSet, sliderBox, volumeSlider, playTime, sound);

                            Button playerClose = new Button();
                            playerClose.setMinSize(25, 25);
                            playerClose.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/closeForm.png); -fx-background-repeat:no-repeat;");
                            AnchorPane.setTopAnchor(playerClose, 10.0);
                            AnchorPane.setRightAnchor(playerClose, 16.0);
                            playerClose.setOnAction((ev) -> {
//                            anchorPane2.toBack();
                                stackPane.getChildren().remove(playerAnchor);
                                mediaPlayer.dispose();
                            });

                            playerAnchor.getChildren().addAll(mediaView, playerClose, toolbar);
                            stackPane.getChildren().add(playerAnchor);

                        });
                        AnchorPane.setTopAnchor(watchOffline, 584.0);
                        AnchorPane.setLeftAnchor(watchOffline, 476.0);

                        String url = e.getOnlineLink();
                        JFXButton watchOnline = new JFXButton("Watch Online");
                        watchOnline.setStyle("-fx-background-color:  #094AAB; -fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
                        watchOnline.setMinSize(130, 25);
                        watchOnline.setEffect(new Glow(0.69));
                        watchOnline.setOnAction((event) -> {

                            try {
                                URI u = new URI(url);
                                java.awt.Desktop.getDesktop().browse(u);
                            } catch (URISyntaxException ex) {
                                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        AnchorPane.setTopAnchor(watchOnline, 584.0);
                        AnchorPane.setLeftAnchor(watchOnline, 625.0);

                        String urlTrailer = e.getTrailerLink();
                        JFXButton watchTrailer = new JFXButton("Watch Trailer");
                        watchTrailer.setStyle("-fx-background-color:  #094AAB; -fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
                        watchTrailer.setMinSize(130, 25);
                        watchTrailer.setEffect(new Glow(0.69));
                        watchTrailer.setOnAction((event) -> {
                            AnchorPane trailerAnchor = new AnchorPane();
                            trailerAnchor.setMaxSize(1366, 736);
                            trailerAnchor.setStyle("-fx-background-color:  #16171A");

                            WebView webview = new WebView();
                            webview.getEngine().load(urlTrailer);
//                            webview.setPrefSize(1100, 650);

                            AnchorPane.setLeftAnchor(webview, 0.0);
                            AnchorPane.setRightAnchor(webview, 0.0);
                            AnchorPane.setTopAnchor(webview, 0.0);
                            AnchorPane.setBottomAnchor(webview, 0.0);

                            Button trailerplayerClose = new Button();
                            trailerplayerClose.setMinSize(25, 25);
                            trailerplayerClose.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/closeForm.png); -fx-background-repeat:no-repeat;");
                            AnchorPane.setTopAnchor(trailerplayerClose, 10.0);
                            AnchorPane.setRightAnchor(trailerplayerClose, 16.0);
                            trailerplayerClose.setOnAction((ev) -> {
//                            anchorPane2.toBack();
                                stackPane.getChildren().remove(trailerAnchor);
                                webview.getEngine().load(null);
                            });

                            trailerAnchor.getChildren().addAll(webview, trailerplayerClose);
                            stackPane.getChildren().add(trailerAnchor);

                        });
                        AnchorPane.setTopAnchor(watchTrailer, 584.0);
                        AnchorPane.setLeftAnchor(watchTrailer, 774.0);

                        String movieId = e.getMovieId();
                        ImageView fav = new ImageView();
                        List<Movie> li = hd.getSingleValue(movieId);
                        for (Movie s : li) {
                            String f = s.getFavourite();

                            if (f.equals("Yes")) {
                                fav.setImage(new Image("img/like-selected.png"));
                            } else {
                                fav.setImage(new Image("img/like.png"));
                            }
                        }
                        fav.setFitHeight(20);
                        fav.setFitWidth(20);
                        Button favourite = new Button("Add to favourites", fav);
                        favourite.setId("favouriteBtn");
                        favourite.setContentDisplay(ContentDisplay.LEFT);
                        favourite.setMinSize(65, 25);
                        favourite.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-repeat:no-repeat; -fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
                        AnchorPane.setTopAnchor(favourite, 545.0);
                        AnchorPane.setLeftAnchor(favourite, 470.0);
                        favourite.setOnAction((event) -> {
                            List<Movie> list = hd.getSingleValue(movieId);
                            for (Movie s : list) {
                                String f = s.getFavourite();
                                if (f.equals("Yes")) {
                                    hd.updateMovie(new Movie(movieId, e.getTitle(), e.getDescription(), e.getRunningTime(), e.getYear(), e.getRating(), s.getWatchStatus(), "Not Yet", e.getPoster(), e.getOfflineLink(), e.getOnlineLink(), e.getTrailerLink(), e.getAddedDate(), e.getAllMovieGenres()));
                                    fav.setImage(new Image("img/like.png"));
                                } else {
                                    hd.updateMovie(new Movie(movieId, e.getTitle(), e.getDescription(), e.getRunningTime(), e.getYear(), e.getRating(), s.getWatchStatus(), "Yes", e.getPoster(), e.getOfflineLink(), e.getOnlineLink(), e.getTrailerLink(), e.getAddedDate(), e.getAllMovieGenres()));
                                    fav.setImage(new Image("img/like-selected.png"));
                                }
                            }
                        });

                        ImageView fav2 = new ImageView();
                        Button seen = new Button("", fav2);
                        List<Movie> li2 = hd.getSingleValue(movieId);
                        for (Movie s : li2) {
                            String f = s.getWatchStatus();

                            if (f.equals("Watched")) {
                                fav2.setImage(new Image("img/seen.png"));
                                seen.setText("Seen");
                            } else {
                                fav2.setImage(new Image("img/eye.png"));
                                seen.setText("Not Seen");
                            }
                        }
                        fav2.setFitHeight(20);
                        fav2.setFitWidth(20);

                        seen.setId("seenBtn");
                        seen.setContentDisplay(ContentDisplay.LEFT);
                        seen.setMinSize(65, 25);
                        seen.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-repeat:no-repeat; -fx-text-fill: #fff; fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 12px;");
                        AnchorPane.setTopAnchor(seen, 545.0);
                        AnchorPane.setLeftAnchor(seen, 619.0);
                        seen.setOnAction((event) -> {
                            List<Movie> list = hd.getSingleValue(movieId);
                            for (Movie s : list) {
                                String f = s.getWatchStatus();
                                if (f.equals("Watched")) {
                                    hd.updateMovie(new Movie(movieId, e.getTitle(), e.getDescription(), e.getRunningTime(), e.getYear(), e.getRating(), "Not Watched", s.getFavourite(), e.getPoster(), e.getOfflineLink(), e.getOnlineLink(), e.getTrailerLink(), e.getAddedDate(), e.getAllMovieGenres()));
                                    fav2.setImage(new Image("img/eye.png"));
                                    seen.setText("Not Seen");
                                } else {
                                    hd.updateMovie(new Movie(movieId, e.getTitle(), e.getDescription(), e.getRunningTime(), e.getYear(), e.getRating(), "Watched", s.getFavourite(), e.getPoster(), e.getOfflineLink(), e.getOnlineLink(), e.getTrailerLink(), e.getAddedDate(), e.getAllMovieGenres()));
                                    fav2.setImage(new Image("img/seen.png"));
                                    seen.setText("Seen");
                                }
                            }
                        });

                        Button b = new Button();
                        b.setMinSize(25, 25);
                        b.setStyle("-fx-background-radius: 0; -fx-border-style: null; -fx-background-color: null; -fx-background-image:url(img/closeForm.png); -fx-background-repeat:no-repeat;");
                        AnchorPane.setTopAnchor(b, 10.0);
                        AnchorPane.setRightAnchor(b, 16.0);
                        b.setOnAction((event) -> {
//                            anchorPane2.toBack();
                            stack4DialogLayout.getChildren().remove(anchorPane2);
                        });

                        anchorPaneInside.getChildren().addAll(imageView2, title2, b, year2, dot, runTime, dot2, genres, ratingLabel, rating, descLabel, watchOffline, watchOnline, watchTrailer, favourite, seen);
                        AnchorPane.setTopAnchor(anchorPaneInside, 0.0);
                        AnchorPane.setLeftAnchor(anchorPaneInside, 0.0);
                        AnchorPane.setBottomAnchor(anchorPaneInside, 0.0);
                        AnchorPane.setRightAnchor(anchorPaneInside, 0.0);

                        AnchorPane.setTopAnchor(blurPane, 0.0);
                        AnchorPane.setLeftAnchor(blurPane, 0.0);
                        AnchorPane.setBottomAnchor(blurPane, 0.0);
                        AnchorPane.setRightAnchor(blurPane, 0.0);

                        AnchorPane.setTopAnchor(darkTransparent, 0.0);
                        AnchorPane.setLeftAnchor(darkTransparent, 0.0);
                        AnchorPane.setBottomAnchor(darkTransparent, 0.0);
                        AnchorPane.setRightAnchor(darkTransparent, 0.0);
                        anchorPane2.getChildren().addAll(blurPane, darkTransparent, anchorPaneInside);

                        stack4DialogLayout.getChildren().add(anchorPane2);
                    }
                });
                mainFlowPane.getChildren().add(vbox1);
            }
        }
    }

    ;

    
    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    currentTime = mediaPlayer.getCurrentTime();
//                    System.out.println("currentTime "+ currentTime);
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    @FXML
    private void moviesBtnAction(ActionEvent event) {
        genreComboFilter.getSelectionModel().selectFirst();
        sortByComboFilter.getSelectionModel().selectFirst();
        mainFlowPane.getChildren().clear();
        loadMovieList();
        loadMovies();
        moviesBtn.setStyle("-fx-background-color: null; -fx-background-image:url(img/movies.png); -fx-background-repeat:no-repeat;");
        watchedBtnFilter.setStyle(null);
        favouriteBtnFilter.setStyle(null);

        seen = true;

    }

    @FXML
    private void infoBtnClickedListener(MouseEvent e) {
        if (e.getClickCount() >= 1) {
            countInfo += 1;
        } else {
        }

        if (countInfo <= 1) {

            try {
                info = FXMLLoader.load(getClass().getResource("Info.fxml"));
                stackPane.getChildren().add(info);
                stage.setMaximized(true);
            } catch (IOException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Info added");
        } else {
            info.toFront();
            stage.setMaximized(true);
            System.out.println("Info to Front");
        }
    }

    @FXML
    private void watchedBtnFilterAction(ActionEvent event) {
        genreComboFilter.getSelectionModel().selectFirst();
        sortByComboFilter.getSelectionModel().selectFirst();

        if (seen) {
            ObservableList<Movie> allWatchedMovies = FXCollections.observableArrayList(hd.getAllWatchedMovies());
            mainFlowPane.getChildren().clear();
            globalMveList = allWatchedMovies;
            watchedBtnFilter.setStyle("-fx-background-image:url(img/seen.png);");
            seen = false;
        } else {
            ObservableList<Movie> allWatchedMovies = FXCollections.observableArrayList(hd.getAllNonWatchedMovies());
            mainFlowPane.getChildren().clear();
            globalMveList = allWatchedMovies;
            watchedBtnFilter.setStyle("-fx-background-image:url(img/eye.png);");
            seen = true;
        }

        loadMovies();
        moviesBtn.setStyle("-fx-background-color: null; -fx-background-repeat:no-repeat;");
        favouriteBtnFilter.setStyle(null);

    }

    @FXML
    private void favouriteBtnFilterAction(ActionEvent event) {
        genreComboFilter.getSelectionModel().selectFirst();
        sortByComboFilter.getSelectionModel().selectFirst();
        ObservableList<Movie> allFavouriteMovies = FXCollections.observableArrayList(hd.getAllFavoutiteMovies());
        mainFlowPane.getChildren().clear();
        globalMveList = allFavouriteMovies;
        loadMovies();
        moviesBtn.setStyle("-fx-background-color: null; -fx-background-repeat:no-repeat;");
        watchedBtnFilter.setStyle(null);
        favouriteBtnFilter.setStyle("-fx-background-image:url(img/like-selected.png);");
        seen = true;

    }

    @FXML
    private void sortByComboFilterAcrion(ActionEvent event) {
        String sortedValue = sortByComboFilter.getSelectionModel().getSelectedItem().toString();
        if (sortedValue.equals("Last Added")) {
            sortedList = FXCollections.observableArrayList(hd.sortByLastAdded());

        } else if (sortedValue.equals("Year")) {
            sortedList = FXCollections.observableArrayList(hd.sortByYear());
        } else if (sortedValue.equals("Title")) {
            sortedList = FXCollections.observableArrayList(hd.sortByTitle());
        } else {
            sortedList = FXCollections.observableArrayList(hd.sortByRating());
        }
        mainFlowPane.getChildren().clear();
        globalMveList = sortedList;
        loadMovies();
    }

    @FXML
    private void genreComboFilterAction(ActionEvent event) {
        String sortedValue = genreComboFilter.getSelectionModel().getSelectedItem().toString();
        if (sortedValue.equals("All")) {
            searchFieldFilter.setText("");
        } else {
            searchFieldFilter.setText(sortedValue);
        }

    }

}
