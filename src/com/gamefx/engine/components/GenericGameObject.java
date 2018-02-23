package com.gamefx.engine.components;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public abstract class GenericGameObject extends Group implements Selectable {

    protected boolean selected;

    public GenericGameObject() {

    }

    public boolean isSelected() {
        return selected;
    }
}
