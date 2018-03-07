package com.gamefx.engine;

import com.gamefx.engine.components.GenericObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class EngineDelegate {

    public static void moveGameObjetToPoint(GenericObject entity, Point2D destination) {

        if (entity != null && destination != null) {
            double speed = 0.2;
            double distance = destination.distance(new Point2D(entity.getTranslateX(), entity.getTranslateY()));
            double time = distance / speed;

            Timeline timeline = new Timeline(60);
            // timeline that scales and moves the player
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(time), new KeyValue(entity.translateXProperty(), destination.getX())),
                    new KeyFrame(Duration.millis(time), new KeyValue(entity.translateYProperty(), destination.getY())),
                    new KeyFrame(Duration.millis(time), new KeyValue(entity.scaleXProperty(), entity.getScaleX())),
                    new KeyFrame(Duration.millis(time), new KeyValue(entity.scaleYProperty(), entity.getScaleY()))
            );
            timeline.play();
        }
    }

    public static Bounds getBounds(Rectangle gameBoard) {

        return gameBoard.localToScene(gameBoard.getBoundsInParent());
    }
}
