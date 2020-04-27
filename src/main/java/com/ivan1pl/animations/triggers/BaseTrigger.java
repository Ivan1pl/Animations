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

import com.ivan1pl.animations.AnimationsPlugin;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.events.Event;
import com.ivan1pl.animations.events.EventListener;
import com.ivan1pl.animations.tasks.AnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 *
 * @author Ivan1pl
 */
public abstract class BaseTrigger implements Trigger, Listener {
    
    private boolean started;
    private boolean finished;
    private final Animation animation;
    
    public BaseTrigger(Animation animation) {
        this.animation = animation;
    }
    
    @Override
    public void register() {
        Bukkit.getServer().getPluginManager().registerEvents(this, AnimationsPlugin.getPluginInstance());
    }
    
    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
    }
    
    protected void startAnimation() {
        AnimationTask task = getAnimation().play();
        if (task != null) {
            setStarted(true);
            task.attachListener(new EventListener() {
                @Override
                public void onEvent(Event event) {
                    setStarted(false);
                    setFinished(true);
                }
            });
        } else {
            if (getAnimation().getFrameCount() > 0) {
                getAnimation().showFrame(0);
            }
            setStarted(false);
            setFinished(false);
        }
    }
    
    protected void startReverseAnimation() {
        AnimationTask task = getAnimation().playReverse();
        if (task != null) {
            setStarted(true);
            task.attachListener(new EventListener() {
                @Override
                public void onEvent(Event event) {
                    setStarted(false);
                    setFinished(false);
                }
            });
        } else {
            if (getAnimation().getFrameCount() > 0) {
                getAnimation().showFrame(getAnimation().getFrameCount()-1);
            }
            setStarted(false);
            setFinished(true);
        }
    }
    
    protected void startLoopAnimation() {
        AnimationTask task = getAnimation().play();
        if (task != null) {
            setStarted(true);
            setFinished(false);
            task.attachListener(new EventListener() {
                @Override
                public void onEvent(Event event) {
                    setStarted(false);
                    setFinished(false);
                    execute();
                }
            });
        } else {
            getAnimation().showFrame(0);
            setStarted(false);
            setFinished(false);
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Animation getAnimation() {
        return animation;
    }
}
