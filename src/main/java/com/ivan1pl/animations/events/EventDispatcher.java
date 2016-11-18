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
package com.ivan1pl.animations.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ivan1pl
 */
public class EventDispatcher {
    
    private final Map<String, List<EventListener>> listeners = new HashMap<>();
    
    public void addEventListener(String type, EventListener listener) {
        List<EventListener> l = listeners.get(type);
        if (l == null) {
            l = new ArrayList<>();
            l.add(listener);
            listeners.put(type, l);
        } else {
            if (!l.contains(listener)) {
                l.add(listener);
            }
        }
    }
    
    public void removeEventListener(String type, EventListener listener) {
        List<EventListener> l = listeners.get(type);
        if (l != null) {
            l.remove(listener);
        }
    }
    
    public void dispatchEvent(Event event) {
        if (event == null) {
            return;
        }
        List<EventListener> l = listeners.get(event.getType());
        if (l != null) {
            for (EventListener listener : l) {
                listener.onEvent(event);
            }
        }
    }
    
}
