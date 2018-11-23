/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan1pl.animations.data;

import org.bukkit.Location;

/**
 *
 * @author Eriol_Eandur
 */
public interface IFrame {
  
    public int getSizeX();
    public int getSizeY();
    public int getSizeZ();

    public void show();
    public void show(int offsetX, int offsetY, int offsetZ);
    public boolean isInside(Location location, int offsetX, int offsetY, int offsetZ);
    public Selection toSelection();
    public Location getCenter();

}
