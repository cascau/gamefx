/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.scene;

import com.gamefx.camera.CameraTransform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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
        minimap.setX(0);
        minimap.setY(0);
        minimap.setWidth(300);
        minimap.setHeight(300);
        minimap.setFill(Color.YELLOW);
        minimap.setStroke(Color.BLACK);

        return minimap;
    }

    public CameraTransform buildAxes() {
        System.out.println("buildAxes()");
        final CameraTransform axisGroup = new CameraTransform();

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

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
            subRootGroup.getChildren().add(line);

        }
        for (int j = 0; j <= GRID_SQUARES_Y; j++) {
            // draw a horizontal line from
            // [0; i*squareLength] to
            // [boardSizeY; i*squareLength]
            Line line = new Line(0, j * GRID_SQUARE_LENGTH, BOARD_SIZE_Y, j * GRID_SQUARE_LENGTH);
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