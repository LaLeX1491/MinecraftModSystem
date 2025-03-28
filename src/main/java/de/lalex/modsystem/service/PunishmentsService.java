package de.lalex.modsystem.service;

import de.lalex.modsystem.ModSystem;
import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.models.PunishmentEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.lalex.modsystem.ModSystem.*;

/**
 * Service to manage the punishment register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
public class PunishmentsService {

    private static final String PUNISHMENTS_PATH = "punishments.";
    private final DataStorage dataStorage = getDataStorage();

    /**
     * Method to get all punishments of a player
     * @param p Player to get from
     * @return List(PunishmentEntity) list with the punishments -> if empty = empty :D if null -> internal server error
     */
    public List<PunishmentEntity> getPunishments(@NotNull Player p) {
        Object objectList = dataStorage.get(PUNISHMENTS_PATH + p.getUniqueId());
        if(objectList instanceof List<?> rawList) {
            List<PunishmentEntity> punishmentEntities = new ArrayList<>();

            for(Object obj : rawList) {
                if(obj instanceof PunishmentEntity) {
                    punishmentEntities.add((PunishmentEntity) obj);
                } else getLogger().error("Internal server error: List<?> !instanceof List<PunishmentEntity> in the storage");
            }
            return punishmentEntities;
        } else getLogger().error("Internal server error: object !instanceof list in the storage");
        return null;
    }

    /**
     * Method to add a punishment to the player
     * @param punishment PunishmentEntity to add
     * @param p Player where to add the punishment
     */
    public void addPunishment(@NotNull PunishmentEntity punishment, @NotNull Player p) {
        List<PunishmentEntity> punishments = getPunishments(p);
        punishments.add(punishment);
        dataStorage.set(PUNISHMENTS_PATH + p.getUniqueId(), punishments);
    }

    /**
     * Method to remove a punishment from the player
     * @param punishment PunishmentEntity to remove
     * @param p Player where to remove
     * @return false if the player doesn't contain the punishment true if it was removed
     */
    public boolean removePunishment(@NotNull PunishmentEntity punishment, @NotNull Player p) {
        List<PunishmentEntity> punishments = getPunishments(p);
        if(!punishments.contains(punishment)) return false;
        punishments.remove(punishment);
        dataStorage.set(PUNISHMENTS_PATH + p.getUniqueId(), punishments);
        return true;
    }

}
