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
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.events.AnimationFrameDisplayedEvent;
import org.bukkit.event.EventHandler;

/**
 *
 * @author Ivan1pl
 */
public class ChainTrigger extends BaseRangeTrigger {
    
    private final String animationName;
    
    private final int frame;
    
    public ChainTrigger(Animation animation, String animationName, int frame) {
        super(animation);
        this.animationName = animationName;
        this.frame = frame;
    }

    @Override
    public void execute() {
        if (!isStarted() && !isFinished()) {
            startAnimation();
        }
    }

    @Override
    protected void onPlayerMoved() {
        if (!isAnyPlayerInRange() && (isStarted() || isFinished())) {
            getAnimation().stop();
            getAnimation().showFrame(0);
            setStarted(false);
            setFinished(false);
        }
    }
    
    @EventHandler
    public void onFrameDisplayed(AnimationFrameDisplayedEvent event) {
        Animation triggeringAnimation = Animations.getAnimation(animationName);
        if (event.getAnimation() == triggeringAnimation && event.getFrame() == frame && !event.isReverse()) {
            execute();
        }
    }
    
}
