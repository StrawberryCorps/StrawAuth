package bzh.strawberrycorps.auth.listener;

import bzh.strawberrycorps.auth.StrawBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/*
 * This file ServerConnect is part of a project StrawAuth.
 * It was created on 05/04/2021 21:40 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ServerConnect implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerConnect(ServerConnectEvent event) {
        ServerInfo serverInfo = event.getTarget();
        ProxiedSession proxiedSession = StrawBungee.STRAW.getProxiedSession(event.getPlayer().getUniqueId());
        if (proxiedSession == null) return;
        if (proxiedSession.isLogged() || proxiedSession.isPremium()) return;
        event.setTarget(ProxyServer.getInstance().getServerInfo("Login"));
    }
}