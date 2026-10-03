package io.github.vlouboos.staffplugin.commands;

import io.github.vlouboos.staffplugin.StaffPlugin;
import io.github.vlouboos.staffplugin.inventories.BanInventory;
import io.github.vlouboos.staffplugin.service.BanService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanCommand extends Command {
    public BanCommand() {
        super("ban");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission("sanctionmanager.ban")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (sender instanceof Player player) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /ban <player>");
            } else {
                UUID uuid = StaffPlugin.sanctionManager.getPlayerService().findUuidByName(args[0]);
                if (uuid == null) {
                    sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' not found.");
                } else {
                    BanInventory.buildInventory(player, uuid, StaffPlugin.sanctionManager.getPlayerService().findNameByUuid(uuid));
                }
            }
        } else {
            UUID uuid = StaffPlugin.sanctionManager.getPlayerService().findUuidByName(args[0]);
            assert uuid != null;
            BanService.banCheating(uuid, null);
        }
        return true;
    }

}
