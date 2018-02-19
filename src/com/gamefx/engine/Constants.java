/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamefx.engine;

/**
 * @author cascau
 */
public interface Constants {


    double GRID_SQUARES_X = 100;
    double GRID_SQUARES_Y = 100;
    double GRID_SQUARE_LENGTH = 20;
    double BOARD_SIZE_X = GRID_SQUARES_X * GRID_SQUARE_LENGTH;
    double BOARD_SIZE_Y = GRID_SQUARES_Y * GRID_SQUARE_LENGTH;

    double CAMERA_INITIAL_DISTANCE_X = 200;
    double CAMERA_INITIAL_DISTANCE_Y = 200;
    double CAMERA_INITIAL_DISTANCE_Z = -700;
    double CAMERA_INITIAL_X_ANGLE = 30.0;
    double CAMERA_INITIAL_Y_ANGLE = 45.0;
    double CAMERA_INITIAL_Z_ANGLE = 45.0;
    double CAMERA_NEAR_CLIP = 0.1;
    double CAMERA_FAR_CLIP = 10000.0;
    double AXIS_LENGTH = 250.0;
    double MOUSE_SPEED = 0.1;
    double ROTATION_SPEED = 2.0;
    double TRACK_SPEED = 0.3;
    double MODIFIER = 10;
}
