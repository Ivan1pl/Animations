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
package com.ivan1pl.animations;

import com.ivan1pl.animations.commands.AnimationsCommandExecutor;
import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.conversations.EditAnimationConversationFactory;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Ivan1pl
 */
public class AnimationsPlugin extends JavaPlugin {
    
    @Getter
    private static AnimationsPlugin pluginInstance;
    
    private final AnimationsCommandExecutor executor = new AnimationsCommandExecutor();
    
    @Getter
    private EditAnimationConversationFactory conversationFactory;
    
    @Override
    public void onEnable() {
        pluginInstance = this;
        this.saveDefaultConfig();
        
        getCommand("anim").setExecutor(executor);
        getCommand("aplay").setExecutor(executor);
        getCommand("alist").setExecutor(executor);
        getCommand("adelete").setExecutor(executor);
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        Animations.reload();
        conversationFactory = new EditAnimationConversationFactory(this);
        
        getLogger().info(Messages.INFO_ENABLED);
    }
    
}
