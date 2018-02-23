package com.gamefx.engine.util;

import com.gamefx.engine.components.GameActor;
import com.gamefx.engine.components.GenericGameObject;

import static com.gamefx.engine.Constants.*;

public class UtilityScripts {

    public static GameActor buildAndPlaceDefaultPlayer() {

        GameActor player = new GameActor();
        player.setTranslateX(BOARD_SIZE_X/2 + GRID_SQUARE_LENGTH/2);
        player.setTranslateY(BOARD_SIZE_Y/2 + GRID_SQUARE_LENGTH/2);
        player.setTranslateZ(player.getTranslateZ()/2 - 1);

        return player;
    }
}
