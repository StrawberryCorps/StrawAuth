package bzh.strawberry.auth.listener;

import bzh.strawberry.auth.AuthBungee;
import bzh.strawberry.auth.session.ProxiedSession;
import net.md_5.bungee.api.ProxyServer;
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
        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(event.getPlayer().getUniqueId());
        if (proxiedSession == null) return;
        if (proxiedSession.isLogged() || proxiedSession.isPremium()) return;
        if (event.getTarget() != null && !event.getTarget().getName().equals("Login"))
            event.setTarget(ProxyServer.getInstance().getServerInfo("Login"));
    }
}