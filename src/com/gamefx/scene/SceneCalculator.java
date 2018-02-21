package com.gamefx.scene;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class SceneCalculator {

    public int getMatrixPosXfromCoordinateX(double value) {
        return (int) (value / GRID_SQUARE_LENGTH);
    }

    public int getMatrixPosYfromCoordinateY(double value) {
        return (int) (value / GRID_SQUARE_LENGTH);
    }

    public int getGameSquareFromMouseEventX(double value) {
        return getMatrixPosXfromCoordinateX(value) * (int) GRID_SQUARE_LENGTH + (int) (GRID_SQUARE_LENGTH / 2);
    }

    public int getGameSquareFromMouseEventY(double value) {
        return getMatrixPosYfromCoordinateY(value) * (int) GRID_SQUARE_LENGTH + (int) (GRID_SQUARE_LENGTH / 2);
    }

    public Point2D getCenterOfGameSquareFromMouseEvent(MouseEvent event) {

        Point2D destination = new Point2D(
                getGameSquareFromMouseEventX(event.getX()),
                getGameSquareFromMouseEventY(event.getY())
        );
        return destination;
    }
}
