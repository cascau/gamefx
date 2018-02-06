/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.engine;

import com.gamefx.engine.components.GridSquare;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cascau
 */
public class GameUtils {


    public static List<Point2D> buildRoad(Point2D A, Point2D B) {

        final List<Point2D> road = new ArrayList<>();

        int dx = (int) (B.getX() - A.getX());
        int dy = (int) (B.getY() - A.getY());

        int min = Math.min(Math.abs(dx), Math.abs(dy));
        int max = Math.max(Math.abs(dx), Math.abs(dy));

        int x = 0, y = 0;
        Point2D P = null;
        // Go diagonal from A to P (flex point)
        for (int i = 0; i <= min; i++) {
            x = (int) (A.getX() + i * Math.signum(dx));
            y = (int) (A.getY() + i * Math.signum(dy));
            P = new Point2D(x, y);
            road.add(P);
        }

        // Go in a straight line from P to B
        if (P != null) {
            Point2D Q;
            if (P.getX() > B.getX()) { // move left
                for (int i = (int) P.getX(); i >= (int) B.getX(); i--) {
                    Q = new Point2D(i, P.getY());
                    road.add(Q);
                }
            } else if (P.getX() < B.getX()) { //move right
                for (int i = (int) P.getX(); i <= (int) B.getX(); i++) {
                    Q = new Point2D(i, P.getY());
                    road.add(Q);
                }
            } else if (P.getY() > B.getY()) { // move up
                for (int i = (int) P.getY(); i >= (int) B.getY(); i--) {
                    Q = new Point2D(P.getX(), i);
                    road.add(Q);
                }
            } else if (P.getY() < B.getY()) { //move down
                for (int i = (int) P.getY(); i <= (int) B.getY(); i++) {
                    Q = new Point2D(P.getX(), i);
                    road.add(Q);
                }
            }
        }
        return road;
    }

    public static GridSquare getGridFromCoordinates(GridSquare[][] matrix, Point2D coords) {

        int i = (int) coords.getX();
        int j = (int) coords.getY();

        return matrix[i][j];
    }
    public static Point2D getPositionInMatrix(GridSquare target, BorderPane parent) {

        Bounds boundsInScene = target.localToScene(target.getBoundsInLocal());
        Bounds firstSquareBounds = parent.localToScene(parent.getBoundsInLocal());
        Point2D first = new Point2D(firstSquareBounds.getMinX(), firstSquareBounds.getMinY());
        Point2D second = new Point2D(boundsInScene.getMinX(), boundsInScene.getMinY());
        int dx = (int) ((second.getX() - first.getX()) / target.getWidth());
        int dy = (int) ((second.getY() - first.getY()) / target.getHeight());
        System.out.println("dxdy = [" + dx + "; " + dy + "]");
        return Point2D.ZERO;
    }

    public static Point2D getCenterOfRectangle(Bounds rect) {

        double width = rect.getWidth();
        double height = rect.getHeight();

        return new Point2D(rect.getMinX() + width / 2, rect.getMinY() + height / 2);
    }

    public static void unselectAllGameSquares(GridSquare[][] squares) {

        for (GridSquare[] row : squares) {
            for (GridSquare square : row) {
                square.unselect(30);
            }
        }
    }

    public static void selectAllGameSquares(GridSquare[][] squares) {

        for (GridSquare[] row : squares) {
            for (GridSquare square : row) {
                square.select();
            }
        }
    }

    public static void highlightSquares(GridSquare[][] squares, Point2D center, int range) {

        int startX = (int) center.getX();
        int startY = (int) center.getY();

        int rowStart = (startX - range < 0) ? 0 : startX - range;
        int rowEnd = (startX + range >= squares.length - 1) ? (squares.length - 1) : (startX + range);

        int colStart = (startY - range < 0) ? 0 : startY - range;
        int colEnd = (startY + range >= squares.length - 1) ? (squares.length - 1) : (startY + range);

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {

                Point2D position = new Point2D(i, j);
                int distance = optimalDistance2D(center, position);

                if (distance > 0 && distance <= range + 1) {
                    squares[i][j].highlight();
                }
            }
        }
    }

    public static void unhighlightSquares(GridSquare[][] squares, Point2D center, int range) {

        unselectAllGameSquares(squares);
    }

    public static int optimalDistance2D(Point2D start, Point2D end) {

        return Math.abs((int) start.getX() - (int) end.getX()) + Math.abs((int) start.getY() - (int) end.getY());
    }
}
