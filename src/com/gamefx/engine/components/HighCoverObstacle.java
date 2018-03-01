package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.DEFAULT_HIGH_OBSTACLE_HEIGHT;
import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class HighCoverObstacle extends Obstacle {

    public HighCoverObstacle() {
        this(0,0);
    }

    public HighCoverObstacle(int initialX, int initialY) {

        super(initialX, initialY);
        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, DEFAULT_HIGH_OBSTACLE_HEIGHT);
        getChildren().add(entity);
        entity.setHeight(DEFAULT_HIGH_OBSTACLE_HEIGHT);
        // color it red
        entity.setMaterial(Materials.createMaterial(Color.RED));
        System.out.println(entity.getTranslateZ());
    }
}
