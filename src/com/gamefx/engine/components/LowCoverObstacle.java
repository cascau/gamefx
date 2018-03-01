package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.DEFAULT_LOW_OBSTACLE_HEIGHT;
import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class LowCoverObstacle extends Obstacle {

    public  LowCoverObstacle() {
        this(0,0);
    }

    public LowCoverObstacle(int x, int y) {

        super(x, y);
        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, DEFAULT_LOW_OBSTACLE_HEIGHT);
        getChildren().add(entity);
        entity.setTranslateZ(-entity.getHeight() / 2);
//        setTranslateZ(-entity.getHeight() / 2);

        // color it red
        entity.setMaterial(Materials.createMaterial(Color.LIMEGREEN));
    }
}
