package com.gamefx.scene.components;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Materials {

    public static PhongMaterial createMaterial(Color color) {
        final PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.TRANSPARENT);
        material.setDiffuseColor(color);
        return material;
    }
//
//    public static PhongMaterial redMaterial() {
//        final PhongMaterial redMaterial = new PhongMaterial();
//        redMaterial.setSpecularColor(Color.RED);
//        redMaterial.setDiffuseColor(Color.RED);
//        return redMaterial;
//    }
//
//    public static PhongMaterial brownMaterial() {
//        final PhongMaterial brownMaterial = new PhongMaterial();
//        brownMaterial.setSpecularColor(Color.BROWN);
//        brownMaterial.setDiffuseColor(Color.BROWN);
//        return brownMaterial;
//    }
//
//    public static PhongMaterial blueMaterial() {
//        final PhongMaterial blueMaterial = new PhongMaterial();
//        blueMaterial.setDiffuseColor(Color.BLUE);
//        blueMaterial.setSpecularColor(Color.BLUE);
//        return blueMaterial;
//    }
//
//    public static PhongMaterial greenMaterial() {
//        final PhongMaterial greenMaterial = new PhongMaterial();
//        greenMaterial.setDiffuseColor(Color.GREEN);
//        greenMaterial.setSpecularColor(Color.GREEN);
//        return greenMaterial;
//    }
//
//    public static PhongMaterial pinkMaterial() {
//        final PhongMaterial greenMaterial = new PhongMaterial();
//        greenMaterial.setDiffuseColor(Color.PINK);
//        greenMaterial.setSpecularColor(Color.PINK);
//        return greenMaterial;
//    }
}
