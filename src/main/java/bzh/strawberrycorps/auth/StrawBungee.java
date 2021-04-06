package bzh.strawberrycorps.auth;

import bzh.strawberry.api.StrawAPIBungee;
import bzh.strawberry.api.util.SymbolUtils;
import bzh.strawberrycorps.auth.command.ChangePasswordCommand;
import bzh.strawberrycorps.auth.command.LoginCommand;
import bzh.strawberrycorps.auth.command.RegisterCommand;
import bzh.strawberrycorps.auth.listener.PostLogin;
import bzh.strawberrycorps.auth.listener.PreLogin;
import bzh.strawberrycorps.auth.listener.ProxiedPlayerDisconnect;
import bzh.strawberrycorps.auth.listener.ServerConnect;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import bzh.strawberrycorps.auth.util.Mojang;
import bzh.strawberrycorps.auth.util.MojangProfile;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/*
 * This file StrawBungee is part of a project StrawAuth.
 * It was created on 05/04/2021 14:33 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBungee extends Plugin {

    public static StrawBungee STRAW;
    public static ArrayList<ProxiedSession> SESSIONS;

    private Mojang mojang;

    @Override
    public void onEnable() {
        STRAW = this;
        long begin = System.currentTimeMillis();
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");
        SESSIONS = new ArrayList<>();

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChangePasswordCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LoginCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RegisterCommand());

        getProxy().getPluginManager().registerListener(this, new PostLogin());
        getProxy().getPluginManager().registerListener(this, new PreLogin());
        getProxy().getPluginManager().registerListener(this, new ProxiedPlayerDisconnect());
        getProxy().getPluginManager().registerListener(this, new ServerConnect());

        this.mojang = new Mojang(getLogger());

        getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        getLogger().info("######################## [StrawAuth - " + getDescription().getVersion() + "] #################################");
    }

    @Override
    public void onDisable() {

    }

    public String getPrefix() {
        return "§cSTRAW §7" + SymbolUtils.ARROW_DOUBLE + " §r";
    }

    public Mojang getMojang() {
        return mojang;
    }

    public ProxiedSession getProxiedSession(UUID uuid) {
        return SESSIONS.stream().filter(proxiedSession -> proxiedSession.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public ProxiedSession getProxiedSessionFromDB(UUID uuid) {
        if (getProxiedSession(uuid) != null)
            return getProxiedSession(uuid);
        return new ProxiedSession(uuid);
    }
}