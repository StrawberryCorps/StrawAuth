package bzh.strawberrycorps.auth.listener;

/*
 * This file ProxiedPlayerDisconnect is part of a project StrawAuth.
 * It was created on 05/04/2021 16:21 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PostLogin implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent event) {
        // save du temps pour stats
        long startTime = System.currentTimeMillis();

        // var utiles
        final ProxiedPlayer player = event.getPlayer();
        ProxiedSession session = StrawBungee.STRAW.getProxiedSession(player.getUniqueId());

        // save du pseudo du joueur : changement de pseudo d'un premium :smirk:
        session.setUsername(player.getName());
        session.setLastIP(player.getSocketAddress().toString());

        if(!session.isPremium()){

            //register
            if(session.getPassword() == null){
                player.sendMessage(new TextComponent("Bienvenue sur notre serveur"));
                player.sendMessage(new TextComponent(""));
                player.sendMessage(new TextComponent("Merci de vous enregistrer à l'aide de la commande §c/register §e<MdP>§r."));
            }
            //login
            else{
                player.sendMessage(new TextComponent("Merci de vous connecter à l'aide de la commande §c/login §e<MdP>§r."));
            }

        } else {
            player.sendMessage(new TextComponent(StrawBungee.STRAW.getPrefix() + "§aConnecté sous compte premium"));
            session.setLastIP(player.getAddress().getAddress().getHostAddress());
            session.setLogged(true);
            player.connect(ProxyServer.getInstance().getServerInfo("Lobby"));
        }

//        StrawSpigot.STRAW.getLogger().info("[StrawAuth] PostLoginEvent : Compte de " + player.getName() + " chargé en " + (System.currentTimeMillis() - startTime) + " ms");
    }
}