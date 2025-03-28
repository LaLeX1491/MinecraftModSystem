package de.lalex.modsystem.commands;

import de.lalex.modsystem.Config;
import de.lalex.modsystem.ModSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClearChatCommand implements CommandExecutor {

    private final String permission = ModSystem.getInstance().getConfig().getString("permissions.clear-chat") != null ? ModSystem.getInstance().getConfig().getString("permissions.clear-chat") : "modsystem.cc";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player sender) {
            if(sender.hasPermission(permission)) {

                clearChat(sender);

                return true;
            } else {
                sender.sendMessage(Config.getNoPermissionMessage());
            }
        }
        return false;
    }

    private void clearChat(@NotNull Player p) {
        StringBuilder clearMessage = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            clearMessage.append("\n");
        }

        Bukkit.getServer().broadcast(Component.text(String.valueOf(clearMessage)));
        Bukkit.getServer().broadcast(Config.getCCBroadcast(p));
    }
}
