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
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Ivan1pl
 */
public abstract class ConversationCommandHandler {
    
    private final String name;
    private final int paramsCount;
    
    private final List<String> paramDescriptions;
    
    private final boolean checkParamTypes;
    private final int optionalParamsCount;
    
    public ConversationCommandHandler(String name, int paramsCount, String... paramDescriptions) {
        this(name, paramsCount, 0, true, paramDescriptions);
    }
    
    public ConversationCommandHandler(String name, int paramsCount, int optionalParamsCount, boolean checkParamTypes, String... paramDescriptions) {
        this.name = name;
        this.paramsCount = paramsCount;
        this.paramDescriptions = Arrays.asList(paramDescriptions);
        this.checkParamTypes = checkParamTypes;
        this.optionalParamsCount = optionalParamsCount;
    }
    
    public String format() {
        String ret = name;
        int count = 1;
        for (String param : paramDescriptions) {
            if (count > paramsCount) {
                ret += " [" + param + "]";
            } else {
                ret += " <" + param + ">";
            }
            count++;
        }
        return ret;
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
        if (this.paramsCount != other.paramsCount || this.optionalParamsCount != other.optionalParamsCount) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.paramsCount;
        hash = 59 * hash + this.optionalParamsCount;
        return hash;
    }
    
    public abstract Prompt handle(ConversationContext cc, Animation animation, String animationName, String[] params) throws AnimationTypeException;
    
    public boolean customCheckParamTypes(String[] params) {
        return true;
    }

    public String getName() {
        return name;
    }

    public int getParamsCount() {
        return paramsCount;
    }

    public boolean isCheckParamTypes() {
        return checkParamTypes;
    }

    public int getOptionalParamsCount() {
        return optionalParamsCount;
    }
}
