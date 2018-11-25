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

import com.boydti.fawe.object.clipboard.CPUOptimizedClipboard;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;


/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class WorldEditFrame implements Serializable, IFrame {
    
    private static final long serialVersionUID = 1L;

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
    
    transient private Schematic schematic;
    transient private EditSession session;
    
    
    public void saveSchematic(File schemFile) { 
        try {
//Logger.getGlobal().info("clipboard: "+schematic.getClipboard().toString());
//Logger.getGlobal().info("region: "+schematic.getClipboard().getRegion().toString());
//Logger.getGlobal().info("world: "+schematic.getClipboard().getRegion().getWorld().toString());
//Logger.getGlobal().info("worlddata: "+schematic.getClipboard().getRegion().getWorld().getWorldData().toString());
            //show();
            schematic.save(schemFile, ClipboardFormat.SCHEMATIC);
        } catch (IOException ex) {
            Logger.getLogger(WorldEditFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadSchematic(EditSession session, File schemFile) { 
        /*com.sk89q.worldedit.world.World world = BukkitUtil.getLocalWorld(s.getCenter().getWorld());
        session = FaweAPI.getEditSessionBuilder(world).build();
        CuboidRegion region = new CuboidRegion(world,s.getPoint1().getVector(),
                                                     s.getPoint2().getVector());
        EditSession copyWorld = new EditSessionBuilder("world").autoQueue(false).build();
        Vector pos1 = new Vector(10, 3, 10);
        Vector pos2 = new Vector(50, 90, 50);
        CuboidRegion copyRegion = new CuboidRegion(pos1, pos2);

        BlockArrayClipboard lazyCopy = copyWorld.lazyCopy(copyRegion);
            schematic = ClipboardFormat.SCHEMATIC.getReader(inputstream);
            Clipboard board = reader.read(worldData);*/
        try {
            schematic = ClipboardFormat.SCHEMATIC.load(schemFile);
            //Region region = schematic.getClipboard().getRegion();
            //region.setWorld(session.getWorld());
            //schematic = new Schematic(region);
            this.session = session;
        } catch (IOException ex) {
            Logger.getLogger(WorldEditFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            /*clipboard = new CuboidClipboard(region.getMaximumPoint()
            .subtract(region.getMinimumPoint()));
            clipboard.copy(session, region);
            worldId = s.getCenter().getWorld().getUID();
            x = region.getMinimumPoint().getBlockX();
            y = region.getMinimumPoint().getBlockY();
            z = region.getMinimumPoint().getBlockZ();
            sizeX = region.getMaximumPoint().getBlockX() - x + 1;
            sizeY = region.getMaximumPoint().getBlockY() - y + 1;
            sizeZ = region.getMaximumPoint().getBlockZ() - z + 1
            //MCEditSchematicFormat format = new MCEditSchematicFormat();
        } catch (IOException ex) {
            Logger.getLogger(WorldEditFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Clipboard board;
        Schematic schem = new Schematic(region);
        this.schemFile = schemFile.getName();
        try {
            schem.save(schemFile, ClipboardFormat.SCHEMATIC);
    }*/
    
    @Override
    public void show() {
        show(0,0,0);
    }
    
    @Override
    public void show(int offsetX, int offsetY, int offsetZ) {
//Logger.getGlobal().info("clipboard: "+schematic.getClipboard().toString());
//Logger.getGlobal().info("region: "+schematic.getClipboard().getRegion().toString());
//Logger.getGlobal().info("world: "+schematic.getClipboard().getRegion().getWorld().toString());
//Logger.getGlobal().info("worlddata: "+schematic.getClipboard().getRegion().getWorld().getWorldData().toString());
//Logger.getGlobal().info("session: "+session.toString());
//Logger.getGlobal().info("maxpoint: "+session.getMaximumPoint().toString());
        schematic.paste(session, new Vector(x+offsetX,y+offsetY,z+offsetZ), true);
        session.flushQueue();
    }
    
    @Override
    public boolean isInside(Location location, int offsetX, int offsetY, int offsetZ) {
        return (location.getBlockX() >= x + offsetX && location.getBlockX() < x + sizeX + offsetX &&
                location.getBlockY() >= y + offsetY && location.getBlockY() <= y + sizeY + offsetY &&
                location.getBlockZ() >= z + offsetZ && location.getBlockZ() < z + sizeZ + offsetZ);
    }
    
    public static WorldEditFrame fromSelection(Selection s, EditSession session) {
        if (Selection.isValid(s)) {
            WorldEditFrame f = new WorldEditFrame();
            int x1 = Math.min(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int x2 = Math.max(s.getPoint1().getBlockX(), s.getPoint2().getBlockX());
            int y1 = Math.min(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int y2 = Math.max(s.getPoint1().getBlockY(), s.getPoint2().getBlockY());
            int z1 = Math.min(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            int z2 = Math.max(s.getPoint1().getBlockZ(), s.getPoint2().getBlockZ());
            f.worldId = s.getPoint1().getWorld().getUID();
            f.x = x1;
            f.y = y1;
            f.z = z1;
            f.sizeX = x2 - x1 + 1;
            f.sizeY = y2 - y1 + 1;
            f.sizeZ = z2 - z1 + 1;

            f.session = session;
            CuboidRegion region = new CuboidRegion(session.getWorld(),s.getPoint1().getVector(),
                                                         s.getPoint2().getVector());
//Logger.getGlobal().info("fromSelection region world: "+region.getWorld().toString());

            BlockArrayClipboard clipboard = session.lazyCopy(region);
//Logger.getGlobal().info("fromSelection clipboard world: "+clipboard.getRegion().getWorld().toString());
            f.schematic = new Schematic(region);
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
    
    public void setBlocks(Material[] blockMaterials, Byte[] blockData) {
        Region region = new CuboidRegion(session.getWorld(),new Vector(x,y,z),new Vector(x+sizeX-1,y+sizeY-1,z+sizeZ-1));
        Clipboard clipboard = new BlockArrayClipboard(region,new CPUOptimizedClipboard(sizeX, sizeY,sizeZ));
        
//Logger.getGlobal().info(sizeX+" "+sizeY + " "+sizeZ);
//Logger.getGlobal().info("Materials length: " +blockMaterials.length);

        for (int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                for (int k = 0; k < sizeZ; ++k) {
//if(i+1==sizeX) Logger.getGlobal().info(i+" "+j+" "+k+" - "+sizeX+" "+sizeY + " "+sizeZ);
                    Material mat = blockMaterials[i*(sizeY)*(sizeZ) + j*(sizeZ) + k];
                    Byte matData = blockData[i*(sizeY)*(sizeZ) + j*(sizeZ) + k];
                    BaseBlock block = new BaseBlock(mat.getId(), matData);
                    try {
                        clipboard.setBlock(x+i, y+j, z+k, block);
                    } catch (WorldEditException ex) {
                        Logger.getLogger(WorldEditFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        schematic = new Schematic(clipboard);
    }
    
}
