package com.gamefx.main.window;

import com.gamefx.camera.CameraDelegate;
import com.gamefx.engine.EngineDelegate;
import com.gamefx.engine.components.GameObject;
import com.gamefx.engine.util.UtilityScripts;
import com.gamefx.scene.DrawSceneDelegate;
import com.gamefx.scene.SceneCalculator;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.gamefx.engine.Constants.*;

public class GameFxWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    SceneCalculator sceneCalculator;
    EngineDelegate engineDelegate;
    DrawSceneDelegate drawSceneDelegate;
    CameraDelegate cameraDelegate;

    GameObject player;

    public double mouseOldX, mouseOldY = 0, mousePosX = 0, mousePosY = 0, mouseDeltaX = 0, mouseDeltaY = 0;

    Scene mainScene;
    SubScene gameScene;

    Group rootGroup;
    Group subRootGroup;

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
        stage.setMaximized(true);
        stage.setScene(mainScene);
        stage.show();
    }

    private void initComponents() {

        initDelegates();

        buildMainScene();
        buildGameScene();

        buildCamera();

        buildGameBoard();
        buildMinimap();

        // initialize a dummy player
        player = UtilityScripts.buildAndPlaceDefaultPlayer();

        subRootGroup.getChildren().addAll(player, gameBoard, drawSceneDelegate.buildAxes());
        rootGroup.getChildren().add(gameScene);
        rootGroup.getChildren().add(minimap);
        rootGroup.getChildren().add(cameraDelegate.cameraXForm);
    }

    private void initDelegates() {
        drawSceneDelegate = new DrawSceneDelegate();
        engineDelegate = new EngineDelegate();
        sceneCalculator = new SceneCalculator();
    }

    private void buildMainScene() {
        rootGroup = new Group();
        mainScene = new Scene(rootGroup, 1600, 1000, false, SceneAntialiasing.BALANCED);
    }

    private void buildGameScene() {
        subRootGroup = new Group();
        subRootGroup.setTranslateX(350);
        subRootGroup.setTranslateY(50);
        drawSceneDelegate.drawFullLinesOnGameBoard(subRootGroup);
//        drawSceneDelegate.drawSmallLinesOnGameBoard(subRootGroup);

        gameScene = new SubScene(subRootGroup, 1600, 1000, true, SceneAntialiasing.BALANCED);
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
                }
            }
        });

        mainScene.setOnKeyPressed(event -> {

            Point2D destination = null;
            double destX = sceneCalculator.getGameSquareFromMouseEventX(player.getTranslateX());
            double destY = sceneCalculator.getGameSquareFromMouseEventY(player.getTranslateY());
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
                engineDelegate.moveGameObjetToPoint(player, destination);
            }
        });
    }

    private void buildGameBoard() {
        // initialize the game board
        gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);
        gameBoard.setFill(Color.TRANSPARENT);
//        gameBoard.setFill(Color.MEDIUMAQUAMARINE);

        gameBoard.setOnMouseClicked(event -> {
            // move the player to right click position
            if (MouseButton.SECONDARY.equals(event.getButton()) && !cameraDragDetected && event.getClickCount() == 1) {
                Point2D destination = sceneCalculator.getCenterOfGameSquareFromMouseEvent(event);
                engineDelegate.moveGameObjetToPoint(player, destination);
            }
            // camera is not dragged anymore
            cameraDragDetected = false;
        });
    }

    private void buildMinimap() {
        // initialize the mini map
        minimap = drawSceneDelegate.buildMinimap();

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
                cameraDelegate.rotateGameBoardPivotInCenter(mouseDeltaX, mouseDeltaY);
            }
        });
    }

    private void buildCamera() {
        // initialize the camera
        cameraDelegate = new CameraDelegate(gameScene);
        cameraDelegate.initCamera();
        gameScene.setCamera(cameraDelegate.camera);
    }
}