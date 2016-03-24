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
public class Messages {
    
    public static final String MSG_INVALID_SELECTION = "Invalid selection!";
    
    public static final String MSG_WRONG_FRAME_INDEX = "Frame index out of range. Index should be between {0} and {1}.";
    
    public static final String MSG_ANIMATION_NOT_FOUND = "Could not find animation with name: {0}.";
    
    public static final String MSG_ANIMATION_EDIT_INFO = "Editing {0}, {1} frames, interval = {2}.\n" +
                                                         "Available operations: addFrame, removeFrame <frame index>, " +
                                                         "previewFrame <frame index>, preview, swapFrames <first index> <second index>, " +
                                                         "cancel, save";
    
    public static final String MSG_NOT_PERMITTED = "You don't have permission to run this command.";
    
    public static final String MSG_EXPECTED_MORE_ARGS = "You're missing arguments for this command. Expected at least {0}.";
    
}
