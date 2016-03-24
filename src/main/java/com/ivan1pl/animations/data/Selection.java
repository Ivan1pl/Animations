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
import org.bukkit.Location;

/**
 *
 * @author Ivan1pl
 */
public class Selection {
    
    @Getter
    @Setter
    private Location point1;
    
    @Getter
    @Setter
    private Location point2;
    
    private boolean validate() {
        return (point1 != null && point2 != null && point1.getWorld().getUID().equals(point2.getWorld().getUID()));
    }
    
    public static boolean isValid(Selection sel) {
        boolean ret = sel == null ? false : sel.validate();
        return ret;
    }
    
}
