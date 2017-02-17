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

import com.ivan1pl.animations.data.Animations;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public abstract class BaseEditorStringPrompt extends StringPrompt {

    @Override
    public final Prompt acceptInput(ConversationContext context, String input) {
        String escapeString = Animations.getEditorEscapeString();
        if (input != null && escapeString != null && escapeString.length() > 0 &&
                input.startsWith(escapeString) && context.getForWhom() instanceof Player) {
            ((Player) context.getForWhom()).chat(input.replaceFirst(escapeString, ""));
            return this;
        }
        return accept(context, input);
    }

    protected abstract Prompt accept(ConversationContext context, String input);

}
