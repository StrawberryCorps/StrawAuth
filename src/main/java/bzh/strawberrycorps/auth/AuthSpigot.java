package bzh.strawberrycorps.auth;

import bzh.strawberrycorps.auth.listener.bukkit.PlayerListeners;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This file StrawSpigot is part of a project StrawAuth.
 * It was created on 05/04/2021 14:33 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class AuthSpigot extends JavaPlugin {

    public static AuthSpigot STRAW;

    @Override
    public void onEnable() {
        STRAW = this;
        long begin = System.currentTimeMillis();
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");

        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");
    }

    @Override
    public void onDisable() {

    }
}
