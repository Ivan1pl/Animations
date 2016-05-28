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
package com.ivan1pl.animations.conversations.handlers;

import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.constants.OperationResult;
import com.ivan1pl.animations.conversations.ConversationResponsePrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SaveCommandHandler extends ConversationCommandHandler {
    
    private final Prompt successPrompt;
    
    public SaveCommandHandler(Prompt successPrompt) {
        super("save", 0);
        this.successPrompt = successPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        animation.stop();
        OperationResult result = Animations.saveAnimation(animationName);
        String message = "";
        switch (result) {
            case SUCCESS:
                message = MessageUtil.formatInfoMessage(Messages.MSG_ANIMATION_SAVED);
                break;
            case INTERNAL_ERROR:
            case NOT_FOUND:
                message = MessageUtil.formatErrorMessage(Messages.MSG_SAVE_FAILED, animationName);
                break;
        }
        return new ConversationResponsePrompt(successPrompt, message);
    }
    
}
