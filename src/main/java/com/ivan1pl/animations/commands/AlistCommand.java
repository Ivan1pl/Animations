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
import com.ivan1pl.animations.constants.Permissions;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.utils.MessageUtil;
import java.util.List;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Ivan1pl
 */
public class AlistCommand extends AnimationsCommand {
    
    public AlistCommand() {
        super(Permissions.PERMISSION_ADMIN, 0);
    }

    @Override
    protected void execute(CommandSender cs, String... args) {
        int page = 1;
        if (args.length > 0 && isNumeric(args[0])) {
            page = Integer.parseUnsignedInt(args[0]);
        }
        if (page < 1) {
            page = 1;
        }
        MessageUtil.sendInfoMessage(cs, Messages.MSG_DISPLAYING_PAGE, page, Animations.countPages());
        List<String> list = Animations.getPage(page);
        for (String item : list) {
            MessageUtil.sendInfoMessage(cs, Messages.MSG_ITEM, item);
        }
    }
    
}
