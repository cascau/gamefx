/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.main;

/**
 * @author cascau
 */

import com.gamefx.engine.Constants;

import com.gamefx.engine.EngineUtils;
import com.gamefx.engine.GameUtils;
import com.gamefx.engine.TXForm;
import com.gamefx.engine.components.GridSquare;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class GameWindow extends Application implements Constants {

    protected Scene gameScene;

    final int gameSquareSizeX = 20;
    final int gameSquareSizeY = 20;
    private GridSquare[][] gameSquares = new GridSquare[gameSquareSizeX][gameSquareSizeY];
    private GridSquare lastClickedSquare;

    protected BorderPane mainPane;

    // top part components
    protected HBox topHBox;
    protected Button resetButton;

    //left part components
    protected VBox leftVBox;

    //center part components
    protected BorderPane centerPane;

    //right part components
    protected VBox rightVBox;

    // bottom part components
    protected HBox bottomHBox;

    protected final Group root = new Group();
    protected final TXForm axisGroup = new TXForm();
    protected final TXForm moleculeGroup = new TXForm();
    protected final TXForm world = new TXForm();
    protected final PerspectiveCamera camera = new PerspectiveCamera(true);
    protected final TXForm cameraXForm = new TXForm();
    protected final TXForm cameraXForm2 = new TXForm();
    protected final TXForm cameraXForm3 = new TXForm();

    protected double mousePosX;
    protected double mousePosY;
    protected double mouseOldX;
    protected double mouseOldY;
    protected double mouseDeltaX;
    protected double mouseDeltaY;

    protected void initGui(Stage primaryStage) {

        primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });

        mainPane = new BorderPane();
        topHBox = this.createTopHBox();
        BorderPane topPane = new BorderPane(topHBox);
        topPane.setPadding(new Insets(2));
        mainPane.setTop(topPane);

        centerPane = new BorderPane();
        gameScene = new Scene(centerPane, 1024, 768);

        centerPane.setPadding(new Insets(2));
        createCenter();
        mainPane.setCenter(centerPane);

        leftVBox = this.createLeftVBox();

        BorderPane lefPane = new BorderPane(leftVBox);
        lefPane.setPadding(new Insets(2));
        mainPane.setLeft(lefPane);

        bottomHBox = this.createBottomHBox();
        BorderPane bottomPane = new BorderPane(bottomHBox);
        bottomPane.setPadding(new Insets(2));
        mainPane.setBottom(bottomPane);

        rightVBox = this.createRightVBox();
        BorderPane rightPane = new BorderPane(rightVBox);
        rightPane.setPadding(new Insets(2));
        mainPane.setRight(rightPane);

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    protected void initWorld() {

        EngineUtils.buildAxes(world, axisGroup);
        buildCamera();
//        gameScene.setCamera(camera);
        centerPane.getScene().setCamera(camera);
        handleMouse(gameScene, root);
        handleKeyboard(gameScene, root);
    }

    @Override
    public void start(Stage primaryStage) {

        initGui(primaryStage);
        initWorld();

        Point2D A = new Point2D(10, 11);
        Point2D B = new Point2D(5, 9);
        Point2D C = new Point2D(17, 4);
        Point2D D = new Point2D(6, 2);
        Point2D E = new Point2D(15, 18);
        Point2D F = new Point2D(3, 15);
        Point2D G = new Point2D(6, 18);

        GridSquare square;
        List<Point2D> road = GameUtils.buildRoad(A, B);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
            System.out.println(p.toString());
        }
        System.out.println();
        road = GameUtils.buildRoad(B, C);
        for (Point2D p : road) {
            System.out.println(p.toString());
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        System.out.println();
        road = GameUtils.buildRoad(C, D);
        for (Point2D p : road) {
            System.out.println(p.toString());
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        System.out.println();
        road = GameUtils.buildRoad(D, E);
        for (Point2D p : road) {
            System.out.println(p.toString());
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        System.out.println();
        road = GameUtils.buildRoad(E, F);
        for (Point2D p : road) {
            System.out.println(p.toString());
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        System.out.println();
        road = GameUtils.buildRoad(F, G);
        for (Point2D p : road) {
            System.out.println(p.toString());
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
    }

    protected void createCenter() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(2));
        for (int row = 0; row < gameSquareSizeX; row++) {
            for (int col = 0; col < gameSquareSizeY; col++) {

                GridSquare square = new GridSquare();
                square.setPositionInMatrix(new Point2D(col, row));
                square.setParentMatrix(gameSquares);

                square.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

                    GridSquare target = (GridSquare) event.getSource();

                    //determine the grid square in the matrix that waas clicked
                    final Point2D positionInMatrix = GameUtils.getPositionInMatrix(target, centerPane);
                    System.out.println(positionInMatrix.toString());

                    GameUtils.unselectAllGameSquares(gameSquares);
                    if (lastClickedSquare != null) {

                        lastClickedSquare.select();

                        // print the square boundaries and square center point
                        Bounds boundsInScene = target.localToScene(target.getBoundsInLocal());
                        Bounds lastClickedSquareBounds = lastClickedSquare.localToScene(lastClickedSquare.getBoundsInLocal());
                        Point2D centerCurrentSquare = GameUtils.getCenterOfRectangle(boundsInScene);
                        Point2D centerLastClickedSquare = GameUtils.getCenterOfRectangle(lastClickedSquareBounds);


                        System.out.println("X: " + lastClickedSquareBounds.getMinX() + " -> " + centerLastClickedSquare.getX() + " -> " + lastClickedSquareBounds.getMaxX());
                        System.out.println("Y: " + lastClickedSquareBounds.getMinY() + " -> " + centerLastClickedSquare.getY() + " -> " + lastClickedSquareBounds.getMaxY());

                        Line line = new Line(centerLastClickedSquare.getX(), centerLastClickedSquare.getY(), centerCurrentSquare.getX(), centerCurrentSquare.getY());
                        centerPane.getChildren().add(line);

                        //
                    }
                    lastClickedSquare = target;
                    target.select();
                    GameUtils.highlightSquares(gameSquares, target.getPositionInMatrix(), 3);
                    // last, switch the last clicked square
//                    lastClickedSquare = gameSquares[dx][dy];
//                    System.out.println("[" + lastClickedSquare.getPositionInMatrix().getX() + " " + lastClickedSquare.getPositionInMatrix().getY() + "] -> [" + dx + "; " + dy + "]");
//                    System.out.println("[" + dx + "; " + dy + "]");


                });

                gameSquares[col][row] = square;

                gridPane.add(square, col, row);
            }
        }
        for (int i = 0; i < gameSquareSizeY; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        centerPane.setCenter(gridPane);
    }

    protected void buildCamera() {
        System.out.println("buildCamera()");
        root.getChildren().add(cameraXForm);
        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);
//        cameraXForm.setRotateZ(180.0);
//        cameraXForm2.setRotateZ(180.0);
//        cameraXForm3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    protected void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXForm.ry.setAngle(cameraXForm.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                cameraXForm.rx.setAngle(cameraXForm.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isMiddleButtonDown()) {
                cameraXForm2.t.setX(cameraXForm2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
                cameraXForm2.t.setY(cameraXForm2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            }
        });
    }

    protected void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXForm2.t.setX(0.0);
                        cameraXForm2.t.setY(0.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        moleculeGroup.setVisible(!moleculeGroup.isVisible());
                        break;
                }
            }
        });
    }

    protected HBox createTopHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setPrefHeight(50);

        resetButton = new Button("Reset");
        resetButton.setPrefSize(100, 20);
        resetButton.setOnMouseClicked(event -> {

            GameUtils.unselectAllGameSquares(gameSquares);
            lastClickedSquare = null;
        });

        hbox.getChildren().add(resetButton);

        return hbox;
    }

    protected HBox createBottomHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        return hbox;
    }

    protected VBox createLeftVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");

        vbox.setFillWidth(true);

        return vbox;
    }

    protected VBox createRightVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");
        vbox.setPrefWidth(50);

        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
