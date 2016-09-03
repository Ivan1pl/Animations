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

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public abstract class BaseRangeTrigger implements Trigger {
    
    @Getter
    @Setter
    private int range;
    
    @Getter
    @Setter
    private boolean started;
    
    @Getter
    @Setter
    private boolean finished;
    
    protected final boolean isPlayerInRange(Player player, Animation animation) {
        return animation.isPlayerInRange(player);
    }
    
    protected final  boolean isAnyPlayerInRange(Animation animation) {
        return animation.isAnyPlayerInRange();
    }
    
}
