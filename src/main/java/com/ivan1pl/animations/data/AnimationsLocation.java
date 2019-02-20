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

import com.sk89q.worldedit.Vector;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class AnimationsLocation implements Serializable {
    
    private static final long serialVersionUID = 5482328410797364959L;

    @Getter
    @Setter
    private double x;
    
    @Getter
    @Setter
    private double y;
    
    @Getter
    @Setter
    private double z;
    
    private UUID worldId;
    
    public AnimationsLocation(World world, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldId = world == null ? null : world.getUID();
    }
    
    public int getBlockX() {
        return Location.locToBlock(x);
    }
    
    public int getBlockY() {
        return Location.locToBlock(y);
    }
    
    public int getBlockZ() {
        return Location.locToBlock(z);
    }
    
    public void setWorld(World world) {
        this.worldId = world == null ? null : world.getUID();
    }
    
    public World getWorld() {
        //return worldId == null || Bukkit.getWorld(worldId)== null? Bukkit.getWorld("world") : Bukkit.getWorld(worldId);
        return Bukkit.getWorld("world");
    }
    
    public static AnimationsLocation fromLocation(Location loc) {
        return loc == null ? null : new AnimationsLocation(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
    }
    
    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    public static boolean isSameBlock(AnimationsLocation l1, AnimationsLocation l2) {
        if (l1 == null || l2 == null) {
            return false;
        }
        
        return l1.worldId.equals(l2.worldId) && l1.getBlockX() == l2.getBlockX()
                && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ();
    }
    
    public Vector getVector() {
        return new Vector(x,y,z);
    }
}
