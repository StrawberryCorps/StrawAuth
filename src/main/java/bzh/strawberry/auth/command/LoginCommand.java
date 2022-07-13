package bzh.strawberry.auth.command;

import bzh.strawberry.api.command.AbstractBCommand;
import bzh.strawberry.auth.AuthBungee;
import bzh.strawberry.auth.session.ProxiedSession;
import bzh.strawberry.auth.util.Encrypt;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
 * This file LoginCommand is part of a project StrawAuth.
 * It was created on 05/04/2021 16:19 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class LoginCommand extends AbstractBCommand {

    public LoginCommand() {
        super(AuthBungee.STRAW, "login", "auth.login");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer proxiedPlayer))
            return false;

        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());

        if (proxiedSession.isPremium())return true;
        if (strings.length < 1) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§6Utilise : /login <mdp>").create());
            return false;
        }

        if (!proxiedSession.isPremium() && proxiedSession.getPassword() == null) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVous êtes pas enregistré !").create());
            return false;
        }
        if (proxiedSession.isPremium() || proxiedSession.isLogged()) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVous êtes déjà authentifié !").create());
            return false;
        }

        if (!Encrypt.getSHA512(strings[0]).equals(proxiedSession.getPassword())) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cMot de passe incorrect !").create());
            return false;
        }

        ProxyServer.getInstance().getScheduler().runAsync(AuthBungee.STRAW, proxiedSession::update);
        proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§aVous êtes connecté").create());
        proxiedSession.setLastIP(proxiedPlayer.getAddress().getAddress().getHostAddress());
        proxiedSession.setLogged(true);
        proxiedPlayer.connect(ProxyServer.getInstance().getServerInfo("Lobby"));
        return true;
    }
}