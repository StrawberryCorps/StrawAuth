package bzh.strawberrycorps.auth.listener;

import bzh.strawberry.api.StrawAPIBungee;
import bzh.strawberrycorps.auth.AuthBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import bzh.strawberrycorps.auth.util.MojangProfile;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/*
 * This file PreLogin is part of a project StrawAuth.
 * It was created on 05/04/2021 16:22 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PreLogin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PreLoginEvent event) {
        event.registerIntent(AuthBungee.STRAW);
        ProxyServer.getInstance().getScheduler().runAsync(AuthBungee.STRAW, () -> {
            long tick = System.currentTimeMillis();
            try {
                PendingConnection pendingConnection = event.getConnection();

                // Check pseudo crack
                if (!pendingConnection.getName().matches("[a-zA-Z0-9_]{3,16}")) {
                    event.setCancelled(true);
                    event.setCancelReason(new ComponentBuilder("§cVotre pseudo ne respecte pas la norme !").create());
                    event.completeIntent(AuthBungee.STRAW);
                    return;
                }

                // Check already online
                if (ProxyServer.getInstance().getPlayer(pendingConnection.getName()) != null) {
                    event.setCancelled(true);
                    event.setCancelReason(new ComponentBuilder("§cVous êtes déjà en ligne :(").create());
                    event.completeIntent(AuthBungee.STRAW);
                    return;
                }

                // Attribution de l'UUID
                UUID uuid = pendingConnection.getUniqueId();
                if (uuid == null) {
                    uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + pendingConnection.getName()).getBytes(StandardCharsets.UTF_8));
                }

                Connection connection = StrawAPIBungee.getAPI().getDataFactory().getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE pseudo = ?");
                preparedStatement.setString(1, pendingConnection.getName());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    uuid = UUID.fromString(resultSet.getString("uuid"));
                    pendingConnection.setOnlineMode(resultSet.getInt("premium") == 1);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();

                ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSessionFromDB(uuid);

                proxiedSession.setUsername(pendingConnection.getName());
                proxiedSession.setLastIP(pendingConnection.getAddress().getAddress().getHostAddress());

                MojangProfile mojangProfile = AuthBungee.STRAW.getMojang().getPremiumProfile(pendingConnection.getName());
                if (mojangProfile != null && mojangProfile.isOnlineMode()) {
                    proxiedSession.setUuid(mojangProfile.getUuid());
                    proxiedSession.setUsername(mojangProfile.getUser());
                    System.out.println(mojangProfile.toString());
                } else if (mojangProfile == null || mojangProfile.isError()) {
                    event.setCancelled(true);
                    event.setCancelReason(new ComponentBuilder("§cLes serveurs d'authentification sont indisponibles.").create());
                    event.completeIntent(AuthBungee.STRAW);
                    return;
                }

                proxiedSession.setPremium(mojangProfile.isOnlineMode());

                if (proxiedSession.isPremium()) {
                    if (mojangProfile.isOnlineMode())
                        proxiedSession.insert();
                }

                if (!proxiedSession.isPremium()) {
                    pendingConnection.setUniqueId(uuid);
                    proxiedSession.setUuid(uuid);
                }

                pendingConnection.setOnlineMode(proxiedSession.isPremium());
                proxiedSession.setVersion(pendingConnection.getVersion());
                AuthBungee.SESSIONS.add(proxiedSession);
                event.completeIntent(AuthBungee.STRAW);
                AuthBungee.STRAW.getLogger().info("StrawAuth - PreLogin " + proxiedSession.getUuid() + " - " + (System.currentTimeMillis() - tick) + "ms");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}