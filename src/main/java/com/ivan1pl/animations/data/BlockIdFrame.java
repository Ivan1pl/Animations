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

import com.mcmiddleearth.pluginutil.LegacyMaterialUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Stairs;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class BlockIdFrame implements Serializable, IFrame {
    
    private static final long serialVersionUID = 2764396372346413554L;

    private int sizeX;
    private int sizeY;
    private int sizeZ;
    private int x;
    private int y;
    private int z;
    transient private UUID worldId;
    
    private String worldName;
    
    
    private transient BlockData[] blockMaterials;
    private Integer[] blockId;
    private Byte[] blockData;
    
    private BlockIdFrame() { 
    }
    
    @Override
    public boolean isOutdated() {
        return true;
    }
    
    @Override
    public void show() {
        show(0,0,0);
    }
    
    @Override
    public void show(int offsetX, int offsetY, int offsetZ) {
        throw new UnsupportedOperationException("Showing BlockIdFrames is no longer supported in 1.13."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isInside(Location location, int offsetX, int offsetY, int offsetZ) {
        return (location.getBlockX() >= x + offsetX && location.getBlockX() < x + sizeX + offsetX &&
                location.getBlockY() >= y + offsetY && location.getBlockY() <= y + sizeY + offsetY &&
                location.getBlockZ() >= z + offsetZ && location.getBlockZ() < z + sizeZ + offsetZ);
    }
    
    public static BlockIdFrame fromSelection(Selection s) {
        if (Selection.isValid(s)) {
            throw new UnsupportedOperationException("Creating BlockIdFrames from Selections is no longer supported in 1.13."); //To change body of generated methods, choose Tools | Templates.
        }
        return null;
    }
    
    @Override
    public Selection toSelection() {
        Selection s = new Selection();
        s.setPoint1(new Location(Bukkit.getServer().getWorld(worldName), x, y, z));
        s.setPoint2(new Location(Bukkit.getServer().getWorld(worldName), x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1));
        return s;
    }

    @Override
    public Location getCenter() {
        return new Location(Bukkit.getWorld(worldName), x + sizeX/2., y + sizeY/2., z + sizeZ/2.);
    }

    public void setBlocks(Material[] blockMaterials, Byte[] blockData) {
            throw new UnsupportedOperationException("Creating BlockIdFrames from is no longer supported in 1.13."); //To change body of generated methods, choose Tools | Templates.
    }

    public void init() {
        blockMaterials = new BlockData[blockId.length];
        for(int i=0; i<blockId.length;i++) {
            blockMaterials[i] = LegacyMaterialUtil.getBlockData(blockId[i],blockData[i]);
        }
        initialize();
        //second pass to fix fences depending on stairs
        initialize();
    }
    
    private void initialize() {
        for (int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                for (int k = 0; k < sizeZ; ++k) {
                    BlockData data = blockMaterials[i*(sizeY)*(sizeZ) + j*(sizeZ) + k];
                    if(data instanceof Stairs || data instanceof Fence) {
                        BlockData west = getNeighbour(i-1,j,k);
                        BlockData east = getNeighbour(i+1,j,k);
                        BlockData north = getNeighbour(i,j,k-1);
                        BlockData south = getNeighbour(i,j,k+1);
                        if(data instanceof Fence) {
                            connectFence(data,west,BlockFace.EAST);
                            connectFence(data,east,BlockFace.WEST);
                            connectFence(data,north,BlockFace.SOUTH);
                            connectFence(data,south,BlockFace.NORTH);
                        }
                        if(data instanceof Stairs) {
                            Stairs stair =  (Stairs)data;
                            switch(stair.getFacing()) {
                                case NORTH:
                                    connectStair(stair, north, south, stair.getFacing());
                                    break;
                                case SOUTH:
                                    connectStair(stair, south, north, stair.getFacing());
                                    break;
                                case WEST:
                                    connectStair(stair, west, east, stair.getFacing());
                                    break;
                                case EAST:
                                    connectStair(stair, east, west, stair.getFacing());
                                    break;
                            }
                            
                        }
                    }
                }
            }
        }
    }
    
    private void connectFence(BlockData data, BlockData neighbour, BlockFace face) {
        if(neighbour.getMaterial().isOccluding()
                || neighbour instanceof Fence
                || (neighbour instanceof Stairs 
                        && (((Stairs)neighbour).getFacing().equals(face)
                                 && !isOneShapeOf(neighbour,Stairs.Shape.OUTER_LEFT,Stairs.Shape.OUTER_RIGHT)
                           || rotate(((Stairs)neighbour).getFacing(),3).equals(face)
                                 && isOneShapeOf(neighbour,Stairs.Shape.INNER_LEFT)
                           || rotate(((Stairs)neighbour).getFacing(),1).equals(face)
                                 && isOneShapeOf(neighbour,Stairs.Shape.INNER_RIGHT)))) {
            ((Fence)data).setFace(rotate(face,2), true);
        }
    }
    
    private boolean isOneShapeOf(BlockData data, Stairs.Shape... shapes) {
        for(Stairs.Shape shape: shapes) {
            if(((Stairs)data).getShape().equals(shape)) {
                return true;
            }
        }
        return false;
    }
    
    private void connectStair(Stairs stair, BlockData neighbour, BlockData oppNeighbour, BlockFace blockFace) {
        if(neighbour instanceof Stairs) {
            if(((Stairs)neighbour).getFacing().equals(rotate(blockFace,3))) {
                stair.setShape(Stairs.Shape.OUTER_LEFT);
            } else if(((Stairs)neighbour).getFacing().equals(rotate(blockFace,1))) {
                stair.setShape(Stairs.Shape.OUTER_RIGHT);
            }
        }
        if(oppNeighbour instanceof Stairs) {
            if(((Stairs)oppNeighbour).getFacing().equals(rotate(blockFace,3))) {
                stair.setShape(Stairs.Shape.INNER_LEFT);
            } else if(((Stairs)oppNeighbour).getFacing().equals(rotate(blockFace,1))) {
                stair.setShape(Stairs.Shape.INNER_RIGHT);
            }
        }
    }
    
    private BlockFace rotate(BlockFace face, int repetitions) {
        if(repetitions <= 0) {
            return face;
        }
        BlockFace result;
        switch(face) {
            case NORTH:
                result = BlockFace.EAST;
                break;
            case EAST:
                result = BlockFace.SOUTH;
                break;
            case SOUTH:
                result = BlockFace.WEST;
                break;
            default:
                result = BlockFace.NORTH;
                break;
        }
        return rotate(result, repetitions-1);
    }
    
    private BlockData getNeighbour(int i, int j, int k) {
        if(i>=0 && i<sizeX && j>=0 && j<sizeY && k>=0 && k<sizeZ) {
            int index = i*(sizeY)*(sizeZ) + j*(sizeZ) + k;
            BlockData result = blockMaterials[index];
            return result;
        } else {
            BlockData result =  Bukkit.getWorld(worldName).getBlockAt(x+i, y+j, z+k).getBlockData();
            return result;
            
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public int getSizeZ() {
        return sizeZ;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public UUID getWorldId() {
        return worldId;
    }

    public BlockData[] getBlockMaterials() {
        return blockMaterials;
    }

    public Integer[] getBlockId() {
        return blockId;
    }

    public Byte[] getBlockData() {
        return blockData;
    }
}
