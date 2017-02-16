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
package com.ivan1pl.animations.events;

import com.ivan1pl.animations.data.Animation;
import lombok.Getter;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Ivan1pl
 */
public abstract class AnimationEvent extends org.bukkit.event.Event {
    
    private static final HandlerList handlers = new HandlerList();
    
    @Getter
    private final Animation animation;
    
    public AnimationEvent(Animation animation) {
        this.animation = animation;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
