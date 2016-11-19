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
import com.ivan1pl.animations.conversations.ConversationResponsePrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.triggers.TriggerBuilderData;
import com.ivan1pl.animations.triggers.TriggerType;
import com.ivan1pl.animations.utils.MessageUtil;
import com.ivan1pl.animations.utils.StringUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class TriggerCommandHandler extends ConversationCommandHandler {
    
    private final Prompt successPrompt;
    
    public TriggerCommandHandler(Prompt successPrompt) {
        super("trigger", 2, false, "r(ange)|l(oop)", "max_range");
        this.successPrompt = successPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        TriggerType type = TriggerType.fromString(params[1]);
        int i = Integer.parseUnsignedInt(params[2]);
        animation.setTriggerBuilderData(new TriggerBuilderData(type, i));
        return new ConversationResponsePrompt(successPrompt, MessageUtil.formatInfoMessage(Messages.MSG_TRIGGER_CHANGED));
    }
    
    @Override
    public boolean customCheckParamTypes(String[] params) {
        return StringUtil.isUnsignedInteger(params[2]);
    }
    
}
