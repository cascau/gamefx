package com.gamefx.main.window;

import com.gamefx.camera.CameraDelegate;
import com.gamefx.engine.EngineDelegate;
import com.gamefx.engine.components.GameObject;
import com.gamefx.engine.utilscripts.UtilityScripts;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.gamefx.engine.Constants.BOARD_SIZE_X;
import static com.gamefx.engine.Constants.BOARD_SIZE_Y;

public class GameFxWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    EngineDelegate engineDelegate;
    CameraDelegate cameraDelegate;

    GameObject player;

    public double mouseOldX, mouseOldY = 0, mousePosX = 0, mousePosY = 0, mouseDeltaX = 0, mouseDeltaY = 0;

    Scene mainScene;
    SubScene gameScene;

    Group rootGroup;
    Group subRootGroup;

    Rectangle gameBoard;
    Rectangle minimap;

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

        engineDelegate.drawLinesOnGameBoard(subRootGroup);

        stage.setTitle("3D Prototype");
        stage.setMaximized(true);
        stage.setScene(mainScene);
        stage.show();
    }

    private void initComponents() {

        engineDelegate = new EngineDelegate();

        buildMainScene();
        buildGameScene();

        buildCamera();

        buildGameBoard();
        buildMinimap();

        // initialize a dummy player
        player = UtilityScripts.buildAndPlaceDefaultPlayer();

        subRootGroup.getChildren().addAll(player, gameBoard, engineDelegate.buildAxes());
        rootGroup.getChildren().add(gameScene);
        rootGroup.getChildren().add(minimap);
        rootGroup.getChildren().add(cameraDelegate.cameraXForm);
    }

    private void buildMainScene() {
        rootGroup = new Group();
        mainScene = new Scene(rootGroup, 1600, 1000, false, SceneAntialiasing.BALANCED);
    }

    private void buildGameScene() {
        subRootGroup = new Group();
        subRootGroup.setTranslateX(350);
        subRootGroup.setTranslateY(50);

        gameScene = new SubScene(subRootGroup, 1600, 1000, true, SceneAntialiasing.BALANCED);

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
                    // rotate the game board on its X axis
                    cameraDelegate.rotateGameBoardOnAxisX(mouseDeltaX, mouseDeltaY);
                }
            }
        });
    }

    private void buildGameBoard() {
        // initialize the game board
        gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);
        gameBoard.setFill(Color.DARKGRAY);

        gameBoard.setOnMouseClicked(event -> {

            double x = event.getSceneX();
            double y = event.getSceneY();

            System.out.println(gameBoard.getBoundsInParent());

            System.out.println(x + "\t" + y);
        });
    }

    private void buildMinimap() {
        // initialize the mini map
        minimap = engineDelegate.buildMinimap();

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
                cameraDelegate.rotateGameBoardOnAxisX(mouseDeltaX, mouseDeltaY);
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