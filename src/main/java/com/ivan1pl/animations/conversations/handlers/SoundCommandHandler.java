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

import com.ivan1pl.animations.conversations.SelectSoundConversationPrompt;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.SoundData;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SoundCommandHandler extends ConversationCommandHandler {

    private final Prompt successPrompt;

    public SoundCommandHandler(Prompt successPrompt) {
        super("sound", 3, "range", "pitch", "volume");
        this.successPrompt = successPrompt;
    }

    @Override
    public Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException {
        int range = Integer.parseUnsignedInt(params[1]);
        int pitch = Integer.parseUnsignedInt(params[2]);
        int volume = Integer.parseUnsignedInt(params[3]);
        SoundData sd = new SoundData();
        sd.setRange(range);
        sd.setPitch(pitch);
        sd.setVolume(volume);
        return new SelectSoundConversationPrompt(successPrompt, animation, sd);
    }

}
