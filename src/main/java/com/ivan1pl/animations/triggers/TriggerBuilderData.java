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

import java.io.Serializable;
import lombok.Getter;

/**
 * Don't store whole trigger in animation, just object of this class.
 * 
 * When animation is loaded it will use it to create a trigger.
 * @author Ivan1pl
 */
public class TriggerBuilderData implements Serializable {
    
    @Getter
    private final TriggerType type;
    
    @Getter
    private final int range;
    
    public TriggerBuilderData(TriggerType type, int range) {
        this.type = type;
        this.range = range;
    }
    
}
