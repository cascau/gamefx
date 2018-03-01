package com.gamefx.engine.components;

import com.gamefx.scene.SceneCalculator;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Box;

public abstract class GenericObject extends Group implements Selectable {

    protected Box entity;

    public GenericObject() {
        this(0,0);
    }

    public GenericObject(int initialX, int initialY) {

        // move to the first square
        Point2D firstMatrixPosition = SceneCalculator.getGameSquareCenterFromMatrixPosition(initialX, initialY);
        setTranslateX(firstMatrixPosition.getX());
        setTranslateY(firstMatrixPosition.getY());
    }

    public Box getEntity() {
        return entity;
    }
}
