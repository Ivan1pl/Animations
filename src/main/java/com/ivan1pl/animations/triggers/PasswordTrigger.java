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
import com.ivan1pl.animations.events.Event;
import com.ivan1pl.animations.events.EventListener;
import com.ivan1pl.animations.tasks.AnimationTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Ivan1pl
 */
public class PasswordTrigger extends BaseRangeTrigger {
    
    private final String password;
    
    public PasswordTrigger(Animation animation, String password) {
        super(animation);
        this.password = password;
    }

    @Override
    protected void onPlayerMoved() {
        if (!isAnyPlayerInRange() && !isStarted() && isFinished()) {
            AnimationTask task = getAnimation().playReverse();
            if (task != null) {
                setStarted(true);
                task.attachListener(new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        setStarted(false);
                        setFinished(false);
                    }
                });
            } else {
                getAnimation().showFrame(0);
                setStarted(false);
                setFinished(false);
            }
        }
    }

    @Override
    public void execute() {
        if (isAnyPlayerInRange() && !isStarted() && !isFinished()) {
            AnimationTask task = getAnimation().play();
            if (task != null) {
                setStarted(true);
                task.attachListener(new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        setStarted(false);
                        setFinished(true);
                    }
                });
            } else {
                getAnimation().showFrame(0);
                setStarted(false);
                setFinished(false);
            }
        }
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(password) && isPlayerInRange(event.getPlayer())) {
            execute();
        }
    }
    
}
