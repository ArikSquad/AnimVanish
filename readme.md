my amazing image with light background in dark theme :D
![Title](docs/title.png)
___
Plugin that provides large library of pre-made effects to your vanish

### Current effects
- Herobrine effect (Lightning strike and night)
- Particle effect
- TNT effect
- NPC effect (requires [Citizens](https://www.spigotmc.org/resources/citizens.13811/))
- Zombie effect
- Blindness effect
- Sound effect

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
    # How long the effect will last? Default: 3
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
    # What sound the player will hear. Default: BLOCK_ANVIL_BREAK
    # You can find types here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html#enum-constant-summary
    type: BLOCK_ANVIL_FALL

```
### messages.yml:
```yaml
# Configuration file of AnimVanish #
# -------------------------------- #
# You can edit the colors using this instruction: https://www.digminecraft.com/lists/color_list_pc.php


# ---- GENERAL ---- #
prefix: "[&bAnimVanish&f]"

# ---- COMMANDS ---- #

not_player: "&cYou must be a player to use this command."
no_permission: "&cYou don't have permission to use this command."
invalid_args: "&cInvalid arguments."

reload: "&aSuccessfully reloaded!"


invis:
  particle:
    invalid_config: "&cInvalid particle configuration."
    invalid_particle: "&cThat particle doesn't exist or it isn't supported."
  blindness:
    message: "&eYou saw something and you now feel dizzy"


dependency:
  no_citizens: "&cCitizens is not installed."
  no_vanish: "&cYou must have SuperVanish or PremiumVanish installed to use this command."
```
![Title](docs/permissions.png)
```txt
animvanish.* - All permissions in one
animvanish.invis - This permission allows vanishing with effects
animvanish.reload - This permission can reload the plugin
animvanish.help - This permissions can see help for the plugin
```