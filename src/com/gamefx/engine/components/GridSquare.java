/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.engine.components;

import com.gamefx.engine.GameUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author cascau
 */
public class GridSquare extends StackPane {

    private boolean clicked = false;
    private Point2D positionInMatrix;
    private Point2D positionInGame2D;
    private GridSquare[][] parentMatrix;

    private GameEntity entity;

    public GridSquare() {
        super.setStyle("-fx-background-color: white;");
        super.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void select() {

        this.setClicked(true);
        super.setStyle("-fx-background-color: red;");
    }

    public void unselect(int range) {

        this.setClicked(false);
        this.setStyle("-fx-background-color: white;");
    }

    public void highlight() {
        this.setClicked(false);
        super.setStyle("-fx-background-color: lightblue;");
    }

    public Point2D getPositionInMatrix() {
        return positionInMatrix;
    }

    public void setPositionInMatrix(Point2D positionInMatrix) {
        this.positionInMatrix = positionInMatrix;
    }

    public GridSquare[][] getParentMatrix() {
        return parentMatrix;
    }

    public void setParentMatrix(GridSquare[][] parentMatrix) {
        this.parentMatrix = parentMatrix;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public void setEntity(GameEntity entity) {
        this.entity = entity;
    }

    public Point2D getPositionInGame2D() {
        return positionInGame2D;
    }

    public void setPositionInGame2D(Point2D positionInGame2D) {
        this.positionInGame2D = positionInGame2D;
    }

    public Point2D getCenter() {

        Bounds boundsInScene = this.localToScene(this.getBoundsInLocal());
        return GameUtils.getCenterOfRectangle(boundsInScene);
    }
}
