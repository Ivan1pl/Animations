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
import com.ivan1pl.animations.conversations.EditBlockTriggerConversationPrompt;
import com.ivan1pl.animations.conversations.EditMultiBlockTriggerConversationPrompt;
import com.ivan1pl.animations.conversations.EditPasswordTriggerConversationPrompt;
import com.ivan1pl.animations.conversations.SelectAnimationConversationPrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.triggers.TriggerBuilder;
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
    
    private final Prompt failPrompt;
    
    public TriggerCommandHandler(Prompt successPrompt, Prompt failPrompt) {
        super("trigger", 1, 1, false, "r(ange)|l(oop)|b(lock)|p(assword)|t(woblock)|c(hain)", "max_range");
        this.successPrompt = successPrompt;
        this.failPrompt = failPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        TriggerType type = TriggerType.fromString(params[1]);
        int i = -1;
        if (params.length > 2) {
            i = Integer.parseUnsignedInt(params[2]);
        }
        Prompt retPrompt = new ConversationResponsePrompt(successPrompt, MessageUtil.formatInfoMessage(Messages.MSG_TRIGGER_CHANGED));
        TriggerBuilder builder = new TriggerBuilder(animation).setTriggerType(type).setRange(i);
        if (i < 0 && (type == TriggerType.BLOCK || type == TriggerType.LOOP || type == TriggerType.PASSWORD || type == TriggerType.RANGE || type == TriggerType.CHAIN)) {
            return new ConversationResponsePrompt(failPrompt, MessageUtil.formatErrorMessage(Messages.MSG_RANGE_NEEDED, type.toString()));
        }
        if (null != type) switch (type) {
            case BLOCK:
                retPrompt = new EditBlockTriggerConversationPrompt(successPrompt, animation, builder);
                break;
            case PASSWORD:
                retPrompt = new EditPasswordTriggerConversationPrompt(successPrompt, animation, builder);
                break;
            case TWO_BLOCK:
                retPrompt = new EditMultiBlockTriggerConversationPrompt(successPrompt, animation, builder, 1, 2);
                break;
            case CHAIN:
                retPrompt = new SelectAnimationConversationPrompt(successPrompt, animation, builder);
                break;
            default:
                animation.setTriggerBuilderData(new TriggerBuilderData(type, i));
                break;
        }
        return retPrompt;
    }
    
    @Override
    public boolean customCheckParamTypes(String[] params) {
        return params.length < 3 || StringUtil.isUnsignedInteger(params[2]);
    }
    
}
