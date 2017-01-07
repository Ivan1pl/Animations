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
package com.ivan1pl.animations.triggers;

import com.ivan1pl.animations.constants.MouseButton;
import com.ivan1pl.animations.constants.Permissions;
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.Animations;
import com.ivan1pl.animations.data.AnimationsLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 *
 * @author Ivan1pl
 */
public class TwoBlockTrigger extends BaseTrigger {
    
    private final AnimationsLocation triggerBlock1;
    
    private final MouseButton triggerButton1;
    
    private final AnimationsLocation triggerBlock2;
    
    private final MouseButton triggerButton2;
    
    public TwoBlockTrigger(Animation animation, AnimationsLocation triggerBlock1, MouseButton triggerButton1, AnimationsLocation triggerBlock2, MouseButton triggerButton2) {
        super(animation);
        this.triggerBlock1 = triggerBlock1;
        this.triggerBlock2 = triggerBlock2;
        this.triggerButton1 = triggerButton1;
        this.triggerButton2 = triggerButton2;
    }

    @Override
    public void execute() {
    }
    
    private boolean checkAction1(Action action) {
        if (null != triggerButton1) switch (triggerButton1) {
            case BOTH:
                return action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK;
            case LEFT:
                return action == Action.LEFT_CLICK_BLOCK;
            case RIGHT:
                return action == Action.RIGHT_CLICK_BLOCK;
        }
        return false;
    }
    
    private boolean checkAction2(Action action) {
        if (null != triggerButton2) switch (triggerButton2) {
            case BOTH:
                return action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK;
            case LEFT:
                return action == Action.LEFT_CLICK_BLOCK;
            case RIGHT:
                return action == Action.RIGHT_CLICK_BLOCK;
        }
        return false;
    }
    
    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || event.getHand() != EquipmentSlot.HAND
                || (!checkAction1(event.getAction()) && !checkAction2(event.getAction())) ||
                (player.hasPermission(Permissions.PERMISSION_ADMIN)
                && (player.getInventory().getItemInMainHand().getType().equals(Animations.getWandMaterial()) ||
                    player.getInventory().getItemInMainHand().getType().equals(Animations.getBlockSelectorMaterial())))) {
            return;
        }
        
        if (AnimationsLocation.isSameBlock(triggerBlock1, AnimationsLocation.fromLocation(event.getClickedBlock().getLocation())) && checkAction1(event.getAction())) {
            event.setCancelled(true);
            executeFirst();
        }
        if (AnimationsLocation.isSameBlock(triggerBlock2, AnimationsLocation.fromLocation(event.getClickedBlock().getLocation())) && checkAction2(event.getAction())) {
            event.setCancelled(true);
            executeSecond();
        }
    }
    
    private void executeFirst() {
        if (!isStarted() && !isFinished()) {
            startAnimation();
        }
    }
    
    private void executeSecond() {
        if (!isStarted() && isFinished()) {
            startReverseAnimation();
        }
    }
    
}
