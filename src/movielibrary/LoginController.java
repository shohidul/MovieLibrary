/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Hridoy
 */
public class LoginController implements Initializable {

    private JFXButton loginBtn;
    private Button closeLogin;

    Stage stage;
    Scene scene;
    private Pane pane;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SplashScreen().start();
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.4), rootPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        
        fadeIn.play();
    }

    private void nextStage(String fxml, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage = new Stage();
        scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style/winDec.css").toExternalForm());
        stage.setScene(scene);
//        makeResizable(true);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    public boolean makeResizable(boolean r) {
        if (r) {
            ResizeListener listener = new ResizeListener();
            scene.setOnMouseMoved(listener);
            scene.setOnMousePressed(listener);
            scene.setOnMouseDragged(listener);
        } else {
            System.out.println("else");
        }
        return r;
    }

    public void loginAction(ActionEvent event) throws IOException {
        Stage current = (Stage) loginBtn.getScene().getWindow();
        nextStage(MovieLibrary.MainFrame, "Movie Library", true);
        current.hide();
    }

    private void closeLoginAction(ActionEvent event) {
        Stage current = (Stage) closeLogin.getScene().getWindow();
        current.close();
    }

    public class ResizeListener implements EventHandler<MouseEvent> {

        double dx;
        double dy;
        double deltaX;
        double deltaY;
        double border = 10;
        boolean moveH;
        boolean moveV;
        boolean resizeH = false;
        boolean resizeV = false;

        @Override
        public void handle(MouseEvent t) {
            if (MouseEvent.MOUSE_MOVED.equals(t.getEventType())) {
                if (t.getX() < border && t.getY() < border) {
                    scene.setCursor(Cursor.NW_RESIZE);
                    resizeH = true;
                    resizeV = true;
                    moveH = true;
                    moveV = true;
                } else if (t.getX() < border && t.getY() > scene.getHeight() - border) {
                    scene.setCursor(Cursor.SW_RESIZE);
                    resizeH = true;
                    resizeV = true;
                    moveH = true;
                    moveV = false;
                } else if (t.getX() > scene.getWidth() - border && t.getY() < border) {
                    scene.setCursor(Cursor.NE_RESIZE);
                    resizeH = true;
                    resizeV = true;
                    moveH = false;
                    moveV = true;
                } else if (t.getX() > scene.getWidth() - border && t.getY() > scene.getHeight() - border) {
                    scene.setCursor(Cursor.SE_RESIZE);
                    resizeH = true;
                    resizeV = true;
                    moveH = false;
                    moveV = false;
                } else if (t.getX() < border || t.getX() > scene.getWidth() - border) {
                    scene.setCursor(Cursor.E_RESIZE);
                    resizeH = true;
                    resizeV = false;
                    moveH = (t.getX() < border);
                    moveV = false;
                } else if (t.getY() < border || t.getY() > scene.getHeight() - border) {
                    scene.setCursor(Cursor.N_RESIZE);
                    resizeH = false;
                    resizeV = true;
                    moveH = false;
                    moveV = (t.getY() < border);
                } else {
                    scene.setCursor(Cursor.DEFAULT);
                    resizeH = false;
                    resizeV = false;
                    moveH = false;
                    moveV = false;
                }
            } else if (MouseEvent.MOUSE_PRESSED.equals(t.getEventType())) {
                dx = stage.getWidth() - t.getX();
                dy = stage.getHeight() - t.getY();
            } else if (MouseEvent.MOUSE_DRAGGED.equals(t.getEventType())) {
                if (resizeH && resizeV) {
                    if (stage.getWidth() <= 900) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            if (t.getX() < 0) {// if new > old, it's permitted
                                stage.setWidth(deltaX + stage.getWidth());
                                stage.setX(t.getScreenX());
                            }
                        } else {
                            if (t.getX() + dx - stage.getWidth() > 0) {
                                stage.setWidth(t.getX() + dx);
                            }
                        }
                    } else if (stage.getWidth() > 900) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            stage.setWidth(deltaX + stage.getWidth());
                            stage.setX(t.getScreenX());
                        } else {
                            stage.setWidth(t.getX() + dx);
                        }
                    }

                    if (stage.getHeight() <= 600) {
                        if (moveV) {
                            deltaY = stage.getY() - t.getScreenY();
                            if (t.getY() < 0) {// if new > old, it's permitted
                                stage.setHeight(deltaY + stage.getHeight());
                                stage.setY(t.getScreenY());
                            }
                        } else {
                            if (t.getY() + dy - stage.getHeight() > 0) {
                                stage.setHeight(t.getY() + dy);
                            }
                        }
                    } else if (stage.getHeight() > 600) {
                        if (moveV) {
                            deltaY = stage.getY() - t.getScreenY();
                            stage.setHeight(deltaY + stage.getHeight());
                            stage.setY(t.getScreenY());
                        } else {
                            stage.setHeight(t.getY() + dy);
                        }
                    }
                } else if (resizeH) {
                    if (stage.getWidth() <= 900) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            if (t.getX() < 0) {// if new > old, it's permitted
                                stage.setWidth(deltaX + stage.getWidth());
                                stage.setX(t.getScreenX());
                            }
                        } else {
                            if (t.getX() + dx - stage.getWidth() > 0) {
                                stage.setWidth(t.getX() + dx);
                            }
                        }
                    } else if (stage.getWidth() > 900) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            stage.setWidth(deltaX + stage.getWidth());
                            stage.setX(t.getScreenX());
                        } else {
                            stage.setWidth(t.getX() + dx);
                        }
                    }
                } else if (resizeV) {
                    if (stage.getHeight() <= 600) {
                        if (moveV) {
                            deltaY = stage.getY() - t.getScreenY();
                            if (t.getY() < 0) {// if new > old, it's permitted
                                stage.setHeight(deltaY + stage.getHeight());
                                stage.setY(t.getScreenY());
                            }
                        } else {
                            if (t.getY() + dy - stage.getHeight() > 0) {
                                stage.setHeight(t.getY() + dy);
                            }
                        }
                    } else if (stage.getHeight() > 600) {
                        if (moveV) {
                            deltaY = stage.getY() - t.getScreenY();
                            stage.setHeight(deltaY + stage.getHeight());
                            stage.setY(t.getScreenY());
                        } else {
                            stage.setHeight(t.getY() + dy);
                        }
                    }
                }
            }
        }
    }

class SplashScreen extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Stage current = (Stage) rootPane.getScene().getWindow();
                        nextStage(MovieLibrary.MainFrame, "Movie Library", true);
                        current.hide();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

        } catch (InterruptedException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

}

