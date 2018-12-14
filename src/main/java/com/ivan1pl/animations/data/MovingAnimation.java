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

import com.boydti.fawe.FaweAPI;
import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class MovingAnimation extends Animation implements Serializable {
    
    private static final long serialVersionUID = -3124628769457473325L;
    
    private IFrame frame;
    
    private IFrame background;
    
    @Getter
    private final Selection selection;
    
    @Getter
    @Setter
    private int stepX;
    
    @Getter
    @Setter
    private int stepY;
    
    @Getter
    @Setter
    private int stepZ;
    
    @Getter
    @Setter
    private int maxDistance;
    
    transient private EditSession session;
    
    public MovingAnimation(Selection selection) throws InvalidSelectionException {
        if (!Selection.isValid(selection)) {
            throw new InvalidSelectionException();
        }
        this.selection = selection;
        createSession();
        frame = WorldEditFrame.fromSelection(selection,session);
        background = WorldEditFrame.fromSelection(selection,session);
    }
    
    public void updateBackground() {
Logger.getGlobal().info("Update Background 1");
        Selection s = SerializationUtils.clone(selection);
Logger.getGlobal().info("Update Background 2");
        s.expand(stepX*getFrameCount(), stepY*getFrameCount(), stepZ*getFrameCount());
Logger.getGlobal().info("Update Background 3");
        background = WorldEditFrame.fromSelection(s,session);
Logger.getGlobal().info("Update Background 4");
    }

    @Override
    public boolean showFrame(int index) {
        if (index < 0 || index >= getFrameCount()) {
            return false;
        }
        background.show();
        frame.show(stepX * index, stepY * index, stepZ * index);
        return true;
    }

    @Override
    public int getFrameCount() {
        return maxDistance;
    }
    
    public void movePlayers(int index, boolean reverse) {
        List<Player> allPlayers = selection.getPoint1().getWorld().getPlayers();
        for (Player player : allPlayers) {
            Animations.debug(Messages.DEBUG_MOVING_ANIMATION_CHECKING_PLAYER, this.getClass().getName(), player.getName());
            if (frame.isInside(player.getLocation(), stepX * index, stepY * index, stepZ * index) && player.isOnGround()) {
                Animations.debug(Messages.DEBUG_MOVING_PLAYER, this.getClass().getName(), player.getName());
                if(reverse) { 
                    player.teleport(player.getLocation().add(-stepX, -stepY, -stepZ));
                } else {
                    player.teleport(player.getLocation().add(stepX, stepY, stepZ));
                }
            }
        }
    }

    @Override
    public boolean isPlayerInRange(Player player, int range) {
        Selection s = background.toSelection();
        return player.getWorld().equals(s.getPoint1().getWorld()) ?
                s.getDistance(player.getLocation()) <= range : false;
    }

    @Override
    public int getSizeInBlocks() {
        return frame.getSizeX() * frame.getSizeY() * frame.getSizeZ() +
                background.getSizeX() * background.getSizeY() * background.getSizeZ();
    }

    @Override
    protected Location getCenter() {
        return background.getCenter();
    }
    
    @Override
    public void saveTo(File folder, ObjectOutputStream out) throws IOException {
        super.saveTo(folder, out);
        if(background instanceof WorldEditFrame) {
            if(!folder.exists()) {
                folder.mkdir();
            }
            ((WorldEditFrame)background).saveSchematic(new File(folder,"background.schem"));
        }
        if(frame instanceof WorldEditFrame) {
            if(!folder.exists()) {
                folder.mkdir();
            }
            ((WorldEditFrame)frame).saveSchematic(new File(folder,"frame.schem"));
        }
    }
    
    @Override
    public boolean prepare(File folder) {
        if(!createSession()) {
            Logger.getLogger(MovingAnimation.class.getName()).log(Level.WARNING,
                             "Error while loading Animation "+folder.getName()+": Missing World");
            return false;
        }
        createSession();
        if(!folder.exists()) {
            folder.mkdir();
        }
        if(background instanceof WorldEditFrame) {
            ((WorldEditFrame)background).loadSchematic(session, new File(folder,"background.schem"));
        }
        if(frame instanceof WorldEditFrame) {
            ((WorldEditFrame)frame).loadSchematic(session, new File(folder,"frame.schem"));
        }
        if(background instanceof Frame) {
            WorldEditFrame update = WorldEditFrame.fromSelection(background.toSelection(),session);
            //update.saveSchematic(new File(folder,"background.schem"));
            update.setBlocks(((Frame)background).getBlockMaterials(),((Frame)background).getBlockData());
            background = update;
        }
        if(frame instanceof Frame) {
            WorldEditFrame update = WorldEditFrame.fromSelection(frame.toSelection(),session);
            update.setBlocks(((Frame)frame).getBlockMaterials(),((Frame)frame).getBlockData());
            //update.saveSchematic(new File(folder,"frame.schem"));
            frame = update;
        }
        return true;
    }
    
    private boolean createSession() {
        World bukkitWorld = selection.getCenter().getWorld();
        if(bukkitWorld==null) {
            return false;
        }
        com.sk89q.worldedit.world.World world = new BukkitWorld(bukkitWorld);
        session = FaweAPI.getEditSessionBuilder(world).build();
        return true;
    }

}
