package de.lalex.modsystem.service;

import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.models.PunishmentEntity;
import de.lalex.modsystem.models.WarnEntity;
import de.lalex.modsystem.util.PunishmentType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.lalex.modsystem.ModSystem.*;

/**
 * Service to manage the warns register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
public class WarnService {

    private static final String WARNS_PATH = ".warns";
    private final DataStorage dataStorage = getDataStorage();
    private final PunishmentsService punishmentsService = new PunishmentsService();

    /**
     * Method to get all warns of a player
     * @param p Player to get from
     * @return List(WarnEntity) list with the punishments -> if empty = empty :D if null -> internal server error
     */
    public List<WarnEntity> getWarns(@NotNull Player p) {
        Object objectList = dataStorage.get(p.getUniqueId() + WARNS_PATH);
        if(objectList instanceof List<?> rawList) {
            List<WarnEntity> punishmentEntities = new ArrayList<>();

            for(Object obj : rawList) {
                if(obj instanceof WarnEntity) {
                    punishmentEntities.add((WarnEntity) obj);
                } else getPluginLogger().error("Internal server error: List<?> !instanceof List<WarnEntity> in the storage");
            }
            return punishmentEntities;
        } else getPluginLogger().error("Internal server error: object !instanceof list in the storage");
        return null;
    }

    /**
     * Method to add a warn to the player
     * @param warn WarnEntity to add
     * @param p Player where to add the punishment
     */
    public void addWarn(@NotNull WarnEntity warn, @NotNull Player p) {
        List<WarnEntity> warns = getWarns(p);
        warns.add(warn);
        punishmentsService.addPunishment(PunishmentEntity.builder()
                .punishedBy(warn.getWarnedBy())
                .playerPunished(warn.getPlayerWarned())
                .punishmentReason(warn.getReason())
                .timePunished(warn.getTimeWarned())
                .punishmentType(PunishmentType.WARN)
                .build(), p);
        dataStorage.set(p.getUniqueId() + WARNS_PATH, warns);
    }

    /**
     * Method to remove a warn from the player
     * @param warn PunishmentEntity to remove
     * @param p Player where to remove
     * @return false if the player doesn't contain the warn true if it was removed
     */
    public boolean removeWarn(@NotNull WarnEntity warn, @NotNull Player p) {
        List<WarnEntity> punishments = getWarns(p);
        if(!punishments.contains(warn)) return false;
        punishments.remove(warn);
        dataStorage.set(p.getUniqueId() + WARNS_PATH, punishments);
        return true;
    }

}
