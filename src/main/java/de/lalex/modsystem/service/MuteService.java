package de.lalex.modsystem.service;

import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.models.MuteEntity;
import de.lalex.modsystem.models.PunishmentEntity;
import de.lalex.modsystem.util.PunishmentType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.Optional;

import static de.lalex.modsystem.ModSystem.*;

/**
 * Service to manage the mutes
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
public class MuteService {

    private static final String MUTES_PATH = ".mute";

    private final PunishmentsService punishmentsService = new PunishmentsService();
    private final DataStorage dataStorage = getDataStorage();

    /**
     * Method to register a mute to a specific entity
     * @param mute MuteEntity of the mute itself
     * @param p Player who is getting muted
     * @return Optional(BanEntity) -> if the player is already muted it returns the Entity if not it returns empty Optional
     */
    public Optional<MuteEntity> addMute(@NotNull MuteEntity mute, @NotNull Player p) {
        if(isPlayerMuted(p)) return Optional.ofNullable(getPlayerMute(p));
        dataStorage.set(p.getUniqueId() + MUTES_PATH, mute);
        punishmentsService.addPunishment(PunishmentEntity.builder()
                .playerPunished(p.getUniqueId())
                .timePunished(ZonedDateTime.now())
                .punishmentReason(mute.getReason())
                .punishmentType(PunishmentType.MUTE)
                .build(), p);
        return Optional.empty();
    }

    /**
     * Method to override (refresh a mute)
     * @param mute MuteEntity of the new mute
     * @param p Player to mute
     */
    public void overrideMute(@NotNull MuteEntity mute, @NotNull Player p) {
        dataStorage.set(p.getUniqueId() + MUTES_PATH, mute);
        punishmentsService.addPunishment(PunishmentEntity.builder()
                .playerPunished(p.getUniqueId())
                .timePunished(ZonedDateTime.now())
                .punishmentReason(mute.getReason())
                .punishmentType(PunishmentType.MUTE)
                .build(), p);
    }

    /**
     * Method to remove a mute from the player
     * @param p Player where the mute is going to be removed
     * @return false -> if the player isn't muted | true -> if the mute was removed
     */
    public boolean removeMute(@NotNull Player p) {
        if(!isPlayerMuted(p)) return false;
        dataStorage.set(p.getUniqueId() + MUTES_PATH, null);
        return true;
    }

    /**
     * Method to get the MuteEntity of a mutedPlayer
     * @param p Player to get the mute from
     * @return MuteEntity of the player or null if he isn't muted
     */
    public MuteEntity getPlayerMute(@NotNull Player p) {
        final Object rawObject = dataStorage.get(p.getUniqueId() + MUTES_PATH);
        if(rawObject == null) return null;
        if(rawObject instanceof MuteEntity) return (MuteEntity) rawObject;
        getLogger().error("Internal server error: playerMuteRawObject !instanceof MuteEntity");
        return null;
    }

    /**
     * Boolean to check if the player is muted right now
     * @param p Player to check
     * @return true = muted | false = not muted
     */
    public boolean isPlayerMuted(@NotNull Player p) {
        final Object rawMuteObject = dataStorage.get(p.getUniqueId() + MUTES_PATH);
        return rawMuteObject != null;
    }

}
