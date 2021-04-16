package bzh.strawberry.auth;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.auth.listener.bukkit.PlayerListeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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
        getLogger().info("######################## [StrawAuth Spigot - " + getDescription().getVersion() + "] #################################");
        getLogger().info("Authors : " + Arrays.toString(this.getDescription().getAuthors().toArray()));


        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        assert  StrawAPI.getAPI() != null;
        assert  StrawAPI.getAPI().getDataFactory() != null;
        assert  StrawAPI.getAPI().getDataFactory().getDataSource() != null;


        getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        getLogger().info("######################## [StrawAuth Spigot - " + getDescription().getVersion() + "] #################################");
    }

    @Override
    public void onDisable() {

    }
}
