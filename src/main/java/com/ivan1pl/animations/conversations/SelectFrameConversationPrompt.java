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
import com.ivan1pl.animations.utils.StringUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SelectFrameConversationPrompt extends BaseEditorValidatingPrompt {

    private final Prompt retPrompt;

    private final Animation animation;

    private final TriggerBuilder triggerBuilder;

    public SelectFrameConversationPrompt(Prompt retPrompt, Animation animation, TriggerBuilder triggerBuilder) {
        this.retPrompt = retPrompt;
        this.animation = animation;
        this.triggerBuilder = triggerBuilder;
    }

    @Override
    protected boolean isInputValid(ConversationContext cc, String string) {
        return "c".equalsIgnoreCase(string) || StringUtil.isUnsignedInteger(string);
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
        if ("c".equalsIgnoreCase(string)) {
            return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_EDIT_TRIGGER_CANCELLED));
        } else {
            triggerBuilder.setFrame(Integer.parseInt(string));
            animation.setTriggerBuilderData(triggerBuilder.createBuilderData());
            return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_TRIGGER_CHANGED));
        }
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        return MessageUtil.formatPromptMessage(Messages.MSG_SELECT_FRAME);
    }

}
