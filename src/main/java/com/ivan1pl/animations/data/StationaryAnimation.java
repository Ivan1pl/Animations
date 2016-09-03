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

import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public class StationaryAnimation extends Animation implements Serializable {
    
    private final List<Frame> frames = new ArrayList<>();
    
    @Getter
    private final Selection selection;
    
    public StationaryAnimation(Selection selection) throws InvalidSelectionException {
        if (!Selection.isValid(selection)) {
            throw new InvalidSelectionException();
        }
        this.selection = selection;
    }
    
    public void addFrame() {
        Frame f = Frame.fromSelection(selection);
        frames.add(f);
    }
    
    public boolean removeFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            return false;
        }
        
        showFrame(0);
        frames.remove(index);
        return true;
    }
    
    @Override
    public boolean showFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            return false;
        }
        
        frames.get(index).show();
        return true;
    }
    
    @Override
    public int getFrameCount() {
        return frames.size();
    }
    
    public boolean swapFrames(int i1, int i2) {
        if (i1 < 0 || i1 >= frames.size() || i2 < 0 || i2 >= frames.size()) {
            return false;
        }
        
        Frame f1 = frames.get(i1);
        Frame f2 = frames.get(i2);
        
        frames.set(i1, f2);
        frames.set(i2, f1);
        return true;
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
