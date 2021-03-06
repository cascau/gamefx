package com.gamefx.engine.components;

import static com.gamefx.engine.Constants.GRID_SQUARES_X;
import static com.gamefx.engine.Constants.GRID_SQUARES_Y;

public class MatrixScene {

    public static int[][] MAP_BORS_VASILE   =  new int[GRID_SQUARES_X][GRID_SQUARES_Y];
    static {
        MAP_BORS_VASILE[0]  = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[1]  = new int[]{0,3,3,3,3,0,0,0,3,3,3,3,0,0,0,0,3,3,0,0,1,1,1,1,1,1,1,1,1,0};
        MAP_BORS_VASILE[2]  = new int[]{0,3,3,3,3,0,0,0,3,3,3,3,0,0,0,0,3,3,0,0,1,1,1,1,1,1,1,1,1,0};
        MAP_BORS_VASILE[3]  = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0,0,1,1,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[4]  = new int[]{0,2,2,0,0,3,3,3,3,3,3,0,2,2,0,2,2,2,0,0,1,1,0,0,2,2,0,0,0,0};
        MAP_BORS_VASILE[5]  = new int[]{0,2,2,0,0,3,3,3,3,3,3,0,2,2,0,0,0,0,0,0,1,1,0,0,2,2,2,2,0,0};
        MAP_BORS_VASILE[6]  = new int[]{0,0,0,0,0,3,3,3,3,3,3,0,0,0,0,0,2,0,0,0,0,0,0,0,2,2,2,2,0,0};
        MAP_BORS_VASILE[7]  = new int[]{0,0,0,0,0,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[8]  = new int[]{0,0,0,0,0,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0};
        MAP_BORS_VASILE[9]  = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,0,0,0,0};
        MAP_BORS_VASILE[10] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[11] = new int[]{0,0,2,2,0,0,0,2,2,0,0,0,2,2,0,1,0,0,0,0,1,1,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[12] = new int[]{0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,1,0,0,0,0,1,1,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[13] = new int[]{0,1,1,1,1,0,2,0,0,0,0,1,1,1,1,1,0,0,1,1,1,1,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[14] = new int[]{0,0,0,0,0,0,2,0,2,0,0,0,0,0,0,0,0,0,1,1,1,1,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[15] = new int[]{0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,1,1,0,0,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[16] = new int[]{0,0,2,2,2,2,0,0,2,0,0,1,1,1,1,1,0,0,1,1,0,0,0,3,3,3,0,0,0,0};
        MAP_BORS_VASILE[17] = new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[18] = new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,0,0,0,0};
        MAP_BORS_VASILE[19] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0};
        MAP_BORS_VASILE[20] = new int[]{0,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[21] = new int[]{0,3,3,3,3,3,3,0,0,0,2,2,2,0,0,0,3,3,3,0,0,0,0,0,0,0,3,3,3,3};
        MAP_BORS_VASILE[22] = new int[]{0,3,3,3,3,3,3,0,0,0,2,2,2,0,0,0,3,3,3,0,0,2,2,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[23] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,2,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[24] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,3,3,3,0,0,2,0,0,3,3,3,3,0,0};
        MAP_BORS_VASILE[25] = new int[]{0,0,0,0,0,0,0,0,0,3,3,3,3,0,2,0,3,3,3,0,0,2,0,0,3,3,3,3,0,0};
        MAP_BORS_VASILE[26] = new int[]{0,3,3,3,3,3,3,0,0,3,3,3,3,0,0,0,3,3,3,0,0,2,0,0,3,3,3,3,0,0};
        MAP_BORS_VASILE[27] = new int[]{0,3,3,3,3,3,3,0,0,3,3,3,3,0,0,0,0,0,0,0,0,2,0,0,3,3,3,3,0,0};
        MAP_BORS_VASILE[28] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        MAP_BORS_VASILE[29] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    }

}
