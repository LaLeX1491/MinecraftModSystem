package de.lalex.modsystem.models;

import de.lalex.modsystem.interfaces.ModSystemEntity;
import de.lalex.modsystem.util.PunishmentType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity for the punishment register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PunishmentEntity implements ModSystemEntity {

    @NotNull
    private UUID playerPunished;
    @NotNull
    private UUID punishedBy;
    @NotNull
    private ZonedDateTime timePunished;
    @NotNull
    private String punishmentReason;
    @NotNull
    private PunishmentType punishmentType;

}
