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
package com.ivan1pl.animations.tasks;

import com.ivan1pl.animations.AnimationsPlugin;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Ivan1pl
 */
public class AnimationTask extends BukkitRunnable {
    
    @Getter
    private final Animation animation;
    
    private int stage = 0;
    
    public AnimationTask(Animation animation) {
        this.animation = animation;
    }
    
    public void start() {
        this.runTaskTimer(AnimationsPlugin.getPluginInstance(), 0, animation.getInterval());
    }

    @Override
    public void run() {
        animation.showFrame(stage);
        stage++;
        if (stage >= animation.getFrameCount()) {
            Animations.deleteTask(this);
            this.cancel();
        }
    }
    
    public synchronized void stop() {
        int id = this.getTaskId();
        if (Bukkit.getScheduler().isCurrentlyRunning(id) || Bukkit.getScheduler().isQueued(id)) {
            this.cancel();
        }
        stage = animation.getFrameCount() + 1;
        animation.showFrame(0);
    }
    
}
