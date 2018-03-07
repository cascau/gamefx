package com.gamefx.engine.components;

import com.gamefx.scene.components.Materials;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;

import static com.gamefx.engine.Constants.DEFAULT_CHARACTER_HEIGHT;
import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class GameActor extends GenericObject implements Moveable {

    private Circle movementCircle = null;

    private double viewDistance = 20 * GRID_SQUARE_LENGTH;
    private double movementDistance = 5 * GRID_SQUARE_LENGTH;

    private boolean selected = false;

    public GameActor() {
        this(0, 0);
    }

    public GameActor(int startX, int startY) {

//        super(startX, startY);
        entity = new Box(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, DEFAULT_CHARACTER_HEIGHT);
        getChildren().add(entity);
        entity.translateZProperty().setValue(-entity.getHeight() / 2);

        // color it red
        entity.setMaterial(Materials.createMaterial(Color.PALEVIOLETRED));
    }



    @Override
    public void moveTo(Point2D destination) {

    }

    @Override
    public void select() {
        movementCircle = new Circle(entity.getTranslateX(), entity.getTranslateY(), movementDistance + GRID_SQUARE_LENGTH / 2);
        // color it yellow
        movementCircle.setStroke(Color.DARKVIOLET);
        movementCircle.setFill(Color.TRANSPARENT);
        movementCircle.setTranslateZ(+5);
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

    public Box getEntity() {
        return entity;
    }

    public boolean isSelected() {
        return selected;
    }
}
