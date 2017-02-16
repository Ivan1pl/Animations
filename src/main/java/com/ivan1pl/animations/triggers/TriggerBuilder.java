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
import com.ivan1pl.animations.data.Animation;
import com.ivan1pl.animations.data.AnimationsLocation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Ivan1pl
 */
public class TriggerBuilder {
    
    private final Animation animation;
    
    private int range;
    
    private TriggerType triggerType = TriggerType.RANGE;
    
    private String password;
    
    private List<AnimationsLocation> triggerBlocks;
    
    private List<MouseButton> triggerButtons;

    private String animationName;

    private int frame;
    
    public TriggerBuilder(Animation animation) {
        this.animation = animation;
    }
    
    public TriggerBuilder setRange(int range) {
        this.range = range;
        return this;
    }
    
    public TriggerBuilder setTriggerType(TriggerType type) {
        this.triggerType = type;
        return this;
    }
    
    public TriggerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public TriggerBuilder setTriggerBlock(AnimationsLocation triggerBlock) {
        this.triggerBlocks = Arrays.asList(triggerBlock);
        return this;
    }
    
    public TriggerBuilder setTriggerButton(MouseButton triggerButton) {
        this.triggerButtons = Arrays.asList(triggerButton);
        return this;
    }
    
    public TriggerBuilder addTriggerBlock(AnimationsLocation triggerBlock, MouseButton triggerButton) {
        if (triggerBlocks == null) {
            triggerBlocks = new ArrayList<>();
        }
        if (triggerButtons == null) {
            triggerButtons = new ArrayList<>();
        }
        triggerBlocks.add(triggerBlock);
        triggerButtons.add(triggerButton);
        return this;
    }
    
    public TriggerBuilder setTriggerBlocks(List<AnimationsLocation> triggerBlocks) {
        this.triggerBlocks = triggerBlocks;
        return this;
    }
    
    public TriggerBuilder setTriggerButtons(List<MouseButton> triggerButtons) {
        this.triggerButtons = triggerButtons;
        return this;
    }

    public TriggerBuilder setAnimationName(String animationName) {
        this.animationName = animationName;
        return this;
    }

    public TriggerBuilder setFrame(int frame) {
        this.frame = frame;
        return this;
    }
    
    public Trigger create() {
        Trigger t;
        switch (triggerType) {
            case LOOP:
                t = new LoopTrigger(animation);
                ((BaseRangeTrigger) t).setRange(range);
                break;
            case PASSWORD:
                t = new PasswordTrigger(animation, password);
                ((BaseRangeTrigger) t).setRange(range);
                break;
            case BLOCK:
                t = new BlockTrigger(animation, triggerBlocks.get(0), triggerButtons.get(0));
                ((BaseRangeTrigger) t).setRange(range);
                break;
            case TWO_BLOCK:
                t = new TwoBlockTrigger(animation, triggerBlocks.get(0), triggerButtons.get(0), triggerBlocks.get(1), triggerButtons.get(1));
                break;
            case CHAIN:
                t = new ChainTrigger(animation, animationName, frame);
                ((BaseRangeTrigger) t).setRange(range);
                break;
            case RANGE:
            default:
                t = new RangeTrigger(animation);
                ((BaseRangeTrigger) t).setRange(range);
                break;
        }
        return t;
    }
    
    public TriggerBuilderData createBuilderData() {
        return new TriggerBuilderData(triggerType, range, password, triggerBlocks, triggerButtons, animationName, frame);
    }
    
}
