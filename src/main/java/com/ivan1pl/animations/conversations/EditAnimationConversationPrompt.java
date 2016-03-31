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
package com.ivan1pl.animations.conversations;

import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.constants.OperationResult;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.data.Selection;
import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import com.ivan1pl.animations.utils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public class EditAnimationConversationPrompt extends ValidatingPrompt {
    
    private static final List<ConversationAnswer> CREATE_COMMANDS = new ArrayList<>();
    
    private static final List<ConversationAnswer> EDIT_COMMANDS = new ArrayList<>();
    
    private boolean isEdit;
    
    static {
        CREATE_COMMANDS.add(new ConversationAnswer("y", 0));
        CREATE_COMMANDS.add(new ConversationAnswer("cancel", 0));
        
        EDIT_COMMANDS.add(new ConversationAnswer("addframe", 0));
        EDIT_COMMANDS.add(new ConversationAnswer("removeframe", 1, "frame_index"));
        EDIT_COMMANDS.add(new ConversationAnswer("previewframe", 1, "frame_index"));
        EDIT_COMMANDS.add(new ConversationAnswer("preview", 0));
        EDIT_COMMANDS.add(new ConversationAnswer("swapframes", 2, "first_index", "second_index"));
        EDIT_COMMANDS.add(new ConversationAnswer("interval", 1, "interval"));
        EDIT_COMMANDS.add(new ConversationAnswer("cancel", 0));
        EDIT_COMMANDS.add(new ConversationAnswer("save", 0));
    }
    
    public EditAnimationConversationPrompt(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
        String[] realInput = string.split("\\s+");
        String name = (String) cc.getSessionData("name");
        Animation animation = (Animation) cc.getSessionData("animation");
        
        if (isEdit) {
            if (realInput[0].equalsIgnoreCase("addframe")) {
                animation.stop();
                animation.addFrame();
                return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_FRAME_ADDED));
            } else if (realInput[0].equalsIgnoreCase("removeframe")) {
                int index = Integer.parseUnsignedInt(realInput[1]);
                animation.stop();
                if (animation.removeFrame(index)) {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_FRAME_REMOVED));
                } else {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_WRONG_FRAME_INDEX, 0, animation.getFrameCount()-1));
                }
            } else if (realInput[0].equalsIgnoreCase("previewframe")) {
                int index = Integer.parseUnsignedInt(realInput[1]);
                animation.stop();
                if (animation.showFrame(index)) {
                    return this;
                } else {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_WRONG_FRAME_INDEX, 0, animation.getFrameCount()-1));
                }
            } else if (realInput[0].equalsIgnoreCase("preview")) {
                animation.stop();
                animation.play();
            } else if (realInput[0].equalsIgnoreCase("swapframes")) {
                int i1 = Integer.parseUnsignedInt(realInput[1]);
                int i2 = Integer.parseUnsignedInt(realInput[2]);
                animation.stop();
                if (animation.swapFrames(i1, i2)) {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_FRAMES_SWAPPED));
                } else {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_WRONG_FRAME_INDEX, 0, animation.getFrameCount()-1));
                }
            } else if (realInput[0].equalsIgnoreCase("interval")) {
                int interval = Integer.parseUnsignedInt(realInput[1]);
                animation.stop();
                animation.setInterval(interval);
                return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_INTERVAL_SET, interval));
            } else if (realInput[0].equalsIgnoreCase("cancel")) {
                animation.stop();
                Animations.reloadAnimation(name);
                return new ConversationResponsePrompt(END_OF_CONVERSATION, MessageUtil.formatMessageWithPrefix(Messages.MSG_EDIT_CANCELLED));
            } else if (realInput[0].equalsIgnoreCase("save")) {
                animation.stop();
                OperationResult result = Animations.saveAnimation(name);
                String message = "";
                switch (result) {
                    case SUCCESS:
                        message = MessageUtil.formatMessageWithPrefix(Messages.MSG_ANIMATION_SAVED);
                        break;
                    case INTERNAL_ERROR:
                    case NOT_FOUND:
                        message = MessageUtil.formatMessageWithPrefix(Messages.MSG_SAVE_FAILED, name);
                        break;
                }
                return new ConversationResponsePrompt(this, message);
            }
        } else {
            if (realInput[0].equalsIgnoreCase("y")) {
                Selection sel = Animations.getSelection((Player) cc.getForWhom());
                try {
                    Animation anim = new Animation(sel);
                    Animations.setAnimation(name, anim);
                    OperationResult result = Animations.saveAnimation(name);
                    String message = "";
                    switch (result) {
                        case SUCCESS:
                            message = MessageUtil.formatMessageWithPrefix(Messages.MSG_ANIMATION_SAVED);
                            isEdit = true;
                            cc.setSessionData("animation", anim);
                            break;
                        case INTERNAL_ERROR:
                        case NOT_FOUND:
                            message = MessageUtil.formatMessageWithPrefix(Messages.MSG_SAVE_FAILED, name);
                            break;
                    }
                    return new ConversationResponsePrompt(this, message);
                } catch (InvalidSelectionException ex) {
                    return new ConversationResponsePrompt(this, MessageUtil.formatMessageWithPrefix(Messages.MSG_INVALID_SELECTION));
                }
            } else if (realInput[0].equalsIgnoreCase("cancel")) {
                return new ConversationResponsePrompt(END_OF_CONVERSATION, MessageUtil.formatMessageWithPrefix(Messages.MSG_EDIT_CANCELLED));
            }
        }
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        String name = (String) cc.getSessionData("name");
        Animation animation = (Animation) cc.getSessionData("animation");
        if (isEdit) {
            return MessageUtil.formatMessage(Messages.MSG_ANIMATION_EDIT_INFO, name, animation.getFrameCount(), animation.getInterval(), formatAnswers(EDIT_COMMANDS));
        } else {
            return MessageUtil.formatMessage(Messages.MSG_CREATE_ANIMATION, name, Animations.getWandMaterial().toString());
        }
    }

    @Override
    protected boolean isInputValid(ConversationContext cc, String string) {
        String[] realInput = string.split("\\s+");
        List<ConversationAnswer> cmpList;
        if (isEdit) {
            cmpList = EDIT_COMMANDS;
        } else {
            cmpList = CREATE_COMMANDS;
        }
        
        if (cmpList.contains(new ConversationAnswer(realInput[0].toLowerCase(), realInput.length-1))) {
            for (int i = 1; i < realInput.length; ++i) {
                if (!isNumeric(realInput[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private static String formatAnswers(List<ConversationAnswer> answers) {
        String ret = "";
        for (ConversationAnswer answer : answers) {
            if (ret.length() > 0) {
                ret += ", ";
            }
            ret += answer.format();
        }
        return ret;
    }
    
    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
}
