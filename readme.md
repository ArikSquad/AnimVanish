# Main page at [here.](https://www.spigotmc.org/resources/animvanish-1-18-animated-vanishing.102183/)
![Title](docs/title.png)
___
Our plugin makes your vanishing look great with a large library of pre-made effects on your vanish.

### Current effects
- Herobrine effect (Lightning strike and night)
- Particle effect
- TNT effect
- NPC effect (requires [Citizens](https://www.spigotmc.org/resources/citizens.13811/))
- Zombie effect
- Blindness effect
- Sound effect
- Turn effect

![Title](docs/config.png)
### config.yml: 
```yaml
# Configuration file of AnimVanish #
# -------------------------------- #


# ---- EFFECTS ---- #

effects:
  herobrine:
    # Will the time set to night with the effect? Default: True
    night: true
    # How long the night will last? Default: 3
    time: 3

  particle:
    # What particle will be used when using particle as the effect. Default: DRAGON_BREATH
    # You can find types here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html#enum-constant-summary
    type: DRAGON_BREATH
    # How many particles will be spawned. Default: 50
    amount: 50

  npc:
    # How many seconds the NPC will last. Default: 3
    despawn_after: 3

  zombie:
    # How many seconds the zombie will last. Default: 3
    despawn_after: 3

  blindness:
    # How many seconds the blindness effect will last. Default: 3
    effect_last: 3

  sound:
    # What sound the player will hear. Default: BLOCK_AMETHYST_BLOCK_HIT
    # You can find types here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html#enum-constant-summary
    type: BLOCK_AMETHYST_BLOCK_HIT
```
### messages.yml:
```yaml
# Configuration file of AnimVanish #
# -------------------------------- #
# Remember to add a space after prefix


# ---- GENERAL ---- #
prefix: '[<color:#AFEEEE>AnimVanish</color>] '

# ---- COMMANDS ---- #
invalid_args: '<red>Invalid arguments.</red>'
not_player: '<red>You must be a player.</red>'
reload: '<color:#b0ff5c>Reloaded configs</color>'


invis:
  only_to_vanish: '<red>This effect only applies when going into vanish.</red>'

  herobrine:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.herobrine)</green>"

  particle:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.particle)</green>"
    invalid_config: '<red>Invalid particle configuration. Ask an administrator to check config file.</red>'
    invalid_particle: "<red>That particle doesn't exist or it isn't supported.</red>"

  tnt:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.tnt)</green>"

  npc:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.npc)</green>"

  zombie:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.zombie)</green>"

  blindness:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.blindness)</green>"
    message: '<yellow>You saw something and you now feel dizzy</yellow>'
    author: '<green>You blinded all the players around you.</green>'

  sound:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.sound)</green>"
    invalid_config: '<red>Invalid sound configuration. Ask an administrator to check config file.</red>'
    invalid_sound: "<red>That sound doesn't exist or it isn't supported.</red>"

  turn:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.turn)</green>"

  firework:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.firework)</green>"

  blood:
    no_permission: "<red>You don't have permission to use this effect.</red> <green>(animvanish.invis.blood)</green>"


animvanish:
  help:
    no_permission: "<red>You don't have permission to use this command.</red> <green>(animvanish.help)</green>"
  reload:
    no_permission: "<red>You don't have permission to use this command.</red> <green>(animvanish.reload)</green>"


dependency:
  no_citizens: '<red>You must have Citizens installed to use this effect.</red>'
  no_vanish: '<red>You must have SuperVanish or PremiumVanish installed to use this command.</red>'
```
![Title](docs/permissions.png)
```txt
animvanish.* - All permissions in one
animvanish.invis - This permission allows vanishing with effects
animvanish.invis.[effect] - This permissions allows vanishing with a specific effect
animvanish.reload - This permission can reload the plugin
animvanish.help - This permissions can see help for the plugin
```
