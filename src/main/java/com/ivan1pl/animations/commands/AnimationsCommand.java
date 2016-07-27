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
import com.ivan1pl.animations.data.Animations;
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
    
    private final boolean playerOnly;
    
    public AnimationsCommand(String requiredPermission, int minArgs, boolean playerOnly) {
        this.requiredPermission = requiredPermission;
        this.minArgs = minArgs;
        this.playerOnly = playerOnly;
    }
    
    public AnimationsCommand(String requiredPermission, int minArgs) {
        this(requiredPermission, minArgs, false);
    }
    
    public final void handle(CommandSender cs, String... args) {
        Animations.debug(Messages.DEBUG_COMMAND_EXECUTED, this.getClass().getName(), cs.getName());
        Player p = null;
        if(cs instanceof Player) {
            p = (Player) cs;
        } else if (playerOnly) {
            MessageUtil.sendErrorMessage(cs, Messages.MSG_PLAYER_ONLY);
        }
        
        if(p != null && !p.hasPermission(requiredPermission)) {
            MessageUtil.sendErrorMessage(cs, Messages.MSG_NOT_PERMITTED);
            return;
        }
        
        if(args.length < minArgs) {
            MessageUtil.sendErrorMessage(cs, Messages.MSG_EXPECTED_MORE_ARGS, new Long(minArgs));
            return;
        }
        
        execute(cs, args);
    }
    
    protected abstract void execute(CommandSender cs, String... args);
    
    protected static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
}
