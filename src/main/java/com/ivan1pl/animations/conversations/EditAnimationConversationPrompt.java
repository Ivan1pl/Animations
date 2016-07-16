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

import com.ivan1pl.animations.constants.AnimationType;
import com.ivan1pl.animations.conversations.handlers.ConversationCommandHandler;
import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.constants.OperationResult;
import com.ivan1pl.animations.conversations.handlers.AddframeCommandHandler;
import com.ivan1pl.animations.conversations.handlers.CancelCommandHandler;
import com.ivan1pl.animations.conversations.handlers.HelpCommandHandler;
import com.ivan1pl.animations.conversations.handlers.IntervalCommandHandler;
import com.ivan1pl.animations.conversations.handlers.MaxDistanceCommandHandler;
import com.ivan1pl.animations.conversations.handlers.PreviewCommandHandler;
import com.ivan1pl.animations.conversations.handlers.PreviewframeCommandHandler;
import com.ivan1pl.animations.conversations.handlers.RemoveframeCommandHandler;
import com.ivan1pl.animations.conversations.handlers.SaveCommandHandler;
import com.ivan1pl.animations.conversations.handlers.StepCommandHandler;
import com.ivan1pl.animations.conversations.handlers.SwapframesCommandHandler;
import com.ivan1pl.animations.conversations.handlers.TypeCommandHandler;
import com.ivan1pl.animations.conversations.handlers.YCommandHandler;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.data.MovingAnimation;
import com.ivan1pl.animations.data.Selection;
import com.ivan1pl.animations.data.StationaryAnimation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import com.ivan1pl.animations.utils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

/**
 *
 * @author Ivan1pl
 */
public class EditAnimationConversationPrompt extends ValidatingPrompt {
    
    private final List<ConversationCommandHandler> CREATE_COMMANDS = new ArrayList<>();
    
    private final List<ConversationCommandHandler> STATIONARY_EDIT_COMMANDS = new ArrayList<>();
    
    private final List<ConversationCommandHandler> MOVING_EDIT_COMMANDS = new ArrayList<>();
    
    @Setter
    private boolean isEdit;
    
    @Setter
    private boolean simplePrompt;
    
    @Getter
    @Setter
    private AnimationType type;
    
    {
        CREATE_COMMANDS.add(new YCommandHandler(this, this, this));
        CREATE_COMMANDS.add(new TypeCommandHandler(this, this));
        CREATE_COMMANDS.add(new CancelCommandHandler(END_OF_CONVERSATION));
        
        STATIONARY_EDIT_COMMANDS.add(new AddframeCommandHandler(this));
        STATIONARY_EDIT_COMMANDS.add(new RemoveframeCommandHandler(this, this));
        STATIONARY_EDIT_COMMANDS.add(new PreviewframeCommandHandler(this, this));
        STATIONARY_EDIT_COMMANDS.add(new PreviewCommandHandler(this));
        STATIONARY_EDIT_COMMANDS.add(new SwapframesCommandHandler(this, this));
        STATIONARY_EDIT_COMMANDS.add(new IntervalCommandHandler(this));
        STATIONARY_EDIT_COMMANDS.add(new HelpCommandHandler(this));
        STATIONARY_EDIT_COMMANDS.add(new CancelCommandHandler(END_OF_CONVERSATION));
        STATIONARY_EDIT_COMMANDS.add(new SaveCommandHandler(this));
        
        MOVING_EDIT_COMMANDS.add(new StepCommandHandler(this, this));
        MOVING_EDIT_COMMANDS.add(new MaxDistanceCommandHandler(this, this));
        MOVING_EDIT_COMMANDS.add(new PreviewCommandHandler(this));
        MOVING_EDIT_COMMANDS.add(new IntervalCommandHandler(this));
        MOVING_EDIT_COMMANDS.add(new HelpCommandHandler(this));
        MOVING_EDIT_COMMANDS.add(new CancelCommandHandler(END_OF_CONVERSATION));
        MOVING_EDIT_COMMANDS.add(new SaveCommandHandler(this));
    }
    
    public EditAnimationConversationPrompt(boolean isEdit) {
        this.type = AnimationType.TYPE_STATIONARY;
        this.isEdit = isEdit;
        this.simplePrompt = false;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
        String[] realInput = string.split("\\s+");
        String name = (String) cc.getSessionData("name");
        Animation animation = (Animation) cc.getSessionData("animation");
        StationaryAnimation sAnimation = animation instanceof StationaryAnimation ? (StationaryAnimation) animation : null;
        simplePrompt = true;
        
        if (isEdit && sAnimation != null) {
            for (ConversationCommandHandler handler : STATIONARY_EDIT_COMMANDS) {
                if (realInput[0].equalsIgnoreCase(handler.getName())) {
                    try {
                        return handler.handle(cc, animation, name, realInput);
                    } catch (AnimationTypeException ex) {
                        Logger.getLogger(EditAnimationConversationPrompt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if (isEdit) {
            for (ConversationCommandHandler handler : MOVING_EDIT_COMMANDS) {
                if (realInput[0].equalsIgnoreCase(handler.getName())) {
                    try {
                        return handler.handle(cc, animation, name, realInput);
                    } catch (AnimationTypeException ex) {
                        Logger.getLogger(EditAnimationConversationPrompt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (ConversationCommandHandler handler : CREATE_COMMANDS) {
                if (realInput[0].equalsIgnoreCase(handler.getName())) {
                    try {
                        return handler.handle(cc, animation, name, realInput);
                    } catch (AnimationTypeException ex) {
                        Logger.getLogger(EditAnimationConversationPrompt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        String name = (String) cc.getSessionData("name");
        Animation animation = (Animation) cc.getSessionData("animation");
        StationaryAnimation sAnimation = animation instanceof StationaryAnimation ? (StationaryAnimation) animation : null;
        MovingAnimation mAnimation = animation instanceof MovingAnimation ? (MovingAnimation) animation : null;
        if (isEdit && !simplePrompt) {
            if (sAnimation != null) {
                return MessageUtil.formatPromptMessage(Messages.MSG_STATIONARY_ANIMATION_EDIT_INFO, name, animation.getFrameCount(), animation.getInterval(), formatAnswers(STATIONARY_EDIT_COMMANDS));
            } else if (mAnimation != null) {
                return MessageUtil.formatPromptMessage(Messages.MSG_MOVING_ANIMATION_EDIT_INFO, name, mAnimation.getStepX(), mAnimation.getStepY(), mAnimation.getStepZ(), mAnimation.getMaxDistance(), animation.getInterval(), formatAnswers(MOVING_EDIT_COMMANDS));
            } else {
                return MessageUtil.formatErrorMessage(Messages.MSG_INVALID_ANIMATION_TYPE, name);
            }
        } else if (isEdit && simplePrompt) {
            if (sAnimation != null) {
                return MessageUtil.formatPromptMessage(Messages.MSG_STATIONARY_ANIMATION_EDIT_INFO_SIMPLE, name, animation.getFrameCount(), animation.getInterval());
            } else if (mAnimation != null) {
                return MessageUtil.formatPromptMessage(Messages.MSG_MOVING_ANIMATION_EDIT_INFO_SIMPLE, name, mAnimation.getStepX(), mAnimation.getStepY(), mAnimation.getStepZ(), mAnimation.getMaxDistance(), animation.getInterval());
            } else {
                return MessageUtil.formatErrorMessage(Messages.MSG_INVALID_ANIMATION_TYPE, name);
            }
        } else {
            return MessageUtil.formatPromptMessage(Messages.MSG_CREATE_ANIMATION, name, Animations.getWandMaterial().toString());
        }
    }

    @Override
    protected boolean isInputValid(ConversationContext cc, String string) {
        String[] realInput = string.split("\\s+");
        List<ConversationCommandHandler> cmpList;
        Animation animation = (Animation) cc.getSessionData("animation");
        StationaryAnimation sAnimation = animation instanceof StationaryAnimation ? (StationaryAnimation) animation : null;
        if (isEdit && sAnimation != null) {
            cmpList = STATIONARY_EDIT_COMMANDS;
        } else if (isEdit && sAnimation == null) {
            cmpList = MOVING_EDIT_COMMANDS;
        } else {
            cmpList = CREATE_COMMANDS;
        }
        
        for (ConversationCommandHandler handler : cmpList) {
            if (handler.getName().equalsIgnoreCase(realInput[0]) && handler.getParamsCount() == realInput.length-1) {
                if (handler.isCheckParamTypes()) {
                    for (int i = 1; i < realInput.length; ++i) {
                        if (!isNumeric(realInput[i])) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    private static String formatAnswers(List<ConversationCommandHandler> answers) {
        String ret = "";
        for (ConversationCommandHandler answer : answers) {
            if (ret.length() > 0) {
                ret += "\n";
            }
            ret += MessageUtil.formatPromptMessage(Messages.MSG_ITEM, answer.format());
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
