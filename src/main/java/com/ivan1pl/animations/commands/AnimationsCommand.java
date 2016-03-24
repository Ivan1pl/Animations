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
package com.ivan1pl.animations.commands;

import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.utils.MessageUtil;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public abstract class AnimationsCommand {
    
    private final String requiredPermission;
    
    @Getter
    private final int minArgs;
    
    public AnimationsCommand(String requiredPermission, int minArgs) {
        this.requiredPermission = requiredPermission;
        this.minArgs = minArgs;
    }
    
    public final void handle(CommandSender cs, String... args) {
        Player p = null;
        if(cs instanceof Player) {
            p = (Player) cs;
        }
        
        if(p != null && !p.hasPermission(requiredPermission)) {
            MessageUtil.sendErrorMessage(cs, Messages.MSG_NOT_PERMITTED);
            return;
        }
        
        if(args.length < minArgs) {
            MessageUtil.sendErrorMessage(cs, Messages.MSG_EXPECTED_MORE_ARGS, minArgs);
            return;
        }
        
        execute(cs, args);
    }
    
    protected abstract void execute(CommandSender cs, String... args);
    
}
