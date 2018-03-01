package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.DEFAULT_NO_OBSTACLE_HEIGHT;
import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class NoCoverObstacle extends Obstacle {

    public NoCoverObstacle() {
        this(0,0);
    }

    public NoCoverObstacle(int initialX, int initialY) {

        super(initialX, initialY);
        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, DEFAULT_NO_OBSTACLE_HEIGHT);
        getChildren().add(entity);
//        entity.setTranslateZ(-entity.getHeight() / 2);
        setTranslateZ(-entity.getHeight());

        // color it red
        entity.setMaterial(Materials.createMaterial(Color.BLUE));
    }
}