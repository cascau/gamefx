package com.gamefx.scene;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class SceneCalculator {

    private static int getMatrixPosXfromCoordinateX(double value) {
        return (int) (value / GRID_SQUARE_LENGTH);
    }

    private static int getMatrixPosYfromCoordinateY(double value) {
        return (int) (value / GRID_SQUARE_LENGTH);
    }

    public static int getGameSquareFromMouseEventX(double value) {
        return getMatrixPosXfromCoordinateX(value) * (int) GRID_SQUARE_LENGTH + (int) (GRID_SQUARE_LENGTH / 2);
    }

    public static int getGameSquareFromMouseEventY(double value) {
        return getMatrixPosYfromCoordinateY(value) * (int) GRID_SQUARE_LENGTH + (int) (GRID_SQUARE_LENGTH / 2);
    }

    public static Point2D getCenterOfGameSquareFromMouseEvent(MouseEvent event) {

        Point2D destination = new Point2D(
                getGameSquareFromMouseEventX(event.getX()),
                getGameSquareFromMouseEventY(event.getY())
        );
        return destination;
    }

    public static Point2D getGameSquareCenterFromMatrixPosition(int x, int y) {

        return new Point2D(
                x * GRID_SQUARE_LENGTH + GRID_SQUARE_LENGTH / 2,
                y * GRID_SQUARE_LENGTH + GRID_SQUARE_LENGTH / 2);
    }

    public static Point2D getMatrixPositionFromGameSquare(Point2D square) {

        return new Point2D(
                getGameSquareFromMouseEventX(square.getX()),
                getGameSquareFromMouseEventY(square.getY())
        );
    }
}
