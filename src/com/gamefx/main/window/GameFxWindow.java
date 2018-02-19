package com.gamefx.main.window;

import com.gamefx.camera.CameraDelegate;
import com.gamefx.engine.EngineDelegate;
import com.gamefx.engine.components.GameObject;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.gamefx.engine.Constants.*;

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

        engineDelegate = new EngineDelegate();

        rootGroup = new Group();
        mainScene = new Scene(rootGroup, 1600, 1000, false, SceneAntialiasing.BALANCED);

        subRootGroup = new Group();
        gameScene = new SubScene(subRootGroup, 1600, 1000, true, SceneAntialiasing.BALANCED);


        gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);
        gameBoard.setFill(Color.DARKGRAY);

        // initialize the camera
        cameraDelegate = new CameraDelegate(gameScene);
        cameraDelegate.initCamera();


        minimap = new Rectangle();
        minimap.setX(0);
        minimap.setY(0);
        minimap.setWidth(300);
        minimap.setHeight(300);
        minimap.setFill(Color.GREY);

        buildAndPlacePlayer();

        rootGroup.getChildren().add(gameScene);

        gameScene.setCamera(cameraDelegate.camera);
        subRootGroup.getChildren().addAll(player, gameBoard, engineDelegate.buildAxes());
        subRootGroup.setTranslateX(350);
        subRootGroup.setTranslateY(50);
        rootGroup.getChildren().add(minimap);

        rootGroup.getChildren().add(cameraDelegate.cameraXForm);

        // events for rotation
        rootGroup.setOnMousePressed(event -> {
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
        });

        gameScene.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            if (event.isPrimaryButtonDown()) {
                cameraDelegate.moveGameBoard(mouseDeltaX, mouseDeltaY);

            } else if (event.isSecondaryButtonDown()) {
                if (event.isControlDown()) {
                    cameraDelegate.rotateGameBoardPivotInCenter(mouseDeltaX, mouseDeltaY);
                } else {
                    cameraDelegate.rotateGameBoardOnAxisX(mouseDeltaX, mouseDeltaY);
                }
            }
        });

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

        gameScene.setOnScroll((ScrollEvent event) -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            double zoomFactor = event.getDeltaY() > 0 ? 1.05 : 0.95;
//            cameraDelegate.zoomCamera(event.getDeltaY());
            cameraDelegate.zoomNodeAnimated(subRootGroup, mousePosX, mousePosY, zoomFactor);
            event.consume();
        });

        engineDelegate.drawLinesOnGameBoard(subRootGroup);

        stage.setTitle("3D Prototype");
        stage.setMaximized(true);
        stage.setScene(mainScene);
        stage.show();
    }

    private void buildAndPlacePlayer() {


        player = new GameObject();
        player.setTranslateX(500 + GRID_SQUARE_LENGTH / 2);
        player.setTranslateY(500 + GRID_SQUARE_LENGTH / 2);
    }
}