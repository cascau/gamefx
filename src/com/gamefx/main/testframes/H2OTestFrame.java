/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.main.testframes;

import com.gamefx.engine.Constants;
import com.gamefx.engine.TXForm;
import com.gamefx.main.GameWindow;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author cascau
 */
public class H2OTestFrame extends GameWindow {

    @Override
    public void start(Stage primaryStage) {

        // setUserAgentStylesheet(STYLESHEET_MODENA);
        System.out.println("start()");

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        // buildScene();
        buildCamera();
//        buildAxes();
        buildMolecule();

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);

        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);
    }

    private void buildMolecule() {
        //======================================================================
        // THIS IS THE IMPORTANT MATERIAL FOR THE TUTORIAL
        //======================================================================

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        // Molecule Hierarchy
        // [*] moleculeXForm
        //     [*] oxygenXForm
        //         [*] oxygenSphere
        //     [*] hydrogen1SideXForm
        //         [*] hydrogen1XForm
        //             [*] hydrogen1Sphere
        //         [*] bond1Cylinder
        //     [*] hydrogen2SideXForm
        //         [*] hydrogen2XForm
        //             [*] hydrogen2Sphere
        //         [*] bond2Cylinder
        TXForm moleculeXForm = new TXForm();
        TXForm oxygenXForm = new TXForm();
        TXForm hydrogen1SideXForm = new TXForm();
        TXForm hydrogen1XForm = new TXForm();
        TXForm hydrogen2SideXForm = new TXForm();
        TXForm hydrogen2XForm = new TXForm();

        Sphere oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);

        Sphere hydrogen1Sphere = new Sphere(30.0);
        hydrogen1Sphere.setMaterial(whiteMaterial);
        hydrogen1Sphere.setTranslateX(0.0);

        Sphere hydrogen2Sphere = new Sphere(30.0);
        hydrogen2Sphere.setMaterial(whiteMaterial);
        hydrogen2Sphere.setTranslateZ(0.0);

        Cylinder bond1Cylinder = new Cylinder(5, 100);
        bond1Cylinder.setMaterial(greyMaterial);
        bond1Cylinder.setTranslateX(50.0);
        bond1Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond1Cylinder.setRotate(90.0);

        Cylinder bond2Cylinder = new Cylinder(5, 100);
        bond2Cylinder.setMaterial(greyMaterial);
        bond2Cylinder.setTranslateX(50.0);
        bond2Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond2Cylinder.setRotate(90.0);

        moleculeXForm.getChildren().add(oxygenXForm);
        moleculeXForm.getChildren().add(hydrogen1SideXForm);
        moleculeXForm.getChildren().add(hydrogen2SideXForm);
        oxygenXForm.getChildren().add(oxygenSphere);
        hydrogen1SideXForm.getChildren().add(hydrogen1XForm);
        hydrogen2SideXForm.getChildren().add(hydrogen2XForm);
        hydrogen1XForm.getChildren().add(hydrogen1Sphere);
        hydrogen2XForm.getChildren().add(hydrogen2Sphere);
        hydrogen1SideXForm.getChildren().add(bond1Cylinder);
        hydrogen2SideXForm.getChildren().add(bond2Cylinder);

        hydrogen1XForm.setTx(100.0);
        hydrogen2XForm.setTx(100.0);
        hydrogen2SideXForm.setRotateY(Constants.HYDROGEN_ANGLE);

        moleculeGroup.getChildren().add(moleculeXForm);

        world.getChildren().addAll(moleculeGroup);
    }

    public static void main(String[] args) {
        launch(args);
    }
}