package de.lalex.modsystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

import static de.lalex.modsystem.ModSystem.*;

public class Config {

    private static final FileConfiguration config = getInstance().getConfig();

    // DEFAULT SETTINGS
    public static @NotNull ZoneId getTimeZone() {
        try {
            String timeZone = config.getString("timezone");
            if(timeZone == null) return ZoneId.of("UTC");
            return ZoneId.of(timeZone);
        } catch (DateTimeException e) {
            getPluginLogger().error("Timezone {} is invalid! Using UTC instead.", config.get("timezone"));
            return ZoneId.of("UTC");
        }
    }

    public static @NotNull Component PREFIX = LegacyComponentSerializer.legacyAmpersand().deserialize(Objects.requireNonNull(getInstance().getConfig().getString("prefix")));

    public static @NotNull Component TEAM_CHAT_PREFIX = LegacyComponentSerializer.legacyAmpersand().deserialize(Objects.requireNonNull(getInstance().getConfig().getString("team-chat-prefix")));

    public static @NotNull String TEAM_CHAT_IDENTIFIER = Objects.requireNonNull(config.getString("team-chat-identifier"));

    public static @NotNull Component TEAM_LOG_PREFIX = LegacyComponentSerializer.legacyAmpersand().deserialize(Objects.requireNonNull(getInstance().getConfig().getString("team-log-prefix")));

    public static @NotNull Component getNoPermissionMessage() {
        String noPerm = Objects.requireNonNull(getInstance().getConfig().getString("messages.no-permission"));
        noPerm = noPerm.replace("%prefix%", LegacyComponentSerializer.legacySection().serialize(PREFIX));
        return LegacyComponentSerializer.legacyAmpersand().deserialize(noPerm);
    }

    public static boolean TEAM_CHAT_ENABLED = config.get("team-chat") == null || config.getBoolean("team-chat");

    public static boolean TEAM_LOG_ENABLED = config.get("team-log") == null || config.getBoolean("team-log");

    // PERMISSIONS
    public static String TEAM_CHAT_PERMISSION = Objects.requireNonNull(config.getString("permissions.team-chat"));
    public static String TEAM_LOG_PERMISSION = Objects.requireNonNull(config.getString("permissions.team-log"));
    public static String CLEAR_CHAT_PERMISSION = Objects.requireNonNull(config.getString("permissions.cc"));
    public static String KICK_PERMISSION = Objects.requireNonNull(config.getString("permissions.kick"));

    // MESSAGES
    public static @NotNull Component getKickMessage(@NotNull Player kickedUser, @NotNull String reason) {
        String rawMessage = Objects.requireNonNull(config.getString("broadcasts.kick"));
        rawMessage = rawMessage
                .replace("%prefix%", LegacyComponentSerializer.legacySection().serialize(PREFIX))
                .replace("%user%", kickedUser.getName())
                .replace("%reason%", reason);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }

    // BROADCASTS
    public static @NotNull Component getKickBroadcast(@NotNull Player kickedUser, @NotNull Player operator, @NotNull String reason) {
        String rawMessage = Objects.requireNonNull(config.getString("broadcasts.team-log.kick"));
        rawMessage = rawMessage
                .replace("%prefix%", LegacyComponentSerializer.legacyAmpersand().serialize(TEAM_CHAT_PREFIX))
                .replace("%user%", kickedUser.getName())
                .replace("%operator%", operator.getName())
                .replace("%reason%", reason);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }

    public static @NotNull Component getCCBroadcast(@NotNull Player operator) {
        String rawMessage = Objects.requireNonNull(config.getString("broadcasts.team-log.kick"));
        rawMessage = rawMessage
                .replace("%prefix%", LegacyComponentSerializer.legacyAmpersand().serialize(PREFIX))
                .replace("%operator%", operator.getName());
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }

    // CONNECT MESSAGES
    public static @NotNull Component getKickConnectMessage(@NotNull Player operator, @NotNull String reason) {
        String rawMessage = Objects.requireNonNull(config.getString("connect-messages.kick"));
        rawMessage = rawMessage
                .replace("%user%", operator.getName())
                .replace("%reason%", reason);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }

    // ERROR-MESSAGES
    public static @NotNull Component getInsufficientArgsMessage(int args) {
        String rawMessage = Objects.requireNonNull(config.getString("error-messages.insufficient-args"));
        rawMessage = rawMessage
                .replace("%prefix%", LegacyComponentSerializer.legacyAmpersand().serialize(PREFIX))
                .replace("%args%", String.valueOf(args));
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }

    public static @NotNull Component getPlayerNotFoundMessage(String playerName) {
        String rawMessage = Objects.requireNonNull(config.getString("error-messages.player-not-found"));
        rawMessage = rawMessage
                .replace("%prefix%", LegacyComponentSerializer.legacyAmpersand().serialize(PREFIX))
                .replace("%playername%", String.valueOf(playerName));
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }



}
