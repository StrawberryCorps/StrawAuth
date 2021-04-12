package bzh.strawberry.auth.listener;

import bzh.strawberry.auth.AuthBungee;
import bzh.strawberry.auth.session.ProxiedSession;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/*
 * This file PlayerChat is part of a project StrawAuth.
 * It was created on 06/04/2021 14:47 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerChat implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());
        if (proxiedSession.isPremium() || proxiedSession.isLogged())return;
        if (!event.getMessage().equals("/login") && event.getMessage().equals("/register"))
            event.setCancelled(true);
        proxiedPlayer.sendMessage(new TextComponent(AuthBungee.STRAW.getPrefix() + "§cVous devez d'abord vous authentifier ou vous inscrire !"));
    }
}