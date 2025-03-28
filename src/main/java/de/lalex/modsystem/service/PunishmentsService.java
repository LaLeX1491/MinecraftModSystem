package de.lalex.modsystem.service;

import de.lalex.modsystem.ModSystem;
import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.models.PunishmentEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.lalex.modsystem.ModSystem.*;

public class PunishmentsService {

    private static final String PUNISHMENTS_PATH = "punishments";
    private final DataStorage dataStorage = getDataStorage();

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

    public void addPunishment(@NotNull PunishmentEntity punishment, @NotNull Player p) {
        List<PunishmentEntity> punishments = getPunishments(p);
        punishments.add(punishment);
        dataStorage.set(PUNISHMENTS_PATH + p.getUniqueId(), punishments);
    }

    public boolean removePunishment(@NotNull PunishmentEntity punishment, @NotNull Player p) {
        List<PunishmentEntity> punishments = getPunishments(p);
        if(!punishments.contains(punishment)) return false;
        punishments.remove(punishment);
        dataStorage.set(PUNISHMENTS_PATH + p.getUniqueId(), punishments);
        return true;
    }

}
