/* 
 *  Copyright (C) 2016 Ivan1pl
 * 
 *  This file is part of Animations.
 * 
 *  Animations is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Animations is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Animations.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ivan1pl.animations.data;

import java.io.Serializable;
import lombok.Getter;
import org.bukkit.Location;

/**
 *
 * @author Ivan1pl
 */
public class Selection implements Serializable {
    
    @Getter
    private AnimationsLocation point1;
    
    @Getter
    private AnimationsLocation point2;
    
    private boolean validate() {
        return (point1 != null && point2 != null && point1.getWorld().getUID().equals(point2.getWorld().getUID()));
    }
    
    public static boolean isValid(Selection sel) {
        boolean ret = sel == null ? false : sel.validate();
        return ret;
    }
    
    public void setPoint1(Location loc) {
        point1 = AnimationsLocation.fromLocation(loc);
    }
    
    public void setPoint2(Location loc) {
        point2 = AnimationsLocation.fromLocation(loc);
    }
    
    public int getVolume() {
        if (!validate()) {
            return 0;
        }
        
        int dx = Math.abs(point1.getBlockX() - point2.getBlockX()) + 1;
        int dy = Math.abs(point1.getBlockY() - point2.getBlockY()) + 1;
        int dz = Math.abs(point1.getBlockZ() - point2.getBlockZ()) + 1;
        return dx*dy*dz;
    }
    
    public void expand(int dx, int dy, int dz) {
        if (dx < 0) {
            if (point1.getBlockX() < point2.getBlockX()) {
                point1.add(dx, 0, 0);
            } else {
                point2.add(dx, 0, 0);
            }
        } else {
            if (point1.getBlockX() < point2.getBlockX()) {
                point2.add(dx, 0, 0);
            } else {
                point1.add(dx, 0, 0);
            }
        }
        
        if (dy < 0) {
            if (point1.getBlockY() < point2.getBlockY()) {
                point1.add(0, dy, 0);
            } else {
                point2.add(0, dy, 0);
            }
        } else {
            if (point1.getBlockY() < point2.getBlockY()) {
                point2.add(0, dy, 0);
            } else {
                point1.add(0, dy, 0);
            }
        }
        
        if (dz < 0) {
            if (point1.getBlockZ() < point2.getBlockZ()) {
                point1.add(0, 0, dz);
            } else {
                point2.add(0, 0, dz);
            }
        } else {
            if (point1.getBlockZ() < point2.getBlockZ()) {
                point2.add(0, 0, dz);
            } else {
                point1.add(0, 0, dz);
            }
        }
    }
    
}
