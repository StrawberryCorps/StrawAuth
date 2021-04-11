package bzh.strawberrycorps.auth.command;

import bzh.strawberry.api.command.AbstractBCommand;
import bzh.strawberrycorps.auth.AuthBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
 * This file LobbyCommand is part of a project StrawAuth.
 * It was created on 11/04/2021 16:19 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class LobbyCommand extends AbstractBCommand {

    public LobbyCommand() {
        super(AuthBungee.STRAW, "lobby", "auth.lobby");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer))
            return false;

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());

        if (proxiedSession.isPremium() || proxiedSession.isLogged()){
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§aVous allez être redirigé au Lobby !").create());
            proxiedPlayer.connect(ProxyServer.getInstance().getServerInfo("Lobby"));
            return true;
        }
        proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§6Utilise : /login <mdp>").create());
        return false;
    }
}