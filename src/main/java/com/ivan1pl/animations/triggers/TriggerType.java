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

/**
 *
 * @author Ivan1pl
 */
public enum TriggerType {
    RANGE,
    LOOP,
    PASSWORD,
    BLOCK,
    TWO_BLOCK,
    CHAIN;
    
    public static TriggerType fromString(String type) {
        if ("l".equalsIgnoreCase(type) || "loop".equalsIgnoreCase(type)) {
            return LOOP;
        } else if ("p".equalsIgnoreCase(type) || "password".equalsIgnoreCase(type)) {
            return PASSWORD;
        } else if ("b".equalsIgnoreCase(type) || "block".equalsIgnoreCase(type)) {
            return BLOCK;
        } else if ("t".equalsIgnoreCase(type) || "twoblock".equalsIgnoreCase(type)) {
            return TWO_BLOCK;
        } else if ("c".equalsIgnoreCase(type) || "chain".equalsIgnoreCase(type)) {
            return CHAIN;
        } else {
            return RANGE;
        }
    }
}
