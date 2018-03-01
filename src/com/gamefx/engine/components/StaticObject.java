package com.gamefx.engine.components;


import com.gamefx.engine.EngineDelegate;
import com.gamefx.scene.SceneCalculator;
import javafx.geometry.Point2D;

public abstract class StaticObject extends GenericObject {

    public StaticObject(int x, int y) {
        super(x, y);
        Point2D destination = SceneCalculator.getGameSquareCenterFromMatrixPosition(x, y);
        EngineDelegate.moveGameObjetToPoint(this, destination);
    }

    @Override
    public void select() {

    }

    @Override
    public void deselect() {

    }
}
