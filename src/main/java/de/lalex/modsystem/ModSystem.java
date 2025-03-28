package de.lalex.modsystem;

import de.lalex.modsystem.commands.ClearChatCommand;
import de.lalex.modsystem.commands.KickCommand;
import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.teammessages.TeamChatListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Slf4j
public final class ModSystem extends JavaPlugin implements Serializable  {

    @Getter
    private static ModSystem instance;

    @Getter
    private static final Logger pluginLogger = LoggerFactory.getLogger("ModSystem");

    @Getter
    public static DataStorage dataStorage;

    @Override
    public void onEnable() {
        pluginLogger.info("starting plugin...");

        instance = this;

        dataStorage = new DataStorage();
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new TeamChatListener(), this);

        getCommand("cc").setExecutor(new ClearChatCommand());
        getCommand("kick").setExecutor(new KickCommand());


        pluginLogger.info("startup complete!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
