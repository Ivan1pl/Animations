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
package com.ivan1pl.animations.utils;

import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Ivan1pl
 */
public class MessageUtil {
    
    private static final String NAME = "Animations";
    
    private static final String PREFIX_ERR = ChatColor.RED + "[" + ChatColor.DARK_RED + NAME + ChatColor.RED + "] " + ChatColor.RESET;
    
    private static final String PREFIX_INFO = ChatColor.AQUA + "[" + ChatColor.BLUE + NAME + ChatColor.AQUA + "] " + ChatColor.RESET;
    
    private static final String PREFIX_NOFORMAT = "[" + NAME + "] ";
    
    public static void sendErrorMessage(CommandSender sender, String message, Object... messageParams) {
        if (sender instanceof Player) {
            sender.sendMessage(PREFIX_ERR + MessageFormat.format(message, messageParams));
        } else {
            sender.sendMessage(PREFIX_NOFORMAT + MessageFormat.format(message, messageParams));
        }
    }
    
    public static void sendInfoMessage(CommandSender sender, String message, Object... messageParams) {
        if (sender instanceof Player) {
            sender.sendMessage(PREFIX_INFO + MessageFormat.format(message, messageParams));
        } else {
            sender.sendMessage(PREFIX_NOFORMAT + MessageFormat.format(message, messageParams));
        }
    }
    
    public static String formatMessage(String message, Object... messageParams) {
        return MessageFormat.format(message, messageParams);
    }
    
    public static String formatMessageWithPrefix(String message, Object... messageParams) {
        return PREFIX_NOFORMAT + formatMessage(message, messageParams);
    }
    
}
