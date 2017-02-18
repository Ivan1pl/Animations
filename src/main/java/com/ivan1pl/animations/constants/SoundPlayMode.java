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
package com.ivan1pl.animations.constants;

/**
 *
 * @author Ivan1pl
 */
public enum SoundPlayMode {
    BEGIN,
    END,
    ALL_FRAMES;

    public static SoundPlayMode fromString(String value) {
        if (value.equalsIgnoreCase("b")) {
            return BEGIN;
        } else if (value.equalsIgnoreCase("e")) {
            return END;
        } else if (value.equalsIgnoreCase("a")) {
            return ALL_FRAMES;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
