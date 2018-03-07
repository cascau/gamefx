/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.scene;

import com.gamefx.camera.CameraTransform;
import com.gamefx.engine.Constants;
import com.gamefx.scene.components.Materials;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import static com.gamefx.engine.Constants.*;

/**
 * @author cascau
 */
public class DrawSceneDelegate {

    public Rectangle buildMinimap() {

        Rectangle minimap = new Rectangle();
        minimap.setX(1202);
        minimap.setY(0);
        minimap.setWidth(400);
        minimap.setHeight(400);
        minimap.setFill(Color.LIGHTGOLDENRODYELLOW);
        minimap.setStroke(Color.BLACK);

        return minimap;
    }

    public VBox buildInfoGroup() {


        VBox infoGroup = new VBox(2);
        infoGroup.getChildren().add(new Button("Asd"));
        infoGroup.setTranslateX(1200);
        infoGroup.setTranslateY(400);

        return infoGroup;
    }

    public void drawLinesOnMinimap(Rectangle minimap, Group group) {

        double startX = minimap.getX();
        double endX = startX + minimap.getWidth();
        double startY = minimap.getY();
        double endY = startY + minimap.getHeight();
        double stepX = minimap.getWidth() / Constants.GRID_SQUARES_X;
        double stepY = minimap.getHeight() / Constants.GRID_SQUARES_Y;

        for (double i = startX; i<endX; i+=stepX) {
            //draw vertical lines
            Line line = new Line(i, startY, i, endY);
            line.setFill(Color.TRANSPARENT);
            group.getChildren().add(line);
        }
        for (double j = startY; j<endY; j+=stepY) {
            //draw horizontal lines
            Line line = new Line(startX, j, endX, j);
            line.setFill(Color.TRANSPARENT);
            group.getChildren().add(line);
        }
    }

    public CameraTransform buildAxes() {
        System.out.println("buildAxes()");
        final CameraTransform axisGroup = new CameraTransform();

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(Materials.createMaterial(Color.RED));
        yAxis.setMaterial(Materials.createMaterial(Color.GREEN));
        zAxis.setMaterial(Materials.createMaterial(Color.BLUE));

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);

        return axisGroup;
    }

    public void drawFullLinesOnGameBoard(Group subRootGroup) {

        //draw vertical lines
        for (int i = 0; i <= GRID_SQUARES_X; i++) {
            // draw a line from
            // [i*squareLength; 0] to
            // [i*squareLength; boardSizeX]
            Line line = new Line(i * GRID_SQUARE_LENGTH, 0, i * GRID_SQUARE_LENGTH, BOARD_SIZE_X);
            line.setFill(Color.TRANSPARENT);
            subRootGroup.getChildren().add(line);

        }
        for (int j = 0; j <= GRID_SQUARES_Y; j++) {
            // draw a horizontal line from
            // [0; i*squareLength] to
            // [boardSizeY; i*squareLength]
            Line line = new Line(0, j * GRID_SQUARE_LENGTH, BOARD_SIZE_Y, j * GRID_SQUARE_LENGTH);
            line.setFill(Color.TRANSPARENT);
            subRootGroup.getChildren().add(line);

        }
    }

    public void drawSmallLinesOnGameBoard(Group subRootGroup) {

        //draw vertical lines
        for (int i = 0; i < GRID_SQUARES_X; i++) {
            for (int j = 0; j < GRID_SQUARES_Y; j++) {
                // draw a line from
                // [i*squareLength; 0] to
                // [i*squareLength; boardSizeX]
                Line lineX = new Line(i * GRID_SQUARE_LENGTH - 2, j * GRID_SQUARE_LENGTH, i * GRID_SQUARE_LENGTH + 2, j * GRID_SQUARE_LENGTH);
                subRootGroup.getChildren().add(lineX);

                // draw a horizontal line from
                // [0; i*squareLength] to
                // [boardSizeY; i*squareLength]
                Line lineY = new Line(i * GRID_SQUARE_LENGTH, j * GRID_SQUARE_LENGTH - 2, i * GRID_SQUARE_LENGTH, j * GRID_SQUARE_LENGTH + 2);
                subRootGroup.getChildren().add(lineY);
            }


        }
    }
}
