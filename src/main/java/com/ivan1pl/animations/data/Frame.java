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
import org.bukkit.block.BlockState;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class Frame implements Serializable, IFrame {
    
    private static final long serialVersionUID = 2764396372346413554L;

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
    
    
    @Getter
    private Material[] blockMaterials;
    
    @Getter
    private Byte[] blockData;
    
    private Frame() { }
    
    @Override
    public void show() {
        show(0,0,0);
    }
    
    @Override
    public void show(int offsetX, int offsetY, int offsetZ) {
        World w = Bukkit.getWorld(worldId);
        Set<Chunk> chunksToUpdate = new HashSet<>();
        for (int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                for (int k = 0; k < sizeZ; ++k) {
                    Location loc = new Location(w, i+x+offsetX, j+y+offsetY, k+z+offsetZ);
                    Block b = loc.getBlock();
                    Material mat = blockMaterials[i*(sizeY)*(sizeZ) + j*(sizeZ) + k];
                    Byte matData = blockData[i*(sizeY)*(sizeZ) + j*(sizeZ) + k];
                    BlockState state = b.getState();
                    state.setType(mat);
                    state.setRawData(matData);
                    state.update(true, false);
                    //1.13 removed b.setTypeIdAndData(mat.getId(), matData, false);
                    // causes players to become invis for others chunksToUpdate.add(b.getChunk());
                }
            }
        }
        for (Chunk c : chunksToUpdate) {
            if (c.isLoaded()) {
                w.refreshChunk(c.getX(), c.getZ());
            }
        }
    }
    
    @Override
    public boolean isInside(Location location, int offsetX, int offsetY, int offsetZ) {
        return (location.getBlockX() >= x + offsetX && location.getBlockX() < x + sizeX + offsetX &&
                location.getBlockY() >= y + offsetY && location.getBlockY() <= y + sizeY + offsetY &&
                location.getBlockZ() >= z + offsetZ && location.getBlockZ() < z + sizeZ + offsetZ);
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
            f.blockMaterials = new Material[materials.size()];
            f.blockMaterials = materials.toArray(f.blockMaterials);
            f.blockData = new Byte[datas.size()];
            f.blockData = datas.toArray(f.blockData);
            return f;
        }
        return null;
    }
    
    @Override
    public Selection toSelection() {
        Selection s = new Selection();
        s.setPoint1(new Location(Bukkit.getServer().getWorld(worldId), x, y, z));
        s.setPoint2(new Location(Bukkit.getServer().getWorld(worldId), x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1));
        return s;
    }

    @Override
    public Location getCenter() {
        return new Location(Bukkit.getWorld(worldId), x + sizeX/2., y + sizeY/2., z + sizeZ/2.);
    }
    
}
