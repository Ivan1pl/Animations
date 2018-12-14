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

import com.ivan1pl.animations.tasks.AnimationTask;
import com.ivan1pl.animations.triggers.TriggerBuilderData;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl, Eriol_Eandur
 */
public abstract class Animation implements Serializable {
    
    private static final long serialVersionUID = -7839198751291994315L;

    @Getter
    @Setter
    private int interval = 1;
    
    @Getter
    @Setter
    private TriggerBuilderData triggerBuilderData = null;

    @Getter
    @Setter
    private SoundData soundData = null;
    
    public abstract boolean showFrame(int index);
    
    public AnimationTask play() {
        AnimationTask task = new AnimationTask(this);
        if (Animations.registerTask(task)) {
            task.start();
            return task;
        }
        return null;
    }
    
    public AnimationTask playReverse() {
        AnimationTask task = new AnimationTask(this, true);
        if (Animations.registerTask(task)) {
            task.start();
            return task;
        }
        return null;
    }
    
    public abstract int getFrameCount();
    
    public void stop() {
        AnimationTask task = Animations.retrieveTask(this);
        if (task != null) {
            task.stop();
            Animations.deleteTask(task);
        }
    }
    
    public abstract boolean isPlayerInRange(Player player, int range);
    
    public boolean isAnyPlayerInRange(int range) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (isPlayerInRange(p, range)) {
                return true;
            }
        }
        return false;
    }
    
    public abstract int getSizeInBlocks();

    public void playSound() {
        if (soundData != null) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (isPlayerInRange(p, soundData.getRange())) {
                    p.playSound(getCenter(), Sound.valueOf(soundData.getName()), soundData.getVolume()/100.f, soundData.getPitch()/100.f);
                }
            }
        }
    }

    public void saveTo(File folder, ObjectOutputStream out) throws IOException {
        out.writeObject(this);
    }
    
    public abstract boolean prepare(File folder);
    
    protected abstract Location getCenter();
 
}
