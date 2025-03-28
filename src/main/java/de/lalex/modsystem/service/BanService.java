package de.lalex.modsystem.service;

import de.lalex.modsystem.dataStorage.DataStorage;
import de.lalex.modsystem.models.BanEntity;
import de.lalex.modsystem.models.PunishmentEntity;
import de.lalex.modsystem.util.PunishmentType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.Optional;

import static de.lalex.modsystem.ModSystem.*;

/**
 * Service to manage the bans
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
public class BanService {

    private static final String BANS_PATH = ".ban";

    private final PunishmentsService punishmentsService = new PunishmentsService();
    private final DataStorage dataStorage = getDataStorage();

    /**
     * Method to register a ban to a specific entity
     * @param ban BanEntity of the ban itself
     * @param p Player who is getting banned
     * @return Optional(BanEntity) -> if the player is already banned it returns the Entity if not it returns empty Optional
     */
    public Optional<BanEntity> addBan(@NotNull BanEntity ban, @NotNull Player p) {
        if(isPlayerBanned(p)) return Optional.ofNullable(getPlayerBan(p));
        dataStorage.set(p.getUniqueId() + BANS_PATH, ban);
        punishmentsService.addPunishment(PunishmentEntity.builder()
                .playerPunished(p.getUniqueId())
                .timePunished(ZonedDateTime.now())
                .punishmentReason(ban.getBanReason())
                .punishmentType(PunishmentType.BAN)
                .punishedBy(ban.getBannedBy())
                .build(), p);
        return Optional.empty();
    }

    /**
     * Method to remove a ban from the player
     * @param p Player where the ban is going to be removed
     * @return false -> if the player isn't banned | true -> if the ban was removed
     */
    public boolean removeBan(@NotNull Player p) {
        if(!isPlayerBanned(p)) return false;
        dataStorage.set(p.getUniqueId() + BANS_PATH, null);
        return true;
    }

    /**
     * Method to override (refresh a ban)
     * @param ban BanEntity of the new ban
     * @param p Player to ban
     */
    public void overrideBan(@NotNull BanEntity ban, @NotNull Player p) {
        dataStorage.set(p.getUniqueId() + BANS_PATH, ban);
        punishmentsService.addPunishment(PunishmentEntity.builder()
                .playerPunished(p.getUniqueId())
                .timePunished(ZonedDateTime.now())
                .punishmentReason(ban.getBanReason())
                .punishmentType(PunishmentType.MUTE)
                .punishedBy(ban.getBannedBy())
                .build(), p);
    }

    /**
     * Method to get the BanEntity of a bannedPlayer
     * @param p Player to get the ban from
     * @return BanEntity of the player or null if he isn't banned
     */
    public BanEntity getPlayerBan(@NotNull Player p) {
        final Object rawObject = dataStorage.get(p.getUniqueId() + BANS_PATH);
        if(rawObject == null) return null;
        if(rawObject instanceof BanEntity) return (BanEntity) rawObject;
        getPluginLogger().error("Internal server error: playerBanRawObject !instanceof BanEntity");
        return null;
    }

    /**
     * Boolean to check if the player is banned right now
     * @param p Player to check
     * @return true = banned | false = not banned
     */
    public boolean isPlayerBanned(@NotNull Player p) {
        final Object rawBanObject = dataStorage.get(p.getUniqueId() + BANS_PATH);
        return rawBanObject != null;
    }

}
