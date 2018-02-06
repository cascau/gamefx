/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.engine.components;

/**
 *
 * @author cascau
 */
public class GameEntity {
    
    private int vision;
    private GridSquare parent;

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public GridSquare getParent() {
        return parent;
    }

    public void setParent(GridSquare parent) {
        this.parent = parent;
    }
}
