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
package com.ivan1pl.animations.conversations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 *
 * @author Ivan1pl
 */
public class ConversationAnswer {
    
    @Getter
    private final String answer;
    
    @Getter
    private final int paramsCount;
    
    private final List<String> paramDescriptions;
    
    public ConversationAnswer(String answer, int paramsCount, String... paramDescriptions) {
        this.answer = answer;
        this.paramsCount = paramsCount;
        this.paramDescriptions = Arrays.asList(paramDescriptions);
    }
    
    public String format() {
        String ret = answer;
        for (String param : paramDescriptions) {
            ret += " <" + param + ">";
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.answer);
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
        final ConversationAnswer other = (ConversationAnswer) obj;
        if (this.paramsCount != other.paramsCount) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }
    
}
