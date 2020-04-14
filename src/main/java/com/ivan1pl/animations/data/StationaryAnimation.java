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

import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class StationaryAnimation extends Animation implements Serializable {
    
    private static final long serialVersionUID = -3315164220067048965L;

    private final List<IFrame> frames = new ArrayList<>();
    
    @Getter
    private final Selection selection;
    
    //transient private EditSession session;
    
    public StationaryAnimation(Selection selection) throws InvalidSelectionException {
        if (!Selection.isValid(selection)) {
            throw new InvalidSelectionException();
        }
        this.selection = selection;
        //createSession();
    }
    
    public void addFrame() {
        IFrame f = MCMEStoragePlotFrame.fromSelection(selection);
        frames.add(f);
    }
    
    public boolean removeFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            return false;
        }
        
        showFrame(0);
        frames.remove(index);
        return true;
    }
    
    @Override
    public boolean showFrame(int index) {
//Logger.getGlobal().info("showFrame a "+index);
        if (index < 0 || index >= frames.size()) {
            return false;
        }
        
//Logger.getGlobal().info("showFrame b "+index);
        frames.get(index).show();
        return true;
    }
    
    @Override
    public int getFrameCount() {
        return frames.size();
    }
    
    public boolean swapFrames(int i1, int i2) {
        if (i1 < 0 || i1 >= frames.size() || i2 < 0 || i2 >= frames.size()) {
            return false;
        }
        
        IFrame f1 = frames.get(i1);
        IFrame f2 = frames.get(i2);
        
        frames.set(i1, f2);
        frames.set(i2, f1);
        return true;
    }
    
    @Override
    public boolean isPlayerInRange(Player player, int range) {
        return player.getWorld().equals(selection.getPoint1().getWorld()) ?
                selection.getDistance(player.getLocation()) <= range : false;
    }

    @Override
    public int getSizeInBlocks() {
        return selection.getVolume();
    }

    @Override
    protected Location getCenter() {
        return selection.getCenter();
    }

    public boolean updateFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            return false;
        }

        frames.set(index, MCMEStoragePlotFrame.fromSelection(selection));
        return true;
    }
    
    @Override
    public void saveTo(File folder, ObjectOutputStream out) throws IOException {
        super.saveTo(folder, out);
        if(frames.size()>0 && frames.get(0) instanceof MCMEStoragePlotFrame) {
            if(!folder.exists()) {
                folder.mkdir();
            }
            for(int i = 0; i< frames.size(); i++) {
                ((MCMEStoragePlotFrame)frames.get(i)).save(new File(folder,"frame_"+i+".mcme"));
            }
        }
    }
   
    @Override
    public boolean prepare(File folder) {
        /*if(!createSession()) {
            Logger.getLogger(MovingAnimation.class.getName()).log(Level.WARNING,
                             "Error while loading Animation "+folder.getName()+": Missing World");
            return false;
        }*/
        if(!folder.exists()) {
            folder.mkdir();
        }
        for(int i=0;i<frames.size();i++) {
            IFrame frame = frames.get(i);
//Logger.getGlobal().info("Init frame: "+i);
            if(frame instanceof MCMEStoragePlotFrame) {
                ((MCMEStoragePlotFrame)frame).load(new File(folder,"frame_"+i+".mcme"));
            }
                if(frame instanceof BlockIdFrame) {
//Logger.getGlobal().info("Init BlockIdFrame. ");
                ((BlockIdFrame)frame).init();
            }
    }
        for(int i=0;i<frames.size();i++) {
            IFrame frame = frames.get(i);
            if(frame instanceof BlockIdFrame) {
                MCMEStoragePlotFrame update = MCMEStoragePlotFrame.fromSelection(frame.toSelection());
                //update.saveSchematic(new File(folder,"frame_"+i+".schem"));
                update.setBlocks(((BlockIdFrame)frame).getBlockMaterials());
                frames.set(i, update);
            }
        }
        return true;
    }
    
    /*private boolean createSession() {
        World bukkitWorld = selection.getCenter().getWorld();
        if(bukkitWorld==null) {
            return false;
        }
        com.sk89q.worldedit.world.World world = new BukkitWorld(bukkitWorld);
        //session = FaweAPI.getEditSessionBuilder(world).build();
        session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
        return true;
    }*/

    
}
