package de.lalex.modsystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity for the ban register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BanEntity {

    @NotNull
    private UUID playerBanned;
    @NotNull
    private ZonedDateTime timeBanned;
    @NotNull
    private String banReason;
    @NotNull
    private ZonedDateTime banEnd;

}
