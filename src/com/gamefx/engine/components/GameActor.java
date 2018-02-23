package com.gamefx.engine.components;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class GameActor extends GenericGameObject implements Moveable {

    private Box entity;

    private Circle movementCircle = null;

    private double viewDistance = 20 * GRID_SQUARE_LENGTH;
    private double movementDistance = 5 * GRID_SQUARE_LENGTH;

    public GameActor() {

        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH * 2);
        getChildren().add(entity);

        // move to the first square
        setTranslateX(GRID_SQUARE_LENGTH / 2);
        setTranslateY(GRID_SQUARE_LENGTH / 2);
        setTranslateZ(-GRID_SQUARE_LENGTH * 2);

        // color it red
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.DARKRED);
        redMaterial.setDiffuseColor(Color.RED);
        entity.setMaterial(redMaterial);
    }

    @Override
    public void moveTo(Point2D destination) {

    }

    @Override
    public void select() {
        movementCircle = new Circle(entity.getTranslateX(), entity.getTranslateY(), movementDistance);
        // color it yellow
        movementCircle.setStroke(Color.DARKVIOLET);
        movementCircle.setFill(Color.TRANSPARENT);
        movementCircle.setTranslateZ(+25);
        getChildren().add(movementCircle);
        selected = true;
    }

    @Override
    public void deselect() {
        getChildren().remove(movementCircle);
        movementCircle = null;
        selected = false;
    }

    public double getViewDistance() {
        return viewDistance;
    }

    public double getMovementDistance() {
        return movementDistance;
    }

    public Circle getMovementCircle() {
        return movementCircle;
    }
}
