# Animations

Animations plugin for Bukkit. It allows you to create stationary and moving animations and set triggers for them.

## Features

* Create an animation and play it by command
* Create moving animation - select area, step and range and everything in selected area will move - even players (except flying players)
* Add and remove frames
* Set interval in ticks between frames
* Define triggers for your animation
* Set limits to prevent lag (max animations playing at the same time etc, for details check **config.yml**)

## Triggers

* Range trigger - when a player enters specified range, the animation will start. When all players leave the area, the animation will play in reverse
* Loop trigger - the animation will continue playing as long as there is at least one player in specified range
* Block trigger - when a player in range clicks specified block, the animation will start. When all players leave the area, the animation will play in reverse
* Password trigger - when a player in range enters specified password in public chat, the animation will start. When all players leave the area, the animation will play in reverse
* Two-block trigger - when a player clicks first specified block, the animation will start. When a player clicks second specified block, the animation will play in reverse
