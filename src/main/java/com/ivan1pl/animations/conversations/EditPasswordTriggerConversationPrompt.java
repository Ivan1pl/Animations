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
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.triggers.TriggerBuilder;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class EditPasswordTriggerConversationPrompt extends BaseEditorStringPrompt {

    private final Prompt retPrompt;
    
    private final Animation animation;
    
    private final TriggerBuilder triggerBuilder;
    
    public EditPasswordTriggerConversationPrompt(Prompt retPrompt, Animation animation, TriggerBuilder triggerBuilder) {
        this.retPrompt = retPrompt;
        this.animation = animation;
        this.triggerBuilder = triggerBuilder;
    }
    
    @Override
    public String getPromptText(ConversationContext cc) {
        return MessageUtil.formatPromptMessage(Messages.MSG_ENTER_PASSWORD_FOR_TRIGGER);
    }

    @Override
    protected Prompt accept(ConversationContext cc, String string) {
        animation.setTriggerBuilderData(triggerBuilder.setPassword(string).createBuilderData());
        return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_TRIGGER_CHANGED));
    }
    
}
