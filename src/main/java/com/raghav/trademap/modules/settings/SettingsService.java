package com.raghav.trademap.modules.settings;

import com.raghav.trademap.common.TrackingDateDetails;
import com.raghav.trademap.common.TrackingDateDetailsRepo;
import com.raghav.trademap.common.types.ResourceType;
import com.raghav.trademap.exceptions.ResourceNotFoundException;
import com.raghav.trademap.modules.settings.dto.SettingsRequest;
import com.raghav.trademap.modules.settings.dto.SettingsUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {
    private final SettingsRepo settingsRepo;

    public SettingsService(SettingsRepo settingsRepo){
        this.settingsRepo = settingsRepo;
    }

    public Settings getSettings(String user){
        Optional<Settings> result = settingsRepo.findByUserId(user);

        return result.orElseThrow(() -> new ResourceNotFoundException("No settings found", ResourceType.SETTINGS));

    }

    @Transactional
    public Settings saveSettings(SettingsRequest request, String user) {
        if(settingsRepo.findByUserId(user).isPresent())
            throw new RuntimeException("Data already exists");

        Settings settings = new Settings();

        // only for one user
        settings.setUserId(user);
        settings.setMaxTradeLimit(request.getMaxTradesLimit());
        settings.setWarnOnMaxTrade(request.getWarnWhenMaxLimitReached());
        settings.setStartDate(request.getTrackingDate());

        return settingsRepo.save(settings);
    }

    @Transactional
    public Settings updateSettings(SettingsUpdateRequest request) {
        if(request.getId() == null)
            throw new RuntimeException("ID is required for updating the settings");

        Settings settings = settingsRepo.findById(request.getId()).orElseThrow();

        settings.setMaxTradeLimit(request.getMaxTradesLimit());
        settings.setWarnOnMaxTrade(request.getWarnWhenMaxLimitReached());
        settings.setStartDate(request.getTrackingDate());

        return settingsRepo.save(settings);
    }
}
