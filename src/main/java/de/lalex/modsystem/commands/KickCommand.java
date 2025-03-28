package de.lalex.modsystem.commands;

import de.lalex.modsystem.Config;
import de.lalex.modsystem.models.PunishmentEntity;
import de.lalex.modsystem.service.PunishmentsService;
import de.lalex.modsystem.util.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KickCommand implements CommandExecutor, TabCompleter {

    private PunishmentsService punishmentsService = new PunishmentsService();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(commandSender instanceof Player p) {
            if(p.hasPermission(Config.KICK_PERMISSION)) {
                if(args.length == 2) {
                    Player kickedPlayer = Bukkit.getPlayer(args[0]);
                    if(kickedPlayer != null) {

                        kickedPlayer.kick(Config.getKickConnectMessage(p, args[1]));
                        punishmentsService.addPunishment(PunishmentEntity.builder()
                                        .playerPunished(kickedPlayer.getUniqueId())
                                        .punishedBy(p.getUniqueId())
                                        .timePunished(ZonedDateTime.now(Config.getTimeZone()))
                                        .punishmentReason(args[1])
                                        .punishmentType(PunishmentType.KICK)
                                        .build(), kickedPlayer
                        );

                    } else p.sendMessage(Config.getPlayerNotFoundMessage(args[0]));
                } else p.sendMessage(Config.getInsufficientArgsMessage(2));
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> tabCompleter = new ArrayList<>();
        if(args.length == 1) {
            Bukkit.getOnlinePlayers().forEach(player -> tabCompleter.add(player.getName()));
        }
        return tabCompleter;
    }
}
