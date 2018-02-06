/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.main;

import com.gamefx.engine.TXForm;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author cascau
 */
public class Beginner extends Application {

    protected final Group root = new Group();
    protected final TXForm axisGroup = new TXForm();
    protected final TXForm moleculeGroup = new TXForm();
    protected final TXForm world = new TXForm();
    protected final PerspectiveCamera camera = new PerspectiveCamera(true);
    protected final TXForm cameraXForm = new TXForm();
    protected final TXForm cameraXForm2 = new TXForm();
    protected final TXForm cameraXForm3 = new TXForm();

    protected double mousePosX;
    protected double mousePosY;
    protected double mouseOldX;
    protected double mouseOldY;
    protected double mouseDeltaX;
    protected double mouseDeltaY;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        Rectangle rectangle = new Rectangle(15, 15, 100, 200);
        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
