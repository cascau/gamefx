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

    private Rectangle gameBoard = new Rectangle();

    @Override
    public void start(Stage stage) throws Exception {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);

        Box myBox = new Box(100, 100, 100);
        myBox.setTranslateX(400);
        myBox.setTranslateY(300);
        myBox.setTranslateZ(-400);
        myBox.setMaterial(redMaterial);

        gameBoard.setWidth(1000);
        gameBoard.setHeight(1000);
        gameBoard.setTranslateX(100);
        gameBoard.setTranslateY(100);
        gameBoard.setFill(Color.DARKGRAY);

        EngineUtils.buildAxes(axisGroup);

        Rectangle minimap = new Rectangle();
        minimap.setX(200);
        minimap.setY(600);
        minimap.setWidth(300);
        minimap.setHeight(300);
        minimap.setFill(Color.GREY);
        AnchorPane minimapAnchor = new AnchorPane(minimap);
        AnchorPane.setBottomAnchor(minimap, 0.0);
        AnchorPane.setRightAnchor(minimap, 0.0);


        // to Set pivot points
        rotateX.setPivotX(400);
        rotateX.setPivotY(300);
        rotateX.setPivotZ(400);

        rotateY.setPivotX(400);
        rotateY.setPivotY(300);
        rotateY.setPivotZ(400);

        rotateZ.setPivotX(400);
        rotateZ.setPivotY(300);
        rotateZ.setPivotZ(400);


        // initialize the camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-2000);

        Group root = new Group();
        Group subRoot = new Group();


        Scene scene = new Scene(root, 1600, 1000, true);
        SubScene subScene = new SubScene(subRoot, 1600, 1000, true, SceneAntialiasing.BALANCED);

        root.getChildren().add(subScene);

        subScene.setCamera(camera);
        subRoot.getChildren().addAll(myBox, gameBoard, axisGroup);
        root.getChildren().add(minimapAnchor);

        root.getChildren().add(cameraXForm);
        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
//        cameraXForm.ry.setAngle(30);
//        cameraXForm.rx.setAngle(30);
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

                rotateX.setAngle(rotateX.getAngle() - (event.getSceneX() - mouseOldX));
                rotateZ.setAngle(rotateZ.getAngle() + (event.getSceneX() - mouseOldX));
                mouseOldX = event.getSceneX();
                mouseOldY = event.getSceneY();
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
            gameBoard.setScaleX(gameBoard.getScaleX() * zoomFactor);
            gameBoard.setScaleY(gameBoard.getScaleY() * zoomFactor);
            event.consume();
//            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        });

        stage.setTitle("JavaFX 3D Object");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}