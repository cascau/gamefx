package com.gamefx.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

public class TestFrame extends Application {

    public Scene createScene() {
        BorderPane borderPane = new BorderPane();

        //Top content
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(new Button("Home"));
        toolbar.getItems().add(new Button("Options"));
        toolbar.getItems().add(new Button("Help"));
        borderPane.setTop(toolbar);

        //Left content
        Label label1 = new Label("Left hand");
        Button leftButton = new Button("left");
        VBox leftVbox = new VBox();
        leftVbox.getChildren().addAll(label1, leftButton);
        borderPane.setLeft(leftVbox);

        Pane centerPane = new Pane();
        centerPane.setStyle("-fx-background-color : coral");
        borderPane.setCenter(centerPane);

        //Right content
        Label rightlabel1 = new Label("Right hand");
        Button rightButton = new Button("right");

        VBox rightVbox = new VBox();
        rightVbox.getChildren().addAll(rightlabel1, rightButton);
        rightVbox.setPrefWidth(150);
        borderPane.setRight(rightVbox);

        //Center content
        Label centerLabel = new Label("Center area.");
        centerLabel.setWrapText(true);

        //Using AnchorPane only to position items in the center
        AnchorPane centerAP = new AnchorPane();
        AnchorPane.setTopAnchor(centerLabel, Double.valueOf(5));
        AnchorPane.setLeftAnchor(centerLabel, Double.valueOf(20));
        centerAP.getChildren().addAll(centerLabel);
        borderPane.setCenter(centerAP);

        //Bottom content
        Label bottomLabel = new Label("At the bottom.");
        borderPane.setBottom(bottomLabel);

        Scene scene = new Scene(borderPane, 1200, 1000, false);
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}