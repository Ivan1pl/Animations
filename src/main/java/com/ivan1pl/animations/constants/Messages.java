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
    
    public static final String MSG_INVALID_SELECTION = "Invalid selection! Select 2 points in the same world.";
    
    public static final String MSG_WRONG_FRAME_INDEX = "Frame index out of range. Index should be between {0} and {1}.";
    
    public static final String MSG_ANIMATION_NOT_FOUND = "Could not find animation with name: {0}.";
    
    public static final String MSG_STATIONARY_ANIMATION_EDIT_INFO = "Editing {0}, {1} frames, interval = {2}. Available operations:\n{3}";
    
    public static final String MSG_MOVING_ANIMATION_EDIT_INFO = "Editing {0}, step: ({1}, {2}, {3}), maximum distance: {4}, interval = {5}. Available operations:\n{6}";
    
    public static final String MSG_STATIONARY_ANIMATION_EDIT_INFO_SIMPLE = "Editing {0}, {1} frames, interval = {2}. For list of available operations, type ''help''.";
    
    public static final String MSG_MOVING_ANIMATION_EDIT_INFO_SIMPLE = "Editing {0}, step: ({1}, {2}, {3}), maximum distance: {4}, interval = {5}. For list of available operations, type ''help''.";
    
    public static final String MSG_NOT_PERMITTED = "You don't have permission to run this command.";
    
    public static final String MSG_EXPECTED_MORE_ARGS = "Expected at least {0} arguments for this command.";
    
    public static final String MSG_CREATE_ANIMATION = "Creating {0}. Select area using {1} and set type with ''type m'' (moving animation) or ''type s'' (default - stationary animation).\nConfirm by typing ''y'' or exit with ''cancel''";
    
    public static final String MSG_ANIMATION_CREATED = "Animation {0} created.";
    
    public static final String MSG_SAVE_FAILED = "Failed to save animation: {0}.";
    
    public static final String MSG_POINT1_SET = "First point set{0}.";
    
    public static final String MSG_POINT2_SET = "Secont point set{0}.";
    
    public static final String MSG_BLOCK_SELECTION_SET = "Block selection set.";
    
    public static final String MSG_FRAME_ADDED = "Frame added.";
    
    public static final String MSG_FRAME_REMOVED = "Frame removed.";
    
    public static final String MSG_FRAMES_SWAPPED = "Frames swapped.";
    
    public static final String MSG_EDIT_CANCELLED = "Edit cancelled.";
    
    public static final String MSG_ANIMATION_SAVED = "Animation saved.";
    
    public static final String MSG_INTERVAL_SET = "Interval set to {0} ticks.";
    
    public static final String MSG_PLAYER_ONLY = "You can only use this command ingame.";
    
    public static final String MSG_ANIMATION_DELETED = "Animation deleted.";
    
    public static final String MSG_DELETE_FAILED = "Failed to delete animation: {0}.";
    
    public static final String MSG_DISPLAYING_PAGE = "Displaying page {0}/{1}.";
    
    public static final String MSG_DISPLAYING_FRAME = "Displaying frame {0}.";
    
    public static final String MSG_PLAYING_PREVIEW = "Playing animation preview.";
    
    public static final String MSG_TYPE_SET = "Animation type set to {0}.";
    
    public static final String MSG_INVALID_STEP = "Failed to set step to ({0}, {1}, {2}) - step distance must not be 0.";
    
    public static final String MSG_STEP_SET = "Step set to ({0}, {1}, {2}).";
    
    public static final String MSG_INVALID_MAX_DISTANCE = "Failed to set maximum distance to {0} - maximum distance must not be 0.";
    
    public static final String MSG_BACKGROUND_UPDATED = "Background updated.";
    
    public static final String MSG_INVALID_BACKGROUND_NEEDS_MAX_DISTANCE = "Failed to update background - maximum distance must not be 0.";
    
    public static final String MSG_INVALID_BACKGROUND_NEEDS_STEP = "Failed to update background - step distance must not be 0.";
    
    public static final String MSG_MAX_DISTANCE_SET = "Maximum distance set to {0}.";
    
    public static final String MSG_INVALID_ANIMATION_TYPE = "Invalid animation type for animation: {0}";
    
    public static final String MSG_ITEM = " - {0}";
    
    public static final String MSG_TRIGGER_CHANGED = "Trigger changed.";
    
    public static final String MSG_TRIGGER_DELETED = "Trigger deleted.";
    
    public static final String MSG_EDIT_BLOCK_TRIGGER = "Select a block with {0} and type:\n''l'' if the block should be clicked with left mouse button, ''r'' if the block should be clicked with right mouse button, ''b'' if both mouse buttons should be accepted or exit with ''c'',";
    
    public static final String MSG_EDIT_TRIGGER_CANCELLED = "Cancelled.";
    
    public static final String MSG_EDIT_BLOCK_TRIGGER_NOBLOCK = "You have to select a block before you confirm.";
    
    public static final String MSG_ENTER_PASSWORD_FOR_TRIGGER = "Enter the text which will trigger the animation:";
    
    public static final String MSG_SELECTION_TOO_BIG = "Selected area is too big. Maximum volume is {0}.";
    
    public static final String MSG_BLOCK_ADDED = "Block added.";
    
    public static final String MSG_EDIT_MULTI_BLOCK_TRIGGER = "Editing block #{1}.\n" + MSG_EDIT_BLOCK_TRIGGER;
    
    public static final String MSG_RANGE_NEEDED = "Range is required for trigger type: {0}.";

    public static final String MSG_SELECT_ANIMATION = "Select animation (type animation name or exit with ''c''). Existing animations:\n{0}";

    public static final String MSG_ANIMATION_SELECTED = "Animation selected.";

    public static final String MSG_SELECT_FRAME = "Select frame after which this animation will be triggered or exit with ''c''.";

    public static final String MSG_SELECT_SOUND_CANCELLED = "Sound selection cancelled.";

    public static final String MSG_SELECT_SOUND = "Select sound with ''select <sound_name>'' or exit with ''cancel''. For list of available sounds, type ''list <page>''";

    public static final String MSG_SOUND_SELECTED = "Sound selected.";

    public static final String MSG_INVALID_SOUND = "Invalid sound name: {0}.";

    public static final String MSG_INVALID_PAGE = "Invalid page number: {0}. Expected number between 1 and {1}.";

    public static final String MSG_SOUND_LIST = "Displaying sound list, page {0}/{1}:\n{2}";

    public static final String MSG_SELECT_SOUND_PLAY_MODE = "When should the sound be played? (b - when animation begins, e - when animation ends, a - all frames, c - cancel)";

    public static final String MSG_SOUND_UPDATED = "Sound updated.";

    public static final String MSG_SOUND_DELETED = "Sound deleted.";

    public static final String SOUND_INFO = "Sound: {0}; {1}; range = {2}; pitch = {3}; volume = {4}";

    public static final String NOSOUND_INFO = "Sound: none";
    
    public static final String TRIGGER_INFO = "Trigger: type: {0}, range: {1}.";
    
    public static final String TRIGGER_NORANGE_INFO = "Trigger: type: {0}.";
    
    public static final String NOTRIGGER_INFO = "Trigger: none";
    
    public static final String INFO_ANIMATION_LOADED = "Animation loaded: ";
    
    public static final String INFO_ENABLED = "Enabled!";
    
    public static final String INFO_INVALID_MATERIAL = "Invalid material name: {0}. Using default ({1}).";
    
    public static final String DEBUG_COMMAND_EXECUTED = "''{0}'' is handling command executed by ''{1}''.";
    
    public static final String DEBUG_MOVING_ANIMATION_CHECKING_PLAYER = "''{0}'': checking player: ''{1}''.";
    
    public static final String DEBUG_MOVING_PLAYER = "''{0}'': found player to move: ''{1}''. Moving.";
    
}
