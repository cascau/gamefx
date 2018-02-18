package com.gamefx.main.testframes;

import com.gamefx.engine.CameraTransform;
import com.gamefx.engine.EngineUtils;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import static com.gamefx.engine.Constants.*;

public class rotate3Dwithpanel extends Application {

    private double mouseOldX, mouseOldY = 0, mousePosX = 0, mousePosY = 0, mouseDeltaX = 0, mouseDeltaY = 0, modifier = 10;
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
    protected final CameraTransform cameraXForm = new CameraTransform();
    protected final CameraTransform cameraXForm2 = new CameraTransform();
    protected final CameraTransform cameraXForm3 = new CameraTransform();
    protected final CameraTransform axisGroup = new CameraTransform();

    Group subRoot;
    SubScene subScene;

    private Rectangle gameBoard = new Rectangle(BOARD_SIZE_X, BOARD_SIZE_Y);

    @Override
    public void start(Stage stage) {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        Box myBox = new Box(20, 20, 40);
        myBox.setTranslateX(500);
        myBox.setTranslateY(500);
        myBox.setTranslateZ(1);
        myBox.setMaterial(redMaterial);

        gameBoard.setFill(Color.DARKGRAY);

        EngineUtils.buildAxes(axisGroup);

        Rectangle minimap = new Rectangle();
        minimap.setX(0);
        minimap.setY(0);
        minimap.setWidth(300);
        minimap.setHeight(300);
        minimap.setFill(Color.GREY);
        AnchorPane minimapAnchor = new AnchorPane(minimap);
        AnchorPane.setBottomAnchor(minimapAnchor, 0.0);
        AnchorPane.setRightAnchor(minimapAnchor, 0.0);


        // to Set pivot points
//        rotateX.setPivotX(400);
//        rotateX.setPivotY(300);
//        rotateX.setPivotZ(400);
//
//        rotateY.setPivotX(400);
//        rotateY.setPivotY(300);
//        rotateY.setPivotZ(400);
//
//        rotateZ.setPivotX(400);
//        rotateZ.setPivotY(300);
//        rotateZ.setPivotZ(400);


        // initialize the camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-2000);

        Group root = new Group();
        subRoot = new Group();


        Scene scene = new Scene(root, 1600, 1000, false);
        subScene = new SubScene(subRoot, 1600, 1000, true, SceneAntialiasing.BALANCED);

        root.getChildren().add(subScene);

        subScene.setCamera(camera);
        subRoot.getChildren().addAll(myBox, gameBoard, axisGroup);
        subRoot.setTranslateX(350);
        subRoot.setTranslateY(50);
        root.getChildren().add(minimapAnchor);

        root.getChildren().add(cameraXForm);
        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
//        cameraXForm.ry.setAngle(30);
        cameraXForm.rx.setAngle(30);
//        cameraXForm.rz.setAngle(30);

        // events for rotation
        root.setOnMousePressed(event -> {
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
        });

        subScene.setOnMouseDragged(event -> {

            if (event.isPrimaryButtonDown()) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = event.getSceneX();
                mousePosY = event.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                System.out.println("OldXY " + mouseOldX + " " + mouseOldY);
                System.out.println("PosXY " + mousePosX + " " + mousePosY);

                cameraXForm2.t.setX(cameraXForm2.t.getX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED * 10);
                cameraXForm2.t.setY(cameraXForm2.t.getY() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED * 10);

            } else if (event.isSecondaryButtonDown()) {

//                rotateX.setPivotX(subScene.getWidth() / 2);
//                rotateY.setPivotY(subScene.getHeight() / 2);

//                rotateX.setAngle(rotateX.getAngle() - (event.getSceneX() - mouseOldX));
//                rotateZ.setAngle(rotateZ.getAngle() + (event.getSceneX() - mouseOldX));
//                mouseOldX = event.getSceneX();
//                mouseOldY = event.getSceneY();
            }
        });

        minimap.setOnMousePressed(event -> {
            double x = minimap.getX() - event.getSceneX();
            double y = minimap.getY() - event.getSceneY();
//            System.out.println(x + " " + y);
        });

        subScene.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            subScene.setScaleX(subScene.getScaleX() * zoomFactor);
            subScene.setScaleY(subScene.getScaleY() * zoomFactor);
            event.consume();
//            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        });

        drawLinesOnGameBoard();

        stage.setTitle("3D Prototype");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public void drawLinesOnGameBoard() {

        //draw vertical lines
        for (int i=0; i<GRID_SQUARES_X; i++) {
            // draw a line from
            // [i*squareLength; 0] to
            // [i*squareLength; boardSizeX]
            Line line = new Line(i * GRID_SQUARE_LENGTH, 0, i * GRID_SQUARE_LENGTH, BOARD_SIZE_X);
            subRoot.getChildren().add(line);

        }
        for (int i=0; i<GRID_SQUARES_X; i++) {
            // draw a horizontal line from
            // [0; i*squareLength] to
            // [boardSizeY; i*squareLength]
            Line line = new Line(0, i * GRID_SQUARE_LENGTH, BOARD_SIZE_Y, i * GRID_SQUARE_LENGTH);
            subRoot.getChildren().add(line);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}