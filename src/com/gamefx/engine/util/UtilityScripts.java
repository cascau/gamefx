package com.gamefx.engine.util;

import com.gamefx.engine.components.HighCoverObstacle;
import com.gamefx.engine.components.LowCoverObstacle;
import com.gamefx.engine.components.NoCoverObstacle;
import com.gamefx.engine.components.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class UtilityScripts {

    public static List<Obstacle> createGameWorldFromMatrix(int[][] config) {

        List<Obstacle> result = new ArrayList<>();

        System.out.println("loading map configuration configuration:");
        for (int i=0; i<config.length; i++) {
            for (int j=0; j<config[i].length; j++) {

                switch (config[j][i]) {
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
