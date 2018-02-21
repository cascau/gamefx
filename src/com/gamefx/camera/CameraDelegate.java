package com.gamefx.camera;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import static com.gamefx.engine.Constants.*;

public class CameraDelegate {

    public final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    public final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    public final Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    public final CameraTransform axisGroup = new CameraTransform();
    public final CameraTransform cameraXForm = new CameraTransform();
    public final CameraTransform cameraXForm2 = new CameraTransform();
    public final CameraTransform cameraXForm3 = new CameraTransform();

    public final PerspectiveCamera camera = new PerspectiveCamera(false);
    public CameraView cameraView;

    public CameraDelegate() {

        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);

        camera.setTranslateX(CAMERA_INITIAL_DISTANCE_X);
        camera.setTranslateY(CAMERA_INITIAL_DISTANCE_Y);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE_Z);

        cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
//        cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
//        cameraXForm.rz.setAngle(CAMERA_INITIAL_Z_ANGLE);


        //add a Point Light for better viewing of the grid coordinate system
        PointLight light = new PointLight(Color.BLUE);

        cameraXForm.getChildren().add(light);
        light.setTranslateX(camera.getTranslateX());
        light.setTranslateY(camera.getTranslateY());
        light.setTranslateZ(camera.getTranslateZ());
    }

    public CameraDelegate(SubScene subScene) {

        this();
        cameraView = new CameraView(subScene);
        cameraView.setFitWidth(350);
        cameraView.setFitHeight(225);
        cameraView.getRx().setAngle(-45);
        cameraView.getT().setZ(-1500);
        cameraView.getT().setY(-500);
    }

    public void initCamera() {

        camera.getTransforms().addAll(rotateX, rotateY, rotateZ);
        camera.setNearClip(0.1);
        camera.setFarClip(1000.0);
        camera.setTranslateZ(0);
    }

    public void moveGameBoard(double mouseDeltaX, double mouseDeltaY) {

        cameraXForm2.t.setX(cameraXForm2.t.getX() - mouseDeltaX * MOUSE_SPEED * MODIFIER * TRACK_SPEED * 10);
        cameraXForm2.t.setY(cameraXForm2.t.getY() - mouseDeltaY * MOUSE_SPEED * MODIFIER * TRACK_SPEED * 10);
    }

    public void rotateGameBoardOnAxisX(double mouseDeltaX, double mouseDeltaY) {
//        System.out.println("mouseDeltaX " + mouseDeltaX + "  mouseDeltaY " + mouseDeltaY);
//                cameraXForm.ry.setAngle(cameraXForm.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * MODIFIER * ROTATION_SPEED * 0.2);
        cameraXForm.rx.setPivotX(BOARD_SIZE_X / 2);
        cameraXForm.rx.setAngle(cameraXForm.rx.getAngle() + (mouseDeltaY) * MOUSE_SPEED * MODIFIER * ROTATION_SPEED * 0.2);
//        System.out.println(cameraXForm.rx.getAngle());
    }

    public void zoomCamera(double deltaY) {

        double zoomFactor = 1.05;
        if (deltaY < 0) {
            zoomFactor = 2.0 - zoomFactor;
        }
        camera.setTranslateZ(camera.getTranslateZ() * zoomFactor + deltaY);
//        System.out.println("Cam trans Z: " + camera.getTranslateZ());
    }

    public void rotateGameBoardPivotInCenter(double mouseDeltaX, double mouseDeltaY) {
        cameraXForm.rx.setPivotX(BOARD_SIZE_X / 2);
        cameraXForm.rx.setPivotY(BOARD_SIZE_Y / 2);

        cameraXForm.ry.setPivotX(BOARD_SIZE_X / 2);
        cameraXForm.ry.setPivotY(BOARD_SIZE_Y / 2);

        double newRxAngle = ((cameraXForm.rx.getAngle() - mouseDeltaY * 2.0) % 360 + 540) % 360 - 180;
//        cameraXForm.ry.setAngle(((cameraXForm.ry.getAngle() + mouseDeltaX * 2.0) % 360 + 540) % 360 - 180); // +
        if ((newRxAngle > 5) && (newRxAngle < 90 - CAMERA_INITIAL_X_ANGLE - 5)) {
            cameraXForm.rx.setAngle(newRxAngle); // -
            System.out.println(cameraXForm.rx.getAngle());
        }
    }

    public void zoomNodeAnimated(Node node, double x, double y, double zoomFactor) {
        // determine scale
        double oldScale = node.getScaleX();
        double scale = oldScale * zoomFactor;
        double f = (scale / oldScale) - 1;

        // determine offset that we will have to move the node
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

        Timeline timeline = new Timeline(60);
        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }

    public void zoomNodeAnimated(Group node, double x, double y, double zoomFactor) {
        // determine scale
        double oldScale = node.getScaleX();
        double scale = oldScale * zoomFactor;
        double f = (scale / oldScale) - 1;

        // determine offset that we will have to move the node
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

        Timeline timeline = new Timeline(60);
        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }
}
