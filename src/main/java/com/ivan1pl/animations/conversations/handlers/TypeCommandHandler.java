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

import com.ivan1pl.animations.constants.AnimationType;
import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.conversations.ConversationResponsePrompt;
import com.ivan1pl.animations.conversations.EditAnimationConversationPrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class TypeCommandHandler extends ConversationCommandHandler {
    
    private final Prompt successPrompt;
    
    private final EditAnimationConversationPrompt affectedPrompt;
    
    public TypeCommandHandler(Prompt successPrompt, EditAnimationConversationPrompt affectedPrompt) {
        super("type", 1, 0, false, "m(oving)|s(tationary)");
        this.successPrompt = successPrompt;
        this.affectedPrompt = affectedPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        AnimationType type = AnimationType.fromString(params[1]);
        affectedPrompt.setType(type);
        return new ConversationResponsePrompt(successPrompt, MessageUtil.formatInfoMessage(Messages.MSG_TYPE_SET, type.getName()));
    }
    
}
