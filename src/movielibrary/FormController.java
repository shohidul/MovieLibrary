/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jdbc.Genre;
import jdbc.HibernateDao;
import jdbc.Movie;
import jdbc.MovieGenre;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Rating;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class FormController implements Initializable {

    @FXML
    public AnchorPane formroot;
    @FXML
    private Button closeForm;
    @FXML
    private Button closeForm1;
    @FXML
    private Button closeForm2;
    @FXML
    private JFXButton attatchPosterBtn;
    @FXML
    private ImageView posterView;
    private File file;
    private BufferedImage bufferedImage;
    private WritableImage image;
    private String imagePath;
    private String filePath;
    @FXML
    private TextField titleField;
    @FXML
    private TextField runTimeField;
    @FXML
    private TextField releaseYearField;
    @FXML
    private TextField offlineField;
    @FXML
    private TextField onlineField;
    @FXML
    private TextArea descTextArea;
    @FXML
    private JFXRadioButton watchedCheckBox;
    @FXML
    private JFXRadioButton notWatchedCheckBox;
    @FXML
    private CheckBox yesCheckBox;
    @FXML
    private JFXButton saveToDbBtn;
    @FXML
    private Button movieTableBtn;
    @FXML
    private StackPane formStackPane;
    @FXML
    private AnchorPane movieTabAnchor;
    int count = 0;
    @FXML
    private AnchorPane movieTabAnchorInside;
    @FXML
    private Pane ratinghbox;
    @FXML
    private Rating rating;
    @FXML
    private TextField ratingField;
    @FXML
    private ToggleGroup watchRadioGroup;
    @FXML
    private CheckComboBox<String> genreComboBox;
    private String latestFilePath;
    @FXML
    private Button offlineLinkBtn;
    @FXML
    private JFXButton onlineLinkBtn;
    @FXML
    private Button resetFormBtn;
    private byte[] posterImage;
    private String databaseDirectory;
    @FXML
    private TextField idField;
    @FXML
    private JFXButton generateIDBtn;
    ObservableList<String> checkedItems;
    private String str;
    private int num;
    private String allMovieGenres;
    private String saveBtnCondition = "insert";
    private Date databaseAddedDate;
    private String oldImagePath;
    @FXML
    private TextField onlineTrailerField;
    @FXML
    private JFXButton onlineTrailerBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> genreList = FXCollections.observableArrayList("Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary",
                "Drama", "Family", "Fantasy", "Horror", "Mystery", "Romance", "Sci-Fi", "Thriler");
        genreComboBox.getItems().addAll(genreList);

        genreComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                checkedItems = FXCollections.observableArrayList(genreComboBox.getCheckModel().getCheckedItems());
            }
        });

        watchedCheckBox.setUserData("Watched");
        notWatchedCheckBox.setUserData("Not Watched");
//        System.out.println(checkedItems.size());
    }

    @FXML
    private void closeFormAction(ActionEvent event) {
        formroot.toBack();
//      System.out.println("Form to back");
    }

    @FXML
    private void attatchPosterAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG (Joint Photographic Group)", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG (Portable Network Graphics)", "*.png")
        );

        fileChooser.setTitle("Choise a Image File");

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file);
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            posterView.setImage(image);
            imagePath = file.getAbsolutePath();
            System.out.println(imagePath);
        }

    }

    @FXML
    private void ratingOnClick(MouseEvent event) {

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        Double d = rating.getRating() * 2;
        ratingField.setText(df.format(d));
    }

    @FXML
    private void offlineLinkBtnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("File type(*.mp4)", "*.mp4"),
                new FileChooser.ExtensionFilter("(*.mkv)", "*.mkv"),
                new FileChooser.ExtensionFilter("(*.avi)", "*.avi")
        );

        fileChooser.setTitle("Choise a Video File");

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
//            System.out.println(file);
            filePath = file.getAbsolutePath();

            offlineField.setText(filePath);
        }
    }

    @FXML
    private void onlineLinkBtnAction(ActionEvent event) {
        String result = "";
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String contents = clipboard.getString();

        onlineField.setText(contents);

    }

    @FXML
    private void saveToDbBtnAction(ActionEvent event) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Confirmation to " + saveBtnCondition));
        content.setBody(new Text("Do you want to " + saveBtnCondition + " the data? "
                + "If your answer is yes press Okay button "
                + "else click outside of this box."));
        JFXDialog dialog = new JFXDialog(formStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setStyle("-fx-background-color: #094AAB; -fx-text-fill: #fff;");
        final Glow glow = new Glow();
        glow.setLevel(0.69);
        button.setEffect(glow);
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (saveBtnCondition.equals("update")) {

                    ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
                    HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
                    String favouriteStatus;
                    if (yesCheckBox.isSelected()) {
                        favouriteStatus = "Yes";
                    } else {
                        favouriteStatus = "Not Yet";
                    }

                    // Copy new poster
                    Path from = Paths.get(imagePath);
                    String currentDirectory = System.getProperty("user.dir");
                    databaseDirectory = "posters/" + from.getFileName().toString();
                    Path to = Paths.get(currentDirectory, "\\src\\posters", from.getFileName().toString());

                    CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                    };
                    try {
                        Files.copy(from, to, options);
                        System.out.println("Poster copied");
                    } catch (IOException e) {
                        System.err.println(e);
                        System.out.println("Poster not copied");

                    }
                    // Delete old poster
                    if (imagePath.equals(oldImagePath)) {
                        System.out.println("Same poster");
                    } else {
                        currentDirectory = System.getProperty("user.dir");
                        Path p = Paths.get(currentDirectory, "\\src", oldImagePath);
                        try {
                            Files.deleteIfExists(p);
                            System.out.println("Poster deleted");
                        } catch (IOException ex) {
                            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (checkedItems.size() == 1) {
                        allMovieGenres = checkedItems.get(0);
                        System.out.println(allMovieGenres);
                    } else if (checkedItems.size() == 2) {
                        allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1);
                        System.out.println(allMovieGenres);
                    } else if (checkedItems.size() == 3) {
                        allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1) + "/" + checkedItems.get(2);
                        System.out.println(allMovieGenres);
                    } else if (checkedItems.size() == 4) {
                        allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1) + "/" + checkedItems.get(2) + "/" + checkedItems.get(3);
                        System.out.println(allMovieGenres);
                    }
                    hd.updateMovie(new Movie(idField.getText(), titleField.getText(), descTextArea.getText(), runTimeField.getText(),
                            Integer.parseInt(releaseYearField.getText()), Double.parseDouble(ratingField.getText()),
                            watchRadioGroup.getSelectedToggle().getUserData().toString(), favouriteStatus, databaseDirectory,
                            offlineField.getText(), onlineField.getText(), onlineTrailerField.getText(), databaseAddedDate, allMovieGenres));

                    List<MovieGenre> list = hd.getAllMovieGenre(idField.getText());
                    for (MovieGenre e : list) {
                        String allId = String.valueOf(e.getId());
                        String[] arrayId = allId.split(" ");
                        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arrayId));
                        hd.deleteMovieGenre(Integer.parseInt(arrayList.get(0)));
                    }

                    if (checkedItems.size() == 1) {
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                    } else if (checkedItems.size() == 2) {
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                    } else if (checkedItems.size() == 3) {
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(2)), new Movie(idField.getText()), idField.getText()));
                    } else if (checkedItems.size() == 4) {
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(2)), new Movie(idField.getText()), idField.getText()));
                        hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(3)), new Movie(idField.getText()), idField.getText()));
                    }
                    resetMethod();
                    dialog.close();
                } else {

                    if (checkedItems == null || checkedItems.size() < 1 || checkedItems.size() > 4) {
                        System.out.println("Genre items should be 1-4.");
                        return;
                    } else {

                        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
                        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
                        String favouriteStatus;
                        if (yesCheckBox.isSelected()) {
                            favouriteStatus = "Yes";
                        } else {
                            favouriteStatus = "Not Yet";
                        }

                        Path from = Paths.get(imagePath);
                        String currentDirectory = System.getProperty("user.dir");
                        databaseDirectory = "posters/" + from.getFileName().toString();
                        Path to = Paths.get(currentDirectory, "\\src\\posters", from.getFileName().toString());

                        CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                        };
                        try {
                            Files.copy(from, to, options);
                            System.out.println("Poster copied");
                        } catch (IOException e) {
                            System.err.println(e);
                            System.out.println("Poster not copied");

                        }
                        if (checkedItems.size() == 1) {
                            allMovieGenres = checkedItems.get(0);
                            System.out.println(allMovieGenres);
                        } else if (checkedItems.size() == 2) {
                            allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1);
                            System.out.println(allMovieGenres);
                        } else if (checkedItems.size() == 3) {
                            allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1) + "/" + checkedItems.get(2);
                            System.out.println(allMovieGenres);
                        } else if (checkedItems.size() == 4) {
                            allMovieGenres = checkedItems.get(0) + "/" + checkedItems.get(1) + "/" + checkedItems.get(2) + "/" + checkedItems.get(3);
                            System.out.println(allMovieGenres);
                        }

                        Date date = new Date();
                        hd.saveMovies(new Movie(idField.getText(), titleField.getText(), descTextArea.getText(), runTimeField.getText(),
                                Integer.parseInt(releaseYearField.getText()), Double.parseDouble(ratingField.getText()),
                                watchRadioGroup.getSelectedToggle().getUserData().toString(), favouriteStatus, databaseDirectory,
                                offlineField.getText(), onlineField.getText(), onlineTrailerField.getText(), date, allMovieGenres));

                        if (checkedItems.size() == 1) {
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                        } else if (checkedItems.size() == 2) {
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                        } else if (checkedItems.size() == 3) {
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(2)), new Movie(idField.getText()), idField.getText()));
                        } else if (checkedItems.size() == 4) {
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(0)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(1)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(2)), new Movie(idField.getText()), idField.getText()));
                            hd.saveMovies_Genres(new MovieGenre(new Genre(checkedItems.get(3)), new Movie(idField.getText()), idField.getText()));
                        }
                        resetMethod();
                        dialog.close();
                    }
                }
            }
        });
        content.setActions(button);
        dialog.show();
    }

    @FXML
    private void resetFormBtnAction(ActionEvent event) {
        resetMethod();

    }

    public void resetMethod() {
        titleField.clear();
        descTextArea.clear();
        runTimeField.clear();
        releaseYearField.clear();
        watchRadioGroup.selectToggle(notWatchedCheckBox);
        yesCheckBox.setSelected(false);
        posterView.setImage(null);
        rating.setRating(2.0);
        ratingField.clear();
        offlineField.clear();
        onlineField.clear();
        onlineTrailerField.clear();
        genreComboBox.getCheckModel().clearChecks();
    }

    @FXML
    private void movieTableBtnClickAction(MouseEvent e) {
        if (e.getClickCount() >= 1) {
            count += 1;
//                System.out.println(count);
        } else {
        }
        if (count <= 1) {
            AnchorPane form;
            try {
                form = FXMLLoader.load(getClass().getResource("MovieTable.fxml"));
                formStackPane.getChildren().add(form);
            } catch (IOException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            movieTabAnchorInside.toBack();

        }
    }

    @FXML
    private void generateIDBtnAction(ActionEvent event) {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");

        HibernateDao hd = (HibernateDao) context.getBean("hibernateDao");
        List<Movie> list1 = hd.getSingleValue(idField.getText());

        if (list1.size() > 0) {
            for (Movie e : list1) {

                titleField.setText(e.getTitle());
                descTextArea.setText(e.getDescription());
                runTimeField.setText(e.getRunningTime());
                releaseYearField.setText(String.valueOf(e.getYear()));
                ratingField.setText(String.valueOf(e.getRating()));
                rating.setRating(e.getRating() / 2);
                if (e.getWatchStatus().equals("Watched")) {
                    watchedCheckBox.setSelected(true);
                } else {
                    notWatchedCheckBox.setSelected(true);
                }
                if (e.getFavourite().equals("Yes")) {
                    yesCheckBox.setSelected(true);
                } else {
                    yesCheckBox.setSelected(false);
                }
                posterView.setImage(new Image(e.getPoster()));
                oldImagePath = e.getPoster();
                imagePath = e.getPoster();
                offlineField.setText(e.getOfflineLink());
                onlineField.setText(e.getOnlineLink());
                onlineTrailerField.setText(e.getTrailerLink());
                databaseAddedDate = e.getAddedDate();

                String selectedValues = e.getAllMovieGenres();
                System.out.println("selected " + selectedValues);
                String[] array = selectedValues.split("/");
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

                if (arrayList.size() == 1) {
                    genreComboBox.getCheckModel().clearChecks();
                    genreComboBox.getCheckModel().check(arrayList.get(0));
                } else if (arrayList.size() == 2) {
                    genreComboBox.getCheckModel().clearChecks();
                    genreComboBox.getCheckModel().check(arrayList.get(0));
                    genreComboBox.getCheckModel().check(arrayList.get(1));
                } else if (arrayList.size() == 3) {
                    genreComboBox.getCheckModel().clearChecks();
                    genreComboBox.getCheckModel().check(arrayList.get(0));
                    genreComboBox.getCheckModel().check(arrayList.get(1));
                    genreComboBox.getCheckModel().check(arrayList.get(2));
                } else if (arrayList.size() == 4) {
                    genreComboBox.getCheckModel().clearChecks();
                    genreComboBox.getCheckModel().check(arrayList.get(0));
                    genreComboBox.getCheckModel().check(arrayList.get(1));
                    genreComboBox.getCheckModel().check(arrayList.get(2));
                    genreComboBox.getCheckModel().check(arrayList.get(3));
                }
            }

            saveBtnCondition = "update";
            System.out.println("checked 2" + checkedItems);

        } else {
            List<Movie> list2 = hd.generateMovieId();
            for (Movie s : list2) {

                str = s.getMovieId();
                num = Integer.parseInt(str.substring(3));
                num++;
                if (num < 10) {
                    str = "Mov00" + num;
                } else if (num < 100) {
                    str = "Mov0" + num;
                } else if (num < 1000) {
                    str = "Mov" + num;
                }
                idField.setText(str);
            }
            resetMethod();
            saveBtnCondition = "insert";
        }
        System.out.println(saveBtnCondition);
    }

    

    @FXML
    private void onlineTrailerBtnAction(ActionEvent event) {
        String result = "";
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String contents = clipboard.getString();

        onlineTrailerField.setText(contents);
    }

}
