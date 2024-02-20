package com.raghav.trademap.modules.settings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettingsUpdateRequest extends SettingsRequest{

    @NotNull
    private Integer id;

}
