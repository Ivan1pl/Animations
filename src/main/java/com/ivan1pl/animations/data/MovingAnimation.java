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
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public class MovingAnimation extends Animation implements Serializable {
    
    private Frame frame;
    
    private Frame background;
    
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
    
    public MovingAnimation(Selection selection) throws InvalidSelectionException {
        if (!Selection.isValid(selection)) {
            throw new InvalidSelectionException();
        }
        this.selection = selection;
        frame = Frame.fromSelection(selection);
        background = Frame.fromSelection(selection);
    }
    
    public void updateBackground() {
        Selection s = SerializationUtils.clone(selection);
        s.expand(stepX*getFrameCount(), stepY*getFrameCount(), stepZ*getFrameCount());
        background = Frame.fromSelection(s);
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
    
    public void movePlayers(int index) {
        List<Player> allPlayers = selection.getPoint1().getWorld().getPlayers();
        for (Player player : allPlayers) {
            Animations.debug(Messages.DEBUG_MOVING_ANIMATION_CHECKING_PLAYER, this.getClass().getName(), player.getName());
            if (frame.isInside(player.getLocation(), stepX * index, stepY * index, stepZ * index) && player.isOnGround()) {
                Animations.debug(Messages.DEBUG_MOVING_PLAYER, this.getClass().getName(), player.getName());
                player.teleport(player.getLocation().add(stepX, stepY, stepZ));
            }
        }
    }

    @Override
    public boolean isPlayerInRange(Player player) {
        //TODO implement this
        return false;
    }

    @Override
    public boolean isAnyPlayerInRange() {
        //TODO implement this
        return false;
    }
    
}
