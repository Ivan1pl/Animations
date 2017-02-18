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
import com.ivan1pl.animations.constants.SoundPlayMode;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.SoundData;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SelectSoundPlayModeConversationPrompt extends BaseEditorFixedSetPrompt {

    private final Prompt retPrompt;

    private final Animation animation;

    private final SoundData soundData;

    public SelectSoundPlayModeConversationPrompt(Prompt retPrompt, Animation animation, SoundData soundData) {
        super("b", "e", "a", "c");
        this.retPrompt = retPrompt;
        this.animation = animation;
        this.soundData = soundData;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
        if ("c".equalsIgnoreCase(string)) {
            return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_SELECT_SOUND_CANCELLED));
        } else {
            soundData.setPlayMode(SoundPlayMode.fromString(string));
            animation.setSoundData(soundData);
            return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_SOUND_UPDATED));
        }
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        return MessageUtil.formatPromptMessage(Messages.MSG_SELECT_SOUND_PLAY_MODE);
    }

}
