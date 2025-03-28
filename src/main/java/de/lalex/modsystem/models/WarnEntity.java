package de.lalex.modsystem.models;

import de.lalex.modsystem.interfaces.ModSystemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity for the warn register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WarnEntity implements ModSystemEntity {

    @NotNull
    private UUID playerWarned;
    @NotNull
    private UUID warnedBy;
    @NotNull
    private ZonedDateTime timeWarned;
    @NotNull
    private String reason;

}
