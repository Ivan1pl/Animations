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
import com.ivan1pl.animations.data.MovingAnimation;
import com.ivan1pl.animations.data.StationaryAnimation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SwapframesCommandHandler extends ConversationCommandHandler {
    
    private final Prompt successPrompt;
    
    private final Prompt failPrompt;
    
    public SwapframesCommandHandler(Prompt successPrompt, Prompt failPrompt) {
        super("swapframes", 2, "first_index", "second_index");
        this.successPrompt = successPrompt;
        this.failPrompt = failPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        if (animation instanceof MovingAnimation) {
            throw new AnimationTypeException();
        }
        StationaryAnimation sAnimation = (StationaryAnimation) animation;
        
        int i1 = Integer.parseUnsignedInt(params[1]);
        int i2 = Integer.parseUnsignedInt(params[2]);
        sAnimation.stop();
        if (sAnimation.swapFrames(i1, i2)) {
            return new ConversationResponsePrompt(successPrompt, MessageUtil.formatInfoMessage(Messages.MSG_FRAMES_SWAPPED));
        } else {
            return new ConversationResponsePrompt(failPrompt, MessageUtil.formatErrorMessage(Messages.MSG_WRONG_FRAME_INDEX, new Long(0), new Long(sAnimation.getFrameCount()-1)));
        }
    }
    
}
