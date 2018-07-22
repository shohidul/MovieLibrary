/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movielibrary;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Hridoy
 */
public class TestResizeableCorner extends Application {

    Scene scene;
    Stage stage;

    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        scene = new Scene(root, 300, 250);

        ResizeListener listener = new ResizeListener();
        scene.setOnMouseMoved(listener);
        scene.setOnMousePressed(listener);
        scene.setOnMouseDragged(listener);

        root.setStyle("-fx-background-color: red;"); // just to see the limit of the window ;-)
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED); // remove the "three top buttons on the window"
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
                    if (stage.getWidth() <= 300) {
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
                    } else if (stage.getWidth() > 300) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            stage.setWidth(deltaX + stage.getWidth());
                            stage.setX(t.getScreenX());
                        } else {
                            stage.setWidth(t.getX() + dx);
                        }
                    }
                    
                    if (stage.getHeight() <= 300) {
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
                    } else if (stage.getHeight() > 300) {
                        if (moveV) {
                            deltaY = stage.getY() - t.getScreenY();
                            stage.setHeight(deltaY + stage.getHeight());
                            stage.setY(t.getScreenY());
                        } else {
                            stage.setHeight(t.getY() + dy);
                        }
                    }
                } else if (resizeH) {
                    if (stage.getWidth() <= 300) {
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
                    } else if (stage.getWidth() > 300) {
                        if (moveH) {
                            deltaX = stage.getX() - t.getScreenX();
                            stage.setWidth(deltaX + stage.getWidth());
                            stage.setX(t.getScreenX());
                        } else {
                            stage.setWidth(t.getX() + dx);
                        }
                    }
                } else if (resizeV) {
                    if (stage.getHeight() <= 300) {
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
                    } else if (stage.getHeight() > 300) {
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
}
