package com.gamefx.engine.util;

import com.gamefx.engine.components.HighCoverObstacle;
import com.gamefx.engine.components.LowCoverObstacle;
import com.gamefx.engine.components.NoCoverObstacle;
import com.gamefx.engine.components.Obstacle;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

public class UtilityScripts {

    public static List<Box> buildGameWorld() {

        List<Box> building = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            HighCoverObstacle highCoverObstacle = new HighCoverObstacle();
//            Point2D destination = SceneCalculator.getGameSquareCenterFromMatrixPosition(0, i);
//            highCoverObstacle.getEntity().setTranslateX(destination.getX());
//            highCoverObstacle.getEntity().setTranslateY(destination.getY());
//            building.add(highCoverObstacle.getEntity());
//
//            destination = SceneCalculator.getGameSquareCenterFromMatrixPosition(1, i);
//            LowCoverObstacle lowCoverObstacle = new LowCoverObstacle();
//            lowCoverObstacle.getEntity().setTranslateX(destination.getX());
//            lowCoverObstacle.getEntity().setTranslateY(destination.getY());
//            building.add(lowCoverObstacle.getEntity());
//
//            destination = SceneCalculator.getGameSquareCenterFromMatrixPosition(2, i);
//            NoCoverObstacle noCoverObstacle = new NoCoverObstacle();
//            noCoverObstacle.getEntity().setTranslateX(destination.getX());
//            noCoverObstacle.getEntity().setTranslateY(destination.getY());
//            building.add(noCoverObstacle.getEntity());
//        }

        return building;
    }

    public static List<Obstacle> createGameWorldFromMatrix(int[][] config) {

        List<Obstacle> result = new ArrayList<>();

        System.out.println("loading map configuration configuration:");
        for (int i=0; i<config.length; i++) {
            for (int j=0; j<config[i].length; j++) {

                switch (config[i][j]) {
                    case 1: {
                        result.add(new NoCoverObstacle(i,j));
                        break;
                    }
                    case 2: {
                        result.add(new LowCoverObstacle(i, j));
                        break;
                    }
                    case 3: {
                        result.add(new HighCoverObstacle(i, j));
                        break;
                    }
                }

                System.out.print(config[i][j] + " ");
            }
        System.out.println();
        }

        return result;
    }
}
