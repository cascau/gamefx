package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class HighObstacle extends Obstacle {

    private Box entity;

    public HighObstacle() {

        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH * 0.75);

        // move to the first square
        setTranslateX(GRID_SQUARE_LENGTH / 2);
        setTranslateY(GRID_SQUARE_LENGTH / 2);
        setTranslateZ(-GRID_SQUARE_LENGTH * 2);

        // color it red
        entity.setMaterial(Materials.redMaterial());
    }

    public Box getEntity() {
        return entity;
    }
}
