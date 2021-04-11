package bzh.strawberrycorps.auth.command;

import bzh.strawberry.api.command.AbstractBCommand;
import bzh.strawberrycorps.auth.AuthBungee;
import bzh.strawberrycorps.auth.session.ProxiedSession;
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
public class RegisterCommand extends AbstractBCommand {

    public RegisterCommand() {
        super(AuthBungee.STRAW, "register", "auth.register");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer))
            return false;

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
        ProxiedSession proxiedSession = AuthBungee.STRAW.getProxiedSession(proxiedPlayer.getUniqueId());
        if (proxiedSession.isPremium()) return true;
        if (strings.length < 2) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§6Utilise : /register <mdp> <confirme mdp>").create());
            return false;
        }

        if (!proxiedSession.isPremium() && proxiedSession.getPassword() != null) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVous êtes déjà enregistré !").create());
            return false;
        }
        if (proxiedSession.isPremium() || proxiedSession.isLogged()) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVous êtes déjà authentifié !").create());
            return false;
        }

        if (!strings[0].equals(strings[1])) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cLes mots de passes ne correspondent pas !").create());
            return false;
        }

        if (strings[0].equals(proxiedSession.getUsername())) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVotre mot de passe ne peut pas être votre pseudo !").create());
            return false;
        }

        if (strings[0].length() < 6) {
            proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVotre mot de passe doit avoir plus de 6 caractères !").create());
            return false;
        }

        proxiedSession.setPassword(strings[0]);
        ProxyServer.getInstance().getScheduler().runAsync(AuthBungee.STRAW, proxiedSession::insert);
        proxiedPlayer.sendMessage(new ComponentBuilder(AuthBungee.STRAW.getPrefix() + "§cVous avez bien été enregistré !").create());
        proxiedSession.setLogged(true);
        proxiedPlayer.connect(ProxyServer.getInstance().getServerInfo("Lobby"));
        proxiedSession.setLastIP(proxiedPlayer.getAddress().getAddress().getHostAddress());
        return true;
    }
}