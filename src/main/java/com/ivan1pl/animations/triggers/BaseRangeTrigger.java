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

import com.ivan1pl.animations.data.Animation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ivan1pl
 */
public abstract class BaseRangeTrigger extends BaseTrigger {
    
    private int range;
    
    private final Set<Player> playersInRange = new HashSet<>();
    
    public BaseRangeTrigger(Animation animation) {
        super(animation);
    }
    
    protected final boolean isPlayerInRange(Player player) {
        return getAnimation().isPlayerInRange(player, range);
    }
    
    protected final boolean isAnyPlayerInRange() {
        return !playersInRange.isEmpty();
    }
    
    @Override
    public void register() {
        init();
        super.register();
    }
    
    @Override
    public void unregister() {
        super.unregister();
        playersInRange.clear();
    }
    
    protected final void init() {
        playersInRange.clear();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (isPlayerInRange(p)) {
                playersInRange.add(p);
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        handlePlayerMoved(p, isPlayerInRange(p));
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        handlePlayerMoved(p, isPlayerInRange(p));
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        handlePlayerMoved(p, false);
    }
    
    private void handlePlayerMoved(Player player, boolean inRange) {
        if (inRange) {
            playersInRange.add(player);
        } else {
            playersInRange.remove(player);
        }
        onPlayerMoved();
    }
    
    protected void onPlayerMoved() {
        execute();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
