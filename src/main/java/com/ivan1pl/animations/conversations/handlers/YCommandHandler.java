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
import com.ivan1pl.animations.conversations.EditAnimationConversationPrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.data.MovingAnimation;
import com.ivan1pl.animations.data.Selection;
import com.ivan1pl.animations.data.StationaryAnimation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public class YCommandHandler extends ConversationCommandHandler {
    
    private final Prompt successPrompt;
    
    private final Prompt failPrompt;
    
    private final EditAnimationConversationPrompt affectedPrompt;

    public YCommandHandler(Prompt successPrompt, Prompt failPrompt, EditAnimationConversationPrompt affectedPrompt) {
        super("y", 0);
        this.successPrompt = successPrompt;
        this.failPrompt = failPrompt;
        this.affectedPrompt = affectedPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        Selection sel = Animations.getSelection((Player) cc.getForWhom());
        try {
            Animation anim;
            switch (affectedPrompt.getType()) {
                case TYPE_MOVING:
                    anim = new MovingAnimation(sel);
                    break;
                case TYPE_STATIONARY:
                default:
                    anim = new StationaryAnimation(sel);
                    break;
            }
            
            Animations.setAnimation(animationName, anim);
            OperationResult result = Animations.saveAnimation(animationName);
            String message = "";
            switch (result) {
                case SUCCESS:
                    message = MessageUtil.formatInfoMessage(Messages.MSG_ANIMATION_SAVED);
                    affectedPrompt.setEdit(true);
                    affectedPrompt.setSimplePrompt(false);
                    cc.setSessionData("animation", anim);
                    break;
                case INTERNAL_ERROR:
                case NOT_FOUND:
                    message = MessageUtil.formatErrorMessage(Messages.MSG_SAVE_FAILED, animationName);
                    break;
            }
            return new ConversationResponsePrompt(successPrompt, message);
        } catch (InvalidSelectionException ex) {
            return new ConversationResponsePrompt(failPrompt, MessageUtil.formatErrorMessage(Messages.MSG_INVALID_SELECTION));
        }
    }
    
}
