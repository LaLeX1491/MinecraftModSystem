package de.lalex.modsystem.teammessages;

import de.lalex.modsystem.Config;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class TeamChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(@NotNull AsyncChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        if(message.startsWith(Config.TEAM_CHAT_IDENTIFIER)) {
            if(event.getPlayer().hasPermission(Config.TEAM_CHAT_PERMISSION)) {
                message = message.substring(Config.TEAM_CHAT_IDENTIFIER.length()).trim();
                Component chatComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
                event.setCancelled(true);

                Bukkit.getOnlinePlayers().forEach(player -> {
                    if(player.hasPermission(Config.TEAM_CHAT_PERMISSION)) {
                        player.sendMessage(Config.TEAM_CHAT_PREFIX.append(chatComponent));
                    }
                });
            }
        }
    }

}
