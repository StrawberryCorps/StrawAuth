package bzh.strawberry.auth.listener.bukkit;

import bzh.strawberry.auth.AuthSpigot;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * This file PlayerListeners is part of a project StrawAuth.
 * It was created on 06/04/2021 14:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerListeners implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        event.getPlayer().teleport(new Location(AuthSpigot.STRAW.getServer().getWorlds().get(0), 2.5, 56, 8.5, 180,0));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }
}