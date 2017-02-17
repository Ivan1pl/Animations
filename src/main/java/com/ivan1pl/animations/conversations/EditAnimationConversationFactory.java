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

import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Ivan1pl
 */
public class EditAnimationConversationFactory implements ConversationAbandonedListener {
    
    private final ConversationFactory factory;
    
    public EditAnimationConversationFactory(Plugin plugin) {
        factory = new ConversationFactory(plugin)
                .withModality(false)
                .withTimeout(Animations.getEditorTimeout())
                .withLocalEcho(true)
                .addConversationAbandonedListener(this);
    }
    
    public void startConversation(Player player, String animationName) {
        Animation animation = Animations.getAnimation(animationName);
        Conversation c = factory.withFirstPrompt(new EditAnimationConversationPrompt(animation != null)).buildConversation(player);
        ConversationContext cc = c.getContext();
        cc.setSessionData("name", animationName);
        cc.setSessionData("animation", animation);
        c.begin();
    }

    @Override
    public void conversationAbandoned(ConversationAbandonedEvent cae) {
        String name = (String) cae.getContext().getSessionData("name");
        Animation animation = (Animation) cae.getContext().getSessionData("animation");
        if (!cae.gracefulExit()) {
            if (animation != null) {
                animation.stop();
            }
            
            Animations.reloadAnimation(name);
        }
    }
    
}
