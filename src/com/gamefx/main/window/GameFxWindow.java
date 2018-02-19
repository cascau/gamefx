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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.gamefx.engine.Constants.*;

public class GameFxWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    EngineDelegate engineDelegate;
    CameraDelegate cameraDelegate;

    public double mouseOldX, mouseOldY = 0, mousePosX = 0, mousePosY = 0, mouseDeltaX = 0, mouseDeltaY = 0;

    Group subRootGroup = new Group();
    SubScene gameScene;
    Group rootGroup = new Group();

    private Rectangle gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);

    @Override
    public void start(Stage stage) {

        engineDelegate = new EngineDelegate();

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        GameObject player = new GameObject();
        player.setTranslateX(500 + GRID_SQUARE_LENGTH / 2);
        player.setTranslateY(500 + GRID_SQUARE_LENGTH / 2);
        player.setMaterial(redMaterial);

        gameBoard.setFill(Color.DARKGRAY);

        Scene scene = new Scene(rootGroup, 1600, 1000, false, SceneAntialiasing.BALANCED);

        gameScene = new SubScene(subRootGroup, 1600, 1000, true, SceneAntialiasing.BALANCED);

        // initialize the camera
        cameraDelegate = new CameraDelegate(gameScene);
        cameraDelegate.initCamera();


        Rectangle minimap = new Rectangle();
        minimap.setX(0);
        minimap.setY(0);
        minimap.setWidth(300);
        minimap.setHeight(300);
        minimap.setFill(Color.GREY);
        AnchorPane minimapAnchor = new AnchorPane(minimap);
//        AnchorPane minimapAnchor = new AnchorPane(cameraDelegate.cameraView);
        AnchorPane.setBottomAnchor(minimapAnchor, 1.0);
        AnchorPane.setRightAnchor(minimapAnchor, 1.0);


        rootGroup.getChildren().add(gameScene);

        gameScene.setCamera(cameraDelegate.camera);
        subRootGroup.getChildren().addAll(player, gameBoard, engineDelegate.buildAxes());
        subRootGroup.setTranslateX(350);
        subRootGroup.setTranslateY(50);
        rootGroup.getChildren().add(minimapAnchor);

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

        drawLinesOnGameBoard();

        stage.setTitle("3D Prototype");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public void drawLinesOnGameBoard() {

        //draw vertical lines
        for (int i = 0; i < GRID_SQUARES_X; i++) {
            // draw a line from
            // [i*squareLength; 0] to
            // [i*squareLength; boardSizeX]
            Line line = new Line(i * GRID_SQUARE_LENGTH, 0, i * GRID_SQUARE_LENGTH, BOARD_SIZE_X);
            subRootGroup.getChildren().add(line);

        }
        for (int i = 0; i < GRID_SQUARES_X; i++) {
            // draw a horizontal line from
            // [0; i*squareLength] to
            // [boardSizeY; i*squareLength]
            Line line = new Line(0, i * GRID_SQUARE_LENGTH, BOARD_SIZE_Y, i * GRID_SQUARE_LENGTH);
            subRootGroup.getChildren().add(line);

        }
    }
}