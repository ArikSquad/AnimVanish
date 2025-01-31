package eu.mikart.animvanish.hooks.impl;

import eu.mikart.abeovanish.api.BukkitAbeoVanishAPI;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.entity.Player;

public class AbeoVanishHook extends Hook {

    private BukkitAbeoVanishAPI abeoVanishAPI;

    public AbeoVanishHook() {
        super("AbeoVanish");
    }

    @Override
    public void init() {
        this.abeoVanishAPI = BukkitAbeoVanishAPI.getInstance();
    }

    @Override
    public void vanish(OnlineUser p) {
        Player player = ((BukkitUser) p).getPlayer();
        abeoVanishAPI.setVanished(player, !isVanished(p));
    }

    @Override
    public boolean isVanished(OnlineUser p) {
        Player player = ((BukkitUser) p).getPlayer();
        return abeoVanishAPI.isVanished(player);
    }
}
