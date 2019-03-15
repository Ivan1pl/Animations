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

import com.mcmiddleearth.pluginutil.plotStoring.IStoragePlot;
import com.mcmiddleearth.pluginutil.plotStoring.InvalidRestoreDataException;
import com.mcmiddleearth.pluginutil.plotStoring.MCMEPlotFormat;
import com.mcmiddleearth.pluginutil.plotStoring.StoragePlotSnapshot;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;


/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class MCMEStoragePlotFrame implements Serializable, IFrame, IStoragePlot {
    
    private static final long serialVersionUID = 1L;

    transient private byte[] frameNBTData;
    
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
    private String worldName;
    
    @Override
    public boolean isOutdated() {
        return false;
    }
    
    @Override
    public void show() {
        show(0,0,0);
    }
    
    @Override
    public void show(int offsetX, int offsetY, int offsetZ) {
        try(DataInputStream in = new DataInputStream(new ByteArrayInputStream(frameNBTData))) {
            new MCMEPlotFormat().load(this.getLowCorner().add(new Vector(offsetX, offsetY, offsetZ)),null, in);
        }   catch (IOException | InvalidRestoreDataException ex) {
            Logger.getLogger(MCMEStoragePlotFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override 
    public boolean isInside(Location location) {
        return isInside(location, 0,0,0);
    }
    
    @Override
    public boolean isInside(Location location, int offsetX, int offsetY, int offsetZ) {
        return (location.getBlockX() >= x + offsetX && location.getBlockX() < x + sizeX + offsetX &&
                location.getBlockY() >= y + offsetY && location.getBlockY() <= y + sizeY + offsetY &&
                location.getBlockZ() >= z + offsetZ && location.getBlockZ() < z + sizeZ + offsetZ);
    }
    
    public static MCMEStoragePlotFrame fromSelection(Selection s) {
        if (Selection.isValid(s)) {
            MCMEStoragePlotFrame f = new MCMEStoragePlotFrame();
            int x1 = Math.min(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int x2 = Math.max(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int y1 = Math.min(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int y2 = Math.max(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int z1 = Math.min(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            int z2 = Math.max(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            f.worldName = s.getPoint1().getWorld().getName();
            f.x = x1;
            f.y = y1;
            f.z = z1;
            f.sizeX = x2 - x1 + 1;
            f.sizeY = y2 - y1 + 1;
            f.sizeZ = z2 - z1 + 1;

            try(ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(byteOut)) {
                new MCMEPlotFormat().save(f, out);
                out.flush();
                out.close();
                byteOut.flush();
                byteOut.close();
                f.frameNBTData=byteOut.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(MCMEStoragePlotFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return f;
        }
        return null;
    }
    
    @Override
    public Selection toSelection() {
        Selection s = new Selection();
        s.setPoint1(new Location(Bukkit.getWorld(worldName), x, y, z));
        s.setPoint2(new Location(Bukkit.getWorld(worldName), x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1));
        return s;
    }

    @Override
    public Location getCenter() {
        return new Location(Bukkit.getWorld(worldName), x + sizeX/2., y + sizeY/2., z + sizeZ/2.);
    }
    
    public void setBlocks(BlockData[] blockData) {
        if(Bukkit.getWorld(worldName) == null) {
            return;
        }
        for(int i=0;i<blockData.length;i++) {
            if(blockData[i] instanceof Fence) {
//Logger.getGlobal().info("Fence: \n"+blockData[i].getAsString());
            }
        }
        try(ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteOut)) {
            new MCMEPlotFormat().save(this, out, 
                                      new StoragePlotSnapshotFaksimile(this, blockData));
            out.flush();
            out.close();
            byteOut.flush();
            byteOut.close();
            frameNBTData=byteOut.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(MCMEStoragePlotFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(File file) {
        try(BufferedOutputStream out = new BufferedOutputStream(
                                   new GZIPOutputStream(
                                   new FileOutputStream(file)))) {
            out.write(frameNBTData);
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(MCMEStoragePlotFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load(File file) {
        try(BufferedInputStream in = new BufferedInputStream(
                                 new GZIPInputStream(
                                 new FileInputStream(file)));
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int readBytes = 0;
            do {
                readBytes = in.read(buffer,0,buffer.length);
                out.write(buffer, 0, readBytes);
            } while(readBytes == buffer.length);
            frameNBTData = out.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(MCMEStoragePlotFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    @Override
    public Location getLowCorner() {
        return new Location(Bukkit.getWorld(worldName),x,y,z);
    }

    @Override
    public Location getHighCorner() {
        return new Location(Bukkit.getWorld(worldName),x+sizeX-1,y+sizeY-1,z+sizeZ-1);
    }
    
    public static class StoragePlotSnapshotFaksimile extends StoragePlotSnapshot {
        
    //private final Material[] blockMaterials;
    private final BlockData[] blockData;
    
    private final IStoragePlot plot;
       
        public StoragePlotSnapshotFaksimile(IStoragePlot plot,
                                            BlockData[] blockData) {
            super(plot);
            this.plot = plot;
            //this.blockMaterials = blockMaterials;
            this.blockData = blockData;
        }
        
        @Override
        public BlockData getBlockData(int x, int y, int z) {
            int xMin = plot.getLowCorner().getBlockX();
            int yMin = plot.getLowCorner().getBlockY();
            int zMin = plot.getLowCorner().getBlockZ();
            int xSize = plot.getHighCorner().getBlockX()-plot.getLowCorner().getBlockX()+1;
            int ySize = plot.getHighCorner().getBlockY()-plot.getLowCorner().getBlockY()+1;
            int zSize = plot.getHighCorner().getBlockZ()-plot.getLowCorner().getBlockZ()+1;
            //Material mat = blockMaterials[(x-xMin)*(ySize)*(zSize) + (y-yMin)*(zSize) + z-zMin];
            return blockData[(x-xMin)*(ySize)*(zSize) + (y-yMin)*(zSize) + z-zMin];
            //BlockState state = plot.getWorld().getBlockAt(0, 1, 0).getState();
            //state.setType(mat);
            //state.setRawData(matData);
            //return state.getBlockData();
        }
        
        @Override
        public Biome getBiome(int x, int z) {
///Logger.getGlobal().info("GetBiome at: "+x+" "+(x%16));
            return Biome.PLAINS;
        }
        
        @Override
        public int getMaxY(int x, int z) {
            return 255;
        }
        
        @Override
        public List<BlockState> getTileEntities() {
            return new ArrayList<>();
        }
        
        @Override
        public List<Entity> getEntities() {
            return new ArrayList<>();
        }
        
        @Override
        public ChunkSnapshot[][] getChunks() {
            return new ChunkSnapshot[0][0];
        }
        
    }
    
}
