package bzh.strawberrycorps.auth.command;

import bzh.strawberry.api.command.AbstractBCommand;
import bzh.strawberrycorps.auth.StrawBungee;
import net.md_5.bungee.api.CommandSender;

/*
 * This file LoginCommand is part of a project StrawAuth.
 * It was created on 05/04/2021 16:19 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class LoginCommand extends AbstractBCommand {

    public LoginCommand() {
        super(StrawBungee.STRAW, "login", "auth.login");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String[] strings) {
        return false;
    }
}