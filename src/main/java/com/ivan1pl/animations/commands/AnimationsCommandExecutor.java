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

import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Ivan1pl
 */
public class AnimationsCommandExecutor implements CommandExecutor {
    
    private final Map<String, AnimationsCommand> commandMapping = new HashMap<>();
    
    public AnimationsCommandExecutor() {
        addCommandMapping("anim", new AnimCommand());
        addCommandMapping("aplay", new AplayCommand());
        addCommandMapping("alist", new AlistCommand());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        String command = cmnd.getName().toLowerCase();
        if (commandMapping.containsKey(command)) {
            commandMapping.get(command).handle(cs, strings);
            return true;
        }
        return false;
    }
    
    private void addCommandMapping(String command, AnimationsCommand handler) {
        commandMapping.putIfAbsent(command, handler);
    }
    
}
