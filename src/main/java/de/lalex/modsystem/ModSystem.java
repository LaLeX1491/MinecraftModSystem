package de.lalex.modsystem;

import de.lalex.modsystem.commands.ClearChatCommand;
import de.lalex.modsystem.dataStorage.DataStorage;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

public final class ModSystem extends JavaPlugin implements Serializable  {

    @Getter
    private static ModSystem instance;

    @Getter
    private static final Logger logger = LoggerFactory.getLogger("ModSystem");

    @Getter
    public static DataStorage dataStorage;

    @Override
    public void onEnable() {
        logger.info("starting plugin...");

        instance = this;

        dataStorage = new DataStorage();
        this.saveDefaultConfig();


        getCommand("cc").setExecutor(new ClearChatCommand());


        logger.info("startup complete!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static @NotNull Component getPrefix() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(Objects.requireNonNull(getInstance().getConfig().getString("prefix")));
    }

    public static @NotNull Component getNoPermissionMessage() {
        String noPerm = Objects.requireNonNull(getInstance().getConfig().getString("messages.no-permission"));
        noPerm = noPerm.replace("%prefix%", LegacyComponentSerializer.legacySection().serialize(getPrefix()));
        return LegacyComponentSerializer.legacyAmpersand().deserialize(noPerm);
    }
}
