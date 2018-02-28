package com.gamefx.scene.components;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Materials {

    public static PhongMaterial redMaterial() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.BROWN);
        redMaterial.setDiffuseColor(Color.LIGHTBLUE);
        return redMaterial;
    }

    public static PhongMaterial brownMaterial() {
        final PhongMaterial brownMaterial = new PhongMaterial();
        brownMaterial.setSpecularColor(Color.BROWN);
        brownMaterial.setDiffuseColor(Color.LIGHTBLUE);
        return brownMaterial;
    }

    public static PhongMaterial blueMaterial() {
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        return blueMaterial;
    }

    public static PhongMaterial greenMaterial() {
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        return greenMaterial;
    }
}
