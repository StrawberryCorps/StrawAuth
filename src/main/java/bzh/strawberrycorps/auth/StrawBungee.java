package bzh.strawberrycorps.auth;

import bzh.strawberrycorps.auth.command.ChangePasswordCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

/*
 * This file StrawBungee is part of a project StrawAuth.
 * It was created on 05/04/2021 14:33 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBungee extends Plugin {

    public static StrawBungee STRAW;

    @Override
    public void onEnable() {
        STRAW = this;
        long begin = System.currentTimeMillis();
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChangePasswordCommand());

        getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");
    }

    @Override
    public void onDisable() {

    }
}