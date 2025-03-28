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
 * Entity for the mute register
 * @author LaLeX1491
 * @version 1.0
 * @since 2025-03-28
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MuteEntity implements ModSystemEntity {

    @NotNull
    private UUID playerMuted;
    @NotNull
    private UUID mutedBy;
    @NotNull
    private ZonedDateTime timeMuted;
    @NotNull
    private String reason;
    @NotNull
    private ZonedDateTime muteEnd;

}
