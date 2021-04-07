package bzh.strawberrycorps.auth.command;

import bzh.strawberry.api.command.AbstractBCommand;
import bzh.strawberrycorps.auth.AuthBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
import bzh.strawberrycorps.auth.util.Encrypt;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
 * This file RegisterCommand is part of a project StrawAuth.
 * It was created on 05/04/2021 16:19 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ChangePasswordCommand extends AbstractBCommand {

    public ChangePasswordCommand() {
        super(AuthBungee.STRAW, "changepassword", "auth.changepassword");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer))
            return false;

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());
        if (proxiedSession.isPremium())return true;
        if (strings.length < 2) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §6Utilise : /changepassword <mdp> <nouveau mdp>").create());
            return false;
        }

        if (!proxiedSession.isPremium() && proxiedSession.getPassword() == null) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cVous devez être enregistré !").create());
            return false;
        }
        if (proxiedSession.isPremium() || !proxiedSession.isLogged()) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cVous n'êtes pas authentifié !").create());
            return false;
        }

        if (!Encrypt.getSHA512(strings[0]).equals(proxiedSession.getPassword())) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cMot de passe incorrect !").create());
            return false;
        }

        if (!Encrypt.getSHA512(strings[1]).equals(proxiedSession.getPassword())) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cLe mot de passe doit être différent !").create());
            return false;
        }

        if (strings[1].equals(proxiedSession.getUsername())) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cVotre mot de passe ne peut pas être votre pseudo !").create());
            return false;
        }

        if (strings[1].length() < 6) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cVotre mot de passe doit avoir plus de 6 caractères !").create());
            return false;
        }

        proxiedSession.setPassword(strings[1]);
        ProxyServer.getInstance().getScheduler().runAsync(AuthBungee.STRAW, proxiedSession::update);
        proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + " §cVous avez bien changé votre mot de passe !").create());
        return true;
    }
}