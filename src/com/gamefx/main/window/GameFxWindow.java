package com.gamefx.main.window;

import com.gamefx.camera.CameraDelegate;
import com.gamefx.engine.EngineDelegate;
import com.gamefx.engine.components.GameActor;
import com.gamefx.engine.components.MatrixScene;
import com.gamefx.engine.util.UtilityScripts;
import com.gamefx.scene.DrawSceneDelegate;
import com.gamefx.scene.SceneCalculator;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.gamefx.engine.Constants.*;

public class GameFxWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    List<GameActor> allGameObjects = new ArrayList<>();
    List<GameActor> selectedGameObjects = new ArrayList<>();

    DrawSceneDelegate drawSceneDelegate;
    CameraDelegate cameraDelegate;

    GameActor player1, player2;

    public double mouseOldX, mouseOldY = 0, mousePosX = 0, mousePosY = 0, mouseDeltaX = 0, mouseDeltaY = 0;

    Scene mainScene;
    SubScene gameScene;

    Group rootGroup;
    Group subRootGroup;
    Group minimapGroup;

    VBox infoBox;

    Rectangle gameBoard;
    Rectangle minimap;

    // to avoid triggering mouseClick() event after mouseDrag()
    private boolean cameraDragDetected = false;

    @Override
    public void start(Stage stage) {

        initComponents();


        // events for rotation
        rootGroup.setOnMousePressed(event -> {
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
        });

        stage.setTitle("3D Prototype");
//        stage.setMaximized(true);
        stage.setResizable(Boolean.FALSE);
        stage.setScene(mainScene);
        stage.show();
    }

    private void initComponents() {

        initDelegates();

        buildMinimap();
        buildInfoGroup();
        buildMainScene();
        buildGameScene();


        buildGameBoard();
        buildCamera();

        // initialize a dummy player1
        player1 = new GameActor(15, 28);
        player2 = new GameActor(8, 20);
        allGameObjects.add(player1);
        allGameObjects.add(player2);

        subRootGroup.getChildren().addAll(player1, player2, gameBoard, drawSceneDelegate.buildAxes());
        subRootGroup.getChildren().addAll(UtilityScripts.createGameWorldFromMatrix(MatrixScene.MAP_BORS_VASILE));
        rootGroup.getChildren().add(gameScene);
        rootGroup.getChildren().add(minimapGroup);
        rootGroup.getChildren().add(infoBox);
        rootGroup.getChildren().add(cameraDelegate.cameraXForm);

        cameraDelegate.zoomNodeAnimated(subRootGroup, -500, -300, -10);
    }

    private void initDelegates() {
        drawSceneDelegate = new DrawSceneDelegate();
    }

    private void buildMainScene() {
        rootGroup = new Group();
        mainScene = new Scene(rootGroup, 1602, 1000, false, SceneAntialiasing.BALANCED);
        mainScene.setFill(Color.DARKGREEN);
    }

    private void buildGameScene() {
        subRootGroup = new Group();
        subRootGroup.setTranslateX(-500);
        subRootGroup.setTranslateY(-300);
        drawSceneDelegate.drawFullLinesOnGameBoard(subRootGroup);
//        drawSceneDelegate.drawSmallLinesOnGameBoard(subRootGroup);

        gameScene = new SubScene(subRootGroup, 1200, 800, true, SceneAntialiasing.BALANCED);
        gameScene.setFill(Color.AQUA);
        // scroll event
        gameScene.setOnScroll((ScrollEvent event) -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            double zoomFactor = event.getDeltaY() > 0 ? 1.05 : 0.95;
//            cameraDelegate.zoomCamera(event.getDeltaY());
            cameraDelegate.zoomNodeAnimated(subRootGroup, mousePosX, mousePosY, zoomFactor);
            event.consume();
        });

        // mouse dragged event
        gameScene.setOnMouseDragged(event -> {

            // camera is dragged
            cameraDragDetected = true;

            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            if (event.isPrimaryButtonDown()) {
                // move the game board on LEFT CLICK
                cameraDelegate.moveGameBoard(mouseDeltaX, mouseDeltaY);

            } else if (event.isSecondaryButtonDown()) {
                if (event.isControlDown()) {
                    // rotate the game board with pivot in center on CTRL + RIGHT CLICK
                    cameraDelegate.rotateGameBoardPivotInCenter(mouseDeltaX, mouseDeltaY);
                } else {
                    Bounds bounds = EngineDelegate.getBounds(gameBoard);
                    cameraDelegate.cameraXForm.rz.setPivotX((bounds.getMinX() + bounds.getMaxX()) / 2);
                    cameraDelegate.cameraXForm.rz.setPivotY((bounds.getMinY() + bounds.getMaxY()) / 2);

                    cameraDelegate.cameraXForm.rz.setAngle(cameraDelegate.cameraXForm.rz.getAngle() + mouseDeltaX);
                }
            }
        });

        mainScene.setOnKeyPressed(event -> {

            Point2D destination = null;
            double destX = SceneCalculator.getGameSquareFromMouseEventX(player1.getTranslateX());
            double destY = SceneCalculator.getGameSquareFromMouseEventY(player1.getTranslateY());
            switch (event.getCode()) {
                case W:
                    destination = new Point2D(destX, destY - GRID_SQUARE_LENGTH);
                    break;
                case S:
                    destination = new Point2D(destX, destY + GRID_SQUARE_LENGTH);
                    break;
                case A:
                    destination = new Point2D(destX - GRID_SQUARE_LENGTH, destY);
                    break;
                case D:
                    destination = new Point2D(destX + GRID_SQUARE_LENGTH, destY);
                    break;
            }
            if (destination != null) {
                EngineDelegate.moveGameObjetToPoint(player1, destination);
            }
        });
    }

    private void buildGameBoard() {
        // initialize the game board
        gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);
        gameBoard.setFill(Color.TRANSPARENT);
//        gameBoard.setFill(Color.MEDIUMAQUAMARINE);

        subRootGroup.setOnMouseClicked(event -> {

            Node selectedNode = event.getPickResult().getIntersectedNode();
            GameActor actor = this.getGameObjectFromListOfObjects(selectedNode);

            // select or deselect the game object
            if (MouseButton.PRIMARY.equals(event.getButton()) && !cameraDragDetected) {
                if (actor != null) {
//                    deselectAllGameObjects();
                    if (!actor.isSelected()) {
                        actor.select();
                        selectedGameObjects.add(actor);
                    }
                } else {
                    deselectAllGameObjects();
                }
            }
            // move the player1 to right click position
            if (MouseButton.SECONDARY.equals(event.getButton()) && !cameraDragDetected) {
                Point2D destination = SceneCalculator.getCenterOfGameSquareFromMouseEvent(event);

//                engineDelegate.moveGameObjetToPoint(actor, destination);
                for (GameActor object : selectedGameObjects) {
                    EngineDelegate.moveGameObjetToPoint(object, destination);
                }
            }
            // camera is not dragged anymore
            cameraDragDetected = false;
        });
    }

    private void buildMinimap() {

        minimapGroup = new Group();
        // initialize the mini map
        minimap = drawSceneDelegate.buildMinimap();
        minimapGroup.getChildren().add(minimap);
        drawSceneDelegate.drawLinesOnMinimap(minimap, minimapGroup);

        minimap.setOnScroll((ScrollEvent event) -> {
            cameraDelegate.zoomCamera(event.getDeltaY());
            event.consume();
        });

        minimap.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            if (event.isPrimaryButtonDown()) {
                cameraDelegate.moveGameBoard(mouseDeltaX, mouseDeltaY);

            } else if (event.isSecondaryButtonDown()) {
                Bounds bounds = EngineDelegate.getBounds(gameBoard);
                cameraDelegate.cameraXForm.rz.setPivotX((bounds.getMinX() + bounds.getMaxX()) / 2);
                cameraDelegate.cameraXForm.rz.setPivotY((bounds.getMinY() + bounds.getMaxY()) / 2);

                cameraDelegate.rotateGameBoardPivotInCenter(mouseDeltaX, mouseDeltaY);

            }
        });
    }

    private void buildInfoGroup() {

        infoBox = drawSceneDelegate.buildInfoGroup();

    }

    private void buildCamera() {
        // initialize the camera
        cameraDelegate = new CameraDelegate(gameScene);
        cameraDelegate.initCamera();
        gameScene.setCamera(cameraDelegate.camera);

        Bounds bounds = EngineDelegate.getBounds(gameBoard);
        cameraDelegate.cameraXForm.rz.setPivotX((bounds.getMinX() + bounds.getMaxX()) / 2);
        cameraDelegate.cameraXForm.rz.setPivotY((bounds.getMinY() + bounds.getMaxY()) / 2);
    }

    private GameActor getGameObjectFromListOfObjects(Node node) {

        for (GameActor gameObject : allGameObjects) {

            if (gameObject.getEntity().equals(node)) {
                return gameObject;
            }
        }
        return null;
    }

    private synchronized void deselectAllGameObjects() {

        List<GameActor> objectsToDeselect = new ArrayList<>();
        for (Iterator<GameActor> it = selectedGameObjects.iterator(); it.hasNext(); ) {
            GameActor actor = it.next();
            actor.deselect();
            objectsToDeselect.add(actor);
        }
        selectedGameObjects.removeAll(objectsToDeselect);
    }
}