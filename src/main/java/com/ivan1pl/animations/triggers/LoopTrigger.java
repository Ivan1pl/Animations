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
package com.ivan1pl.animations.triggers;

import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.events.Event;
import com.ivan1pl.animations.events.EventListener;

/**
 *
 * @author Ivan1pl
 */
public class LoopTrigger extends BaseRangeTrigger {
    
    public LoopTrigger(Animation animation) {
        super(animation);
    }

    @Override
    public void execute() {
        if (isAnyPlayerInRange() && !isStarted()) {
            setStarted(true);
            getAnimation().play().attachListener(new EventListener() {
                @Override
                public void onEvent(Event event) {
                    setStarted(false);
                    execute();
                }
            });
        }
    }

    @Override
    protected void onPlayerMoved() {
        execute();
    }
    
}
