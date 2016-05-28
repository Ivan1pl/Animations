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
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ivan1pl
 */
public abstract class Animation implements Serializable {
    
    @Getter
    @Setter
    private int interval = 1;
    
    public abstract boolean showFrame(int index);
    
    public void play() {
        AnimationTask task = new AnimationTask(this);
        Animations.registerTask(task);
        task.start();
    }
    
    public abstract int getFrameCount();
    
    public void stop() {
        AnimationTask task = Animations.retrieveTask(this);
        if (task != null) {
            task.stop();
            Animations.deleteTask(task);
        }
    }
    
}
