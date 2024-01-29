package org.domi.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.domi.METEOR;
import org.domi.command.CMD.CMDS;

public class Commands {
    public static void getWands(CommandSender commandSender, String... strings) {
        if(commandSender instanceof Player p) {
            CMDS.wands(p);
        }
    }

    public static void meteorCount(CommandSender commandSender, Integer i, String... strings) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CMDS.meteorInt(player, i);
        }
    }
}
