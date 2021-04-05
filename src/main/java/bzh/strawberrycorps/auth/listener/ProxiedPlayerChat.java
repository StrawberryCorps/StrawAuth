package bzh.strawberrycorps.auth.listener;

import bzh.strawberrycorps.auth.StrawBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
 * This file ProxiedPlayerChat is part of a project StrawAuth.
 * It was created on 05/04/2021 16:20 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ProxiedPlayerChat implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        ProxiedSession session = StrawBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());

    }
}