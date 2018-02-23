package com.gamefx.engine;

import com.gamefx.engine.components.GenericGameObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class EngineDelegate {

    public void moveGameObjetToPoint(GenericGameObject entity, Point2D destination) {

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
