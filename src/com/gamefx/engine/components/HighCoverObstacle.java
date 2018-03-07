package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.*;

public class HighCoverObstacle extends Obstacle {

    public HighCoverObstacle() {
        this(0, 0);
    }

    public HighCoverObstacle(int initialX, int initialY) {

        super(initialX, initialY);
        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, DEFAULT_HIGH_OBSTACLE_HEIGHT);
        getChildren().add(entity);
        entity.translateZProperty().setValue(-entity.getHeight() / 2);

        // color it red
        entity.setMaterial(Materials.createMaterial(Color.BROWN));
    }
}