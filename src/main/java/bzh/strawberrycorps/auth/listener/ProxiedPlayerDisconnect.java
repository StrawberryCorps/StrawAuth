package bzh.strawberrycorps.auth.listener;

import bzh.strawberrycorps.auth.StrawBungee;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
 * This file ProxiedPlayerDisconnect is part of a project StrawAuth.
 * It was created on 05/04/2021 16:21 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ProxiedPlayerDisconnect implements Listener {

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        StrawBungee.STRAW.getProxiedSession(event.getPlayer().getUniqueId()).save();
        StrawBungee.SESSIONS.remove(StrawBungee.STRAW.getProxiedSession(event.getPlayer().getUniqueId()));
    }
}