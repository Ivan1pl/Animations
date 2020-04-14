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

import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import com.ivan1pl.animations.utils.SerializationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

//import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public class MovingAnimation extends Animation implements Serializable {
    
    private static final long serialVersionUID = -3124628769457473325L;
    
    private IFrame frame;
    
    private IFrame background;
    
    private final Selection selection;
    
    private int stepX;
    private int stepY;
    private int stepZ;
    private int maxDistance;
    
    public MovingAnimation(Selection selection) throws InvalidSelectionException {
        if (!Selection.isValid(selection)) {
            throw new InvalidSelectionException();
        }
        this.selection = selection;
        frame = MCMEStoragePlotFrame.fromSelection(selection);
        background = MCMEStoragePlotFrame.fromSelection(selection);
    }
    
    public void updateBackground() {
        Selection s = SerializationUtils.clone(selection);
        s.expand(stepX*getFrameCount(), stepY*getFrameCount(), stepZ*getFrameCount());
        background = MCMEStoragePlotFrame.fromSelection(s);
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
        if(background instanceof MCMEStoragePlotFrame) {
            if(!folder.exists()) {
                folder.mkdir();
            }
            ((MCMEStoragePlotFrame)background).save(new File(folder,"background.mcme"));
        }
        if(frame instanceof MCMEStoragePlotFrame) {
            if(!folder.exists()) {
                folder.mkdir();
            }
            ((MCMEStoragePlotFrame)frame).save(new File(folder,"frame.mcme"));
        }
    }
    
    @Override
    public boolean prepare(File folder) {
        if(!folder.exists()) {
            folder.mkdir();
        }
        if(background instanceof MCMEStoragePlotFrame) {
            ((MCMEStoragePlotFrame)background).load(new File(folder,"background.mcme"));
        }
        if(frame instanceof MCMEStoragePlotFrame) {
            ((MCMEStoragePlotFrame)frame).load(new File(folder,"frame.mcme"));
        }
        if(background instanceof BlockIdFrame) {
            ((BlockIdFrame)background).init();
        }
        if(frame instanceof BlockIdFrame) {
            ((BlockIdFrame)frame).init();
        }
        if(background instanceof BlockIdFrame) {
            MCMEStoragePlotFrame update = MCMEStoragePlotFrame.fromSelection(background.toSelection());
            update.setBlocks(((BlockIdFrame)background).getBlockMaterials());
            background = update;
        }
        if(frame instanceof BlockIdFrame) {
            MCMEStoragePlotFrame update = MCMEStoragePlotFrame.fromSelection(frame.toSelection());
            update.setBlocks(((BlockIdFrame)frame).getBlockMaterials());
            frame = update;
        }
        return true;
    }

    public Selection getSelection() {
        return selection;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    public int getStepZ() {
        return stepZ;
    }

    public void setStepZ(int stepZ) {
        this.stepZ = stepZ;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}
