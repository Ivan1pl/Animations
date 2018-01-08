# Animations

Animations plugin for Bukkit. It allows you to create stationary and moving animations and set triggers for them.

## Features

* Create an animation and play it by command
* Create moving animation - select area, step and range and everything in selected area will move - even players (except flying players)
* Add, remove and update frames
* Set interval in ticks between frames
* Define triggers for your animation
* Add sound to your animation
* Set limits to prevent lag (max animations playing at the same time etc, for details check **config.yml**)

## Triggers

* Range trigger - when a player enters specified range, the animation will start. When all players leave the area, the animation will play in reverse
* Loop trigger - the animation will continue playing as long as there is at least one player in specified range
* Block trigger - when a player in range clicks specified block, the animation will start. When all players leave the area, the animation will play in reverse
* Password trigger - when a player in range enters specified password in public chat, the animation will start. When all players leave the area, the animation will play in reverse
* Two-block trigger - when a player clicks first specified block, the animation will start. When a player clicks second specified block, the animation will play in reverse
* Chain trigger - when other animation reaches specified frame, the animation will start. When all players leave the area, it will be restored to original state

# Manual

## Commands

List of commands included in the plugin:
* **/alist [page]**

  Show list of all animations
* **/anim <animation_name>**

  Create or edit animation '<animation_name>'
* **/adelete <animation_name>**

  Delete animation '<animation_name>'
* **/aplay <animation_name>**

  Play animation '<animation_name>'

## Creating new animation

To create new animation named '<animation_name>', simply use command /anim <animation_name>. This will start conversation-based editor which will guide you through creation process. You will be asked to do the following:
1. **Select animation area.**

   Use blaze powder to set the area of animation.

   **IMPORTANT:**
   
   For stationary animations, all frames have to fit inside the selected area. Blocks outside of the area will not be affected when playing the animation.
   
   For moving animations, select only the moving part. For example if you want a moving animation of a boat, don't select the whole river - just the boat.
2. **Set animation type.**

   For stationary animation type 'type s'. For moving animation type 'type m'. Default is stationary animation.

When done, type 'y' - that will create empty animation and open the editor. Alternatively, you can cancel creation of new animation by typing 'cancel'.

## Editing existing animation

To edit existing animation named '<animation_name>', use command /anim <animation_name>. This will start conversation-based editor.

Editing an animation can take long and you may want to chat during the process. To do that, simply add '!' to the beginning of your message. Messages starting with '!' will not be passed to the editor but will appear in public chat instead.

You can use the following operations (simply type them in chat):

### For stationary animation

Operations available for stationary animation:
* **addframe**

  Saves all blocks in animation area as new frame.
* **removeframe <frame_index>**

  Removes frame <frame_index>. Frames are indexed starting from 0 so if you have n frames, valid indexes are from range 0 to n-1.
* **updateframe <frame_index>**

  Updates frame <frame_index> with current content of animation area.
* **previewframe <frame_index>**

  Display frame <frame_index>
* **preview**

  Play the animation
* **swapframes <first_index> <second_index>**

  Swap frames <first_index> and <second_index>
* **interval <frame_interval>**

  Set interval in server ticks. 1 second = 20 ticks.
* **help**

  Display all available operations.
* **trigger <trigger_type> [max_range]**

  Set trigger. This will be explained in separate section.
* **deletetrigger**

  Delete trigger.
* **sound <sound_range> <sound_pitch> <sound_volume>**
  
  Set sound. This will be explained in separate section.
* **deletesound**

  Delete sound.
* **cancel**

  Exit the editor.
* **save**

  Save animation.

### For moving animation

Operations available for moving animation:
* **step <step_x> <step_y> <step_z>**

  Moving animations don't have frames. Instead they have step and max distance. Instead of changing to next frame, the object selected when the animation was created will be moved by step defined here.
* **maxdistance <max_distance>**

  Set number of steps.
* **preview**

  Play the animation.
* **interval <frame_interval>**

  Set interval in server ticks. 1 second = 20 ticks.
* **updatebackground**

  Update animation background. When the object moves, the background is used to fill the space where the object was before. This should be used every time you change step or max distance.

  IMPORTANT: remove moving object before using this command.
* **help**

  Display all available operations.
* **trigger <trigger_type> <max_range>**

  Set trigger. This will be explained in separate section.
* **deletetrigger**

  Delete trigger.
* **sound <sound_range> <sound_pitch> <sound_volume>**

  Set sound. This will be explained in separate section.
* **deletesound**

  Delete sound.
* **cancel**

  Exit the editor.
* **save**

  Save animation.

## Triggers

For each animation, you can set a trigger which will start it. Without a trigger, the animation can only be started by command (**/aplay <animation_name>**). With triggers, animations can be started by events - for example a player typing something in chat.

Trigger types:
* **Range trigger**
* **Loop trigger**
* **Block trigger**
* **Password trigger**
* **Two-block trigger**
* **Chain trigger**

### Range trigger

Animations with range triggers will start playing when first player enters the area around animation (specified by max_range). When all players leave the area, reverse animation will be played.

#### Example: gate
When a player enters the area around the gate, it opens. It stays open for as long as there are players close to it. When all players leave the area, the gate closes.

This trigger can be set while editing animation by commands:
* **trigger range <max_range>**
* **trigger r <max_range>**

### Loop trigger

Animations with loop trigger will start playing when first player enters the area around animation. It will continue playing for as long as there are players in range. When all players leave the area, it will stop playing.

This trigger can be set while editing animation by commands:
* **trigger loop <max_range>**
* **trigger l <max_range>**

### Block trigger

Animations with block trigger will start playing when a player in range clicks on a block with specified mouse button. When all players leave the area, reverse animation will be played.

This trigger can be set while editing animation by commands:
* **trigger block <max_range>**
* **trigger b <max_range>**

You will then be asked to select a block using blaze rod and type 'l', 'r' or 'b' to confirm selection or 'c' to cancel creation of block trigger. The letter you type determines which mouse button should be used to trigger the animation - 'l' means left mouse button, 'r' means right mouse button and 'b' means that both buttons can be used to start the animation. The animation will start when a player clicks on selected block with specified mouse button.

### Password trigger

Animations with password trigger will start playing when a player in range types the password in chat. When all players leave the area, reverse animation will be played.

This trigger can be set while editing animation by commands:
* **trigger password <max_range>**
* **trigger p <max_range>**

You will then be asked to type the password which will trigger the animation.

### Two-block trigger

Animations with two-block trigger will start playing when a player clicks on the first of two specified blocks. When a player clicks on the second block, the reverse animation will be played.

This trigger can be set while editing animation by commands:
* **trigger twoblock**
* **trigger t**

You will then be asked twice to select a block using blaze rod and type 'l', 'r' or 'b' to confirm selection or 'c' to cancel creation of block trigger. The letter you type determines which mouse button should be used to trigger the animation - 'l' means left mouse button, 'r' means right mouse button and 'b' means that both buttons can be used to start the animation. The animation will start when a player clicks on the first selected block with the first specified mouse button and reverse animation will start when a player clicks on the second selected block with the second specified mouse button.

### Chain trigger

Animations with chain trigger will start playing when another animation reaches specified frame. When all players leave the area, it will be restored to original state.

This trigger can be set while editing animation by commands:
* **trigger chain <max_range>**
* **trigger c <max_range>**

You will then be asked to type the name and frame index of the animation which will trigger this animation. You can cancel creation of this trigger by typing 'c'. The animation will start when selected animation reaches specified frame.

## Sounds

For each animation, you can set a sound which will be played when animation starts, ends or for each frame.

The sound can be set while editing animation by command:

**sound <sound_range> <sound_pitch> <sound_volume>**

where pitch and volume should be values between 1 and 100.

You will then be asked to select the sound by typing '**select <sound_name>**'. You can cancel by typing '**cancel**'. You can also see the list of all available sounds by typing '**list [page]**'.

After you select the sound, you also need to choose when it will be played. You will be asked to type 'b' (when animation begins), 'e' (when animation ends) or 'a' (all frames) or 'c' to cancel selection and return to the editor.

After you save the animation, whenever the animation reaches specified state (beginning/end/all frames), the selected sound will be played to all players in range of <sound_range> blocks using specified pitch and volume.
