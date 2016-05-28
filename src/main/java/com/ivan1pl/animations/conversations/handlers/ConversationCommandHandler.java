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
package com.ivan1pl.animations.conversations.handlers;

import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.exceptions.AnimationTypeException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 *
 * @author Ivan1pl
 */
public abstract class ConversationCommandHandler {
    
    @Getter
    private final String name;
    
    @Getter
    private final int paramsCount;
    
    private final List<String> paramDescriptions;
    
    @Getter
    private final boolean checkParamTypes;
    
    public ConversationCommandHandler(String name, int paramsCount, String... paramDescriptions) {
        this(name, paramsCount, true, paramDescriptions);
    }
    
    public ConversationCommandHandler(String name, int paramsCount, boolean checkParamTypes, String... paramDescriptions) {
        this.name = name;
        this.paramsCount = paramsCount;
        this.paramDescriptions = Arrays.asList(paramDescriptions);
        this.checkParamTypes = checkParamTypes;
    }
    
    public String format() {
        String ret = name;
        for (String param : paramDescriptions) {
            ret += " <" + param + ">";
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + this.paramsCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConversationCommandHandler other = (ConversationCommandHandler) obj;
        if (this.paramsCount != other.paramsCount) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
    
    public abstract Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException;
    
}
