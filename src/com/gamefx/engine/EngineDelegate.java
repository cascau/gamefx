package com.gamefx.engine;

import com.gamefx.engine.components.GameObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class EngineDelegate {

    public void moveGameObjetToPoint(GameObject entity, Point2D destination) {

        Timeline timeline = new Timeline(60);
        // timeline that scales and moves the player
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1000), new KeyValue(entity.translateXProperty(), destination.getX())),
                new KeyFrame(Duration.millis(1000), new KeyValue(entity.translateYProperty(), destination.getY())),
                new KeyFrame(Duration.millis(1000), new KeyValue(entity.scaleXProperty(), entity.getScaleX())),
                new KeyFrame(Duration.millis(1000), new KeyValue(entity.scaleYProperty(), entity.getScaleY()))
        );
        timeline.play();
    }
}
