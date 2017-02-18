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
import com.ivan1pl.animations.data.SoundData;
import com.ivan1pl.animations.utils.MessageUtil;
import com.ivan1pl.animations.utils.StringUtil;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class SelectSoundConversationPrompt extends BaseEditorValidatingPrompt {

    private static final String[] sounds;

    private static final int PAGE_SIZE = 50;

    private static final int pageCount;

    private final Prompt retPrompt;

    private final Animation animation;

    private final SoundData soundData;

    static {
        Sound[] s = Sound.values();
        sounds = new String[s.length];
        for (int i = 0; i < s.length; ++i) {
            sounds[i] = s[i].name();
        }
        int pc = sounds.length / PAGE_SIZE;
        if (sounds.length % PAGE_SIZE != 0) {
            pc++;
        }
        pageCount = pc;
    }

    public SelectSoundConversationPrompt(Prompt retPrompt, Animation animation, SoundData soundData) {
        this.retPrompt = retPrompt;
        this.animation = animation;
        this.soundData = soundData;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext cc, String string) {
        if ("cancel".equalsIgnoreCase(string)) {
            return new ConversationResponsePrompt(retPrompt, MessageUtil.formatInfoMessage(Messages.MSG_SELECT_SOUND_CANCELLED));
        } else {
            String[] params = string.split("\\s+");
            if (params.length == 2) {
                if (params[0].equalsIgnoreCase("select")) {
                    String soundName = getSound(params[1]);
                    if (soundName != null) {
                        soundData.setName(soundName);
                        return new ConversationResponsePrompt(new SelectSoundPlayModeConversationPrompt(retPrompt, animation, soundData),
                                MessageUtil.formatInfoMessage(Messages.MSG_SOUND_SELECTED));
                    } else {
                        return new ConversationResponsePrompt(this, MessageUtil.formatErrorMessage(Messages.MSG_INVALID_SOUND, params[1]));
                    }
                } else if (params[0].equalsIgnoreCase("list")) {
                    if (isValidPage(Integer.parseUnsignedInt(params[1]))) {
                        return displayPage(Integer.parseUnsignedInt(params[1]));
                    } else {
                        return new ConversationResponsePrompt(this, MessageUtil.formatErrorMessage(Messages.MSG_INVALID_PAGE, Integer.parseUnsignedInt(params[1]), pageCount));
                    }
                } else {
                    return this;
                }
            } else if (params.length == 1 && params[0].equalsIgnoreCase("list")) {
                return displayPage(1);
            } else {
                return this;
            }
        }
    }

    private Prompt displayPage(int page) {
        int startIndex = (page-1) * PAGE_SIZE;
        int stopIndex = Math.min(page * PAGE_SIZE - 1, sounds.length - 1);
        String soundList = "";
        for (int i = startIndex; i <= stopIndex; ++i) {
            if (soundList.length() == 0) {
                soundList = sounds[i];
            } else {
                soundList = soundList + ", " + sounds[i];
            }
        }
        return new ConversationResponsePrompt(this, MessageUtil.formatInfoMessage(Messages.MSG_SOUND_LIST, page, pageCount, soundList));
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        return MessageUtil.formatPromptMessage(Messages.MSG_SELECT_SOUND);
    }

    @Override
    protected boolean isInputValid(ConversationContext cc, String string) {
        if (string == null) {
            return false;
        }
        if (string.equalsIgnoreCase("cancel") || string.equalsIgnoreCase("list")) {
            return true;
        }
        String[] params = string.split("\\s+");
        if (params.length != 2) {
            return false;
        }
        if (params[0].equalsIgnoreCase("select")) {
            return true;
        }
        return params[0].equalsIgnoreCase("list") && StringUtil.isUnsignedInteger(params[1]);
    }

    private String getSound(String name) {
        for (String s : sounds) {
            if (s.equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    private boolean isValidPage(int page) {
        return page > 0 && page <= pageCount;
    }

}
