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
package com.ivan1pl.animations.listeners;

import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.constants.Permissions;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.data.Selection;
import com.ivan1pl.animations.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 *
 * @author Ivan1pl
 */
public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || !player.hasPermission(Permissions.PERMISSION_ADMIN) || event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        
        if (player.getInventory().getItemInMainHand().getType().equals(Animations.getWandMaterial())) {
            Selection selection = Animations.getSelection(player);

            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                event.setCancelled(true);
                selection.setPoint1(event.getClickedBlock().getLocation());
                int volume = selection.getVolume();
                if (volume == 0) {
                    MessageUtil.sendInfoMessage(player, Messages.MSG_POINT1_SET, "");
                } else {
                    MessageUtil.sendInfoMessage(player, Messages.MSG_POINT1_SET, " (" + new Long(volume) + ")");
                }
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                selection.setPoint2(event.getClickedBlock().getLocation());
                event.setCancelled(true);
                int volume = selection.getVolume();
                if (volume == 0) {
                    MessageUtil.sendInfoMessage(player, Messages.MSG_POINT2_SET, "");
                } else {
                    MessageUtil.sendInfoMessage(player, Messages.MSG_POINT2_SET, " (" + new Long(volume) + ")");
                }
            }
        } else if (player.getInventory().getItemInMainHand().getType().equals(Animations.getBlockSelectorMaterial())) {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);
                Animations.setBlockSelection(player, event.getClickedBlock().getLocation());
                MessageUtil.sendInfoMessage(player, Messages.MSG_BLOCK_SELECTION_SET);
            }
        }
    }
    
}
