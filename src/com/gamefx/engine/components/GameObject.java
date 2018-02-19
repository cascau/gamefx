package com.gamefx.engine.components;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import static com.gamefx.engine.Constants.GRID_SQUARE_LENGTH;

public class GameObject extends Box {


    public GameObject() {

        super(GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH, GRID_SQUARE_LENGTH * 2);

        // move to the first square
        setTranslateX(GRID_SQUARE_LENGTH / 2);
        setTranslateY(GRID_SQUARE_LENGTH / 2);
        setTranslateZ(-GRID_SQUARE_LENGTH * 2);

        // color it red
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.DARKRED);
        redMaterial.setDiffuseColor(Color.RED);
        setMaterial(redMaterial);
    }

}
