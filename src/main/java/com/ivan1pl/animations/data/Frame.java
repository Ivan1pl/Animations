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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author Ivan1pl
 */
public class Frame implements Serializable {
    
    @Getter
    private int sizeX;
    
    @Getter
    private int sizeY;
    
    @Getter
    private int sizeZ;
    
    @Getter
    private int x;
    
    @Getter
    private int y;
    
    @Getter
    private int z;
    
    @Getter
    private UUID worldId;
    
    private Material[] blockMaterials;
    
    private Byte[] blockData;
    
    private Frame() { }
    
    public void show() {
        World w = Bukkit.getWorld(worldId);
        Set<Chunk> chunksToUpdate = new HashSet<>();
        for (int i = 0; i <= sizeX; ++i) {
            for (int j = 0; j <= sizeY; ++j) {
                for (int k = 0; k <= sizeZ; ++k) {
                    Location loc = new Location(w, i+x, j+y, k+z);
                    Block b = loc.getBlock();
                    Material mat = blockMaterials[i*(sizeY+1)*(sizeZ+1) + j*(sizeZ+1) + k];
                    Byte matData = blockData[i*(sizeY+1)*(sizeZ+1) + j*(sizeZ+1) + k];
                    b.setTypeIdAndData(mat.getId(), matData, false);
                    chunksToUpdate.add(b.getChunk());
                }
            }
        }
        for (Chunk c : chunksToUpdate) {
            if (c.isLoaded()) {
                w.refreshChunk(c.getX(), c.getZ());
            }
        }
    }
    
    public static Frame fromSelection(Selection s) {
        if (Selection.isValid(s)) {
            Frame f = new Frame();
            int x1 = Math.min(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int x2 = Math.max(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int y1 = Math.min(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int y2 = Math.max(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int z1 = Math.min(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            int z2 = Math.max(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            List<Material> materials = new LinkedList<>();
            List<Byte> datas = new LinkedList<>();
            f.worldId = s.getPoint1().getWorld().getUID();
            f.x = x1;
            f.y = y1;
            f.z = z1;
            f.sizeX = x2 - x1 + 1;
            f.sizeY = y2 - y1 + 1;
            f.sizeZ = z2 - z1 + 1;
            for (int x = x1; x <= x2; ++x) {
                for (int y = y1; y <= y2; ++y) {
                    for (int z = z1; z <= z2; ++z) {
                        Location loc = new Location(s.getPoint1().getWorld(), x, y, z);
                        Block b = loc.getBlock();
                        materials.add(b.getType());
                        datas.add(b.getData());
                    }
                }
            }
            f.blockMaterials = (Material[]) materials.toArray();
            f.blockData = (Byte[]) datas.toArray();
            return f;
        }
        return null;
    }
    
}
