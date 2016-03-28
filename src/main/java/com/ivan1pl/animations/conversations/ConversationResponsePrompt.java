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

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public class ConversationResponsePrompt extends MessagePrompt {
    
    private final Prompt next;
    
    private final String message;
    
    public ConversationResponsePrompt(Prompt next, String message) {
        this.next = next;
        this.message = message;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext cc) {
        return next;
    }

    @Override
    public String getPromptText(ConversationContext cc) {
        return message;
    }
    
}
