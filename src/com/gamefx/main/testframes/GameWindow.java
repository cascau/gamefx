/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.main.testframes;

/**
 * @author cascau
 */

import com.gamefx.engine.Constants;

import com.gamefx.engine.EngineUtils;
import com.gamefx.engine.GameUtils;
import com.gamefx.engine.CameraTransform;
import com.gamefx.engine.components.GridSquare;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class GameWindow extends Application implements Constants {

    protected Scene gameScene;

    final int gameSquareSizeX = 50;
    final int gameSquareSizeY = 50;
    private GridSquare[][] gameSquares = new GridSquare[gameSquareSizeX][gameSquareSizeY];
    private GridSquare lastClickedSquare;

    protected BorderPane mainPane;

    // top part components
    protected HBox topHBox;

    //left part components
    protected VBox leftVBox;

    //center part components
    protected BorderPane centerPane;

    //right part components
    protected VBox rightVBox;

    // bottom part components
    protected HBox bottomHBox;

    protected final Group root = new Group();
    protected final CameraTransform axisGroup = new CameraTransform();
    protected final CameraTransform moleculeGroup = new CameraTransform();
    protected final CameraTransform world = new CameraTransform();
    protected final PerspectiveCamera camera = new PerspectiveCamera(false);
    protected final CameraTransform cameraXForm = new CameraTransform();
    protected final CameraTransform cameraXForm2 = new CameraTransform();
    protected final CameraTransform cameraXForm3 = new CameraTransform();
    protected Rotate rotationTransform;


    protected double mousePosX;
    protected double mousePosY;
    protected double mouseOldX;
    protected double mouseOldY;
    protected double mouseDeltaX;
    protected double mouseDeltaY;

    protected boolean keyWPressed = false, keySPressed = false, keyAPressed = false, keyDPressed = false;

    // miscellaneous
    private Box dummy;

    Point2D pointFromMove = null;
    protected int positionInRoad = 0;

    public static void main(String[] args) {
        launch(args);
    }

    protected void initGui(Stage primaryStage) {

        mainPane = new BorderPane();

        topHBox = this.createTopHBox();
        BorderPane topPane = new BorderPane(topHBox);
//        topPane.setPadding(new Insets(2));
        mainPane.setTop(topPane);

        centerPane = new BorderPane();
//        centerPane.setPadding(new Insets(2));
        createCenterPane();
        mainPane.setCenter(centerPane);

        leftVBox = this.createLeftVBox();
        BorderPane lefPane = new BorderPane(leftVBox);
//        lefPane.setPadding(new Insets(2));
        mainPane.setLeft(lefPane);

        bottomHBox = this.createBottomHBox();
        BorderPane bottomPane = new BorderPane(bottomHBox);
//        bottomPane.setPadding(new Insets(2));
        mainPane.setBottom(bottomPane);

        rightVBox = this.createRightVBox();
        BorderPane rightPane = new BorderPane(rightVBox);
//        rightPane.setPadding(new Insets(2));
        mainPane.setRight(rightPane);

        gameScene = new Scene(mainPane, 1200, 1000);

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    protected void initWorld() {

        EngineUtils.buildAxes(centerPane, axisGroup);
        buildCamera();

        gameScene.setCamera(camera);
        dummy = new Box();
        dummy.setWidth(gameSquares[0][0].getWidth());
        dummy.setHeight(gameSquares[0][0].getWidth());
        dummy.setDepth(gameSquares[0][0].getWidth());

        dummy.setTranslateX(dummy.getTranslateX() + dummy.getWidth()/2);
        dummy.setTranslateY(dummy.getTranslateY() + dummy.getHeight()/2);
        dummy.setTranslateZ(-(dummy.getTranslateZ() + dummy.getDepth()/2));
        centerPane.getChildren().add(dummy);

        handleMouse(gameScene, root);
        handleKeyboard(gameScene, root);
    }

    protected  void initListeners() {
        addWSADListeners();

        Bounds boundsInScene = mainPane.localToScene(mainPane.getBoundsInLocal());
        Point2D center = GameUtils.getCenterOfRectangle(boundsInScene);
        // create a rotation transform starting at 0 degrees, rotating about pivot point 50, 50.
        rotationTransform = new Rotate(0, 50,50);
        mainPane.getTransforms().add(rotationTransform);

        rotationTransform.pivotXProperty().bind(new SimpleDoubleProperty(center.getX()));
        rotationTransform.pivotYProperty().bind(new SimpleDoubleProperty(center.getY()));
//        rotationTransform.pivotXProperty().setValue(0);
//        rotationTransform.pivotYProperty().setValue(0);
//        rotationTransform.pivotZProperty().setValue(0);
    }

    @Override
    public void start(Stage primaryStage) {

        initGui(primaryStage);
        initWorld();
        initListeners();
//        testSelectAndDrawRoad();

    }

    protected void createCenterPane() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(2));
        for (int row = 0; row < gameSquareSizeX; row++) {
            for (int col = 0; col < gameSquareSizeY; col++) {

                GridSquare square = new GridSquare();
                square.setPositionInMatrix(new Point2D(col, row));
                square.setPositionInGame2D(new Point2D(row * (int) square.getWidth(), col * (int) square.getHeight()));
                square.setParentMatrix(gameSquares);

                square.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

                    GridSquare target = (GridSquare) event.getSource();

                    //determine the grid square in the matrix that waas clicked
//                    final Point2D positionInMatrix = GameUtils.getPositionInMatrix(target, getCenterPane());
//                    log(positionInMatrix.toString());

                    if (lastClickedSquare != null) {

//                        lastClickedSquare.select();
                        List<Point2D> road = GameUtils.buildRoad(lastClickedSquare.getPositionInMatrix(), target.getPositionInMatrix());
//                        for (Point2D p : road) {
//                            gameSquares[(int) p.getX()][(int) p.getY()].color("brown");
//                        }

                        // simple way to handle multi select as ctrl click
                        if (event.isControlDown()) {
                            final Timeline timeline = new Timeline(
                                    new KeyFrame(Duration.ZERO, event12 -> {
                                        Point2D pointToMove = road.get(positionInRoad++);
                                        gameSquares[(int) pointToMove.getX()][(int) pointToMove.getY()].color("brown");

                                        if (pointFromMove != null) {
                                            moveDummy(pointFromMove, pointToMove);
                                        }
                                        pointFromMove = pointToMove;
                                    }),
                                    new KeyFrame(Duration.millis(100))
                            );
                            timeline.setOnFinished(event1 -> {
                                // reset the position in the road after the road was completed
                                positionInRoad = 0;
                                target.select();
                                GameUtils.highlightSquares(gameSquares, target.getPositionInMatrix(), 3);
                            });
//                            GameUtils.unhighlightSquares(gameSquares, road.get(0), 3);
                            timeline.setCycleCount(road.size());
                            timeline.play();

                            // print the square boundaries and square center point
                            Bounds boundsInScene = target.localToScene(target.getBoundsInLocal());
                            Bounds lastClickedSquareBounds = lastClickedSquare.localToScene(lastClickedSquare.getBoundsInLocal());
                            Point2D centerCurrentSquare = GameUtils.getCenterOfRectangle(boundsInScene);
                            Point2D centerLastClickedSquare = GameUtils.getCenterOfRectangle(lastClickedSquareBounds);

                            Line line = new Line(centerLastClickedSquare.getX(), centerLastClickedSquare.getY(), centerCurrentSquare.getX(), centerCurrentSquare.getY());
//                        getCenterPane().getChildren().add(line);
                            //
                        } else {
                            GameUtils.unselectAllGameSquares(gameSquares);
                            GameUtils.highlightSquares(gameSquares, target.getPositionInMatrix(), 3);
                        }
                    } else {
                        dummy.setTranslateX(target.getTranslateX() + 10);
                        dummy.setTranslateY(target.getTranslateY() - 10);
                    }
                    lastClickedSquare = target;
                    target.select();
                });

                gameSquares[col][row] = square;

                gridPane.add(square, col, row);
            }
        }
        for (int i = 0; i < gameSquareSizeY; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        getCenterPane().setCenter(gridPane);
    }
    private void moveDummy(Point2D A, Point2D B) {

        GridSquare gridA = GameUtils.getGridFromCoordinates(gameSquares, A);
        GridSquare gridB = GameUtils.getGridFromCoordinates(gameSquares, B);

        double dx = gridB.getCenter().getX() - gridA.getCenter().getX();
        double dy = gridB.getCenter().getY() - gridA.getCenter().getY();
        dummy.setTranslateX(dummy.getTranslateX() + dx);
        dummy.setTranslateY(dummy.getTranslateY() + dy);
    }

    protected void buildCamera() {
        log("buildCamera()");
        root.getChildren().add(cameraXForm);
        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);

//        cameraXForm3.setRotateX(180.0);
//        cameraXForm3.setRotateY(180.0);
//        cameraXForm3.setRotateZ(180.0);
////
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
//        cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
//        cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
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

            double modifier = 10;

            if (me.isControlDown()) {
                rotationTransform.setAxis(Rotate.X_AXIS);
                cameraXForm.rx.setAngle(cameraXForm.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            }
            if (me.isAltDown()) {
                rotationTransform.setAxis(Rotate.Z_AXIS);
            }
            if (me.isShiftDown()) {
                rotationTransform.setAxis(Rotate.Z_AXIS);
            }

            if (me.isPrimaryButtonDown()) {
                handleMouseMove(me);
                // move the scene 2D
                cameraXForm2.t.setX(cameraXForm2.t.getX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED * 10);
                cameraXForm2.t.setY(cameraXForm2.t.getY() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED * 10);
            } else if (me.isSecondaryButtonDown()) {
//                cameraXForm.ry.setAngle(cameraXForm.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED * 0.2);
                cameraXForm.rx.setAngle(cameraXForm.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isMiddleButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                camera.setTranslateZ(newZ);
                // move the scene 2D
//                cameraXForm2.t.setX(cameraXForm2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED * 10);
//                cameraXForm2.t.setY(cameraXForm2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED * 10);
            }
        });
        scene.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            mainPane.setScaleX(mainPane.getScaleX() * zoomFactor);
            mainPane.setScaleY(mainPane.getScaleY() * zoomFactor);
            event.consume();
//            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        });

        scene.setOnMouseMoved((MouseEvent event) -> {
//            handleMouseMove(event);
        });
    }

    protected void addWSADListeners() {

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    keyWPressed = true;
                    break;
                case S:
                    keySPressed = true;
                    break;
                case A:
                    keyAPressed = true;
                    break;
                case D:
                    keyDPressed = true;
                    break;
            }
//            log("\t" + keyWPressed + "\n" + keyAPressed + " " + keySPressed + " " + keyDPressed);
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    keyWPressed = false;
                    break;
                case S:
                    keySPressed = false;
                    break;
                case A:
                    keyAPressed = false;
                    break;
                case D:
                    keyDPressed = false;
                    break;
            }
//            log("\t" + keyWPressed + "\n" + keyAPressed + " " + keySPressed + " " + keyDPressed);
        });
    }

    protected void handleMouseMove(MouseEvent me) {
//
//        Bounds boundsInScene = mainPane.localToScene(mainPane.getBoundsInLocal());
//        log("[" + boundsInScene.getMinX() + " " + boundsInScene.getMinY() + "]");
    }

    protected void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(event -> {
            double moveSpeed = 20;
            switch (event.getCode()) {
                case R:
                    cameraXForm2.t.setX(0.0);
                    cameraXForm2.t.setY(0.0);
                    camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                    cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                    cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                    cameraXForm.rz.setAngle(CAMERA_INITIAL_Z_ANGLE);

                    mainPane.setTranslateX(0);
                    mainPane.setTranslateY(0);
                    break;
                case X:
                    axisGroup.setVisible(!axisGroup.isVisible());
                    break;
                case V:
                    moleculeGroup.setVisible(!moleculeGroup.isVisible());
                    break;

                case W:
                    mainPane.setTranslateY(mainPane.getTranslateY() - moveSpeed);
//                    if (keyAPressed) {
//                        mainPane.setTranslateX(mainPane.getTranslateX() - moveSpeed);
//                    } else if (keyAPressed) {
//                        mainPane.setTranslateX(mainPane.getTranslateX() + moveSpeed);
//                    }
                    break;
                case S:
                    mainPane.setTranslateY(mainPane.getTranslateY() + moveSpeed);
//                    if (keyAPressed) {
//                        mainPane.setTranslateX(mainPane.getTranslateX() - moveSpeed);
//                    } else if (keyAPressed) {
//                        mainPane.setTranslateX(mainPane.getTranslateX() + moveSpeed);
//                    }
                    break;
                case A:
                    mainPane.setTranslateX(mainPane.getTranslateX() - moveSpeed);
//                    if (keyWPressed) {
//                        mainPane.setTranslateY(mainPane.getTranslateY() - moveSpeed);
//                    } else if (keySPressed) {
//                        mainPane.setTranslateY(mainPane.getTranslateY() + moveSpeed);
//                    }
                    break;
                case D:
                    mainPane.setTranslateX(mainPane.getTranslateX() + moveSpeed);
//                    if (keyWPressed) {
//                        mainPane.setTranslateY(mainPane.getTranslateY() - moveSpeed);
//                    } else if (keySPressed) {
//                        mainPane.setTranslateY(mainPane.getTranslateY() + moveSpeed);
//                    }
                    break;
            }
        });
    }

    protected HBox createTopHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(1));
        hbox.setSpacing(0);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setPrefHeight(50);
//        resetButton = new Button("Reset");
//        resetButton.setPrefSize(50, 20);
//        resetButton.setOnMouseClicked(event -> {
//            GameUtils.unselectAllGameSquares(gameSquares);
//            lastClickedSquare = null;
//        });
//
//        hbox.getChildren().add(resetButton);

        return hbox;
    }

    protected HBox createBottomHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(1));
        hbox.setSpacing(0);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setPrefHeight(50);

        return hbox;
    }

    protected VBox createLeftVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(1));
        vbox.setSpacing(0);
        vbox.setStyle("-fx-background-color: #336699;");
        vbox.setPrefWidth(50);
        vbox.setFillWidth(true);

        return vbox;
    }

    protected VBox createRightVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(1));
        vbox.setSpacing(0);
        vbox.setStyle("-fx-background-color: #336699;");
        vbox.setPrefWidth(50);

        return vbox;
    }

    private void testSelectAndDrawRoad() {

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
        }
        road = GameUtils.buildRoad(B, C);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        road = GameUtils.buildRoad(C, D);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        road = GameUtils.buildRoad(D, E);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        road = GameUtils.buildRoad(E, F);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
        road = GameUtils.buildRoad(F, G);
        for (Point2D p : road) {
            square = GameUtils.getGridFromCoordinates(gameSquares, p);
            square.select();
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    public BorderPane getCenterPane() {
         return centerPane;
    }
}
