package com.raghav.trademap.modules.settings;

import com.raghav.trademap.common.TrackingDateDetails;
import com.raghav.trademap.common.TrackingDateDetailsRepo;
import com.raghav.trademap.common.types.ResourceType;
import com.raghav.trademap.exceptions.ResourceNotFoundException;
import com.raghav.trademap.modules.settings.dto.SettingsRequest;
import com.raghav.trademap.modules.settings.dto.SettingsResponse;
import com.raghav.trademap.modules.settings.dto.SettingsUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SettingsService {
    private final TrackingDateDetailsRepo trackingDateDetailsRepo;
    private final SettingsRepo settingsRepo;

    public SettingsService(TrackingDateDetailsRepo repo, SettingsRepo settingsRepo){
        this.trackingDateDetailsRepo = repo;
        this.settingsRepo = settingsRepo;
    }

    public SettingsResponse getSettings(){
        Optional<Settings> result = settingsRepo.findById(1);

        Settings settings = result.orElseThrow(() -> new ResourceNotFoundException("No settings found", ResourceType.SETTINGS));

        return SettingsResponse.mapToSettingsResponse(settings);
    }

    @Transactional
    private Settings createNewSettings(){
        Optional<TrackingDateDetails> trackingDateDetails = trackingDateDetailsRepo.findById(1);
        TrackingDateDetails trackingDate = trackingDateDetails.orElseThrow();

        Settings settings = new Settings();

        // only for one user
        settings.setId(1);
        settings.setMaxTradeLimit(1);
        settings.setWarnOnMaxTrade(true);
        settings.setStartDate(trackingDate.getStartDate());

        return settingsRepo.save(settings);
    }

    @Transactional
    public SettingsResponse saveSettings(SettingsRequest request) {
        if(settingsRepo.findById(1).isPresent())
            throw new RuntimeException("Data already exists");

        Settings settings = new Settings();

        // only for one user
        settings.setId(1);
        settings.setMaxTradeLimit(request.getMaxTradesLimit());
        settings.setWarnOnMaxTrade(request.getWarnWhenMaxLimitReached());
        settings.setStartDate(request.getTrackingDate());

        Settings saved = settingsRepo.save(settings);

        return SettingsResponse.mapToSettingsResponse(saved);
    }

    @Transactional
    public SettingsResponse updateSettings(SettingsUpdateRequest request) {
        if(settingsRepo.findById(1).isPresent())
            throw new RuntimeException("Data already exists");

        if(request.getId() == null)
            throw new RuntimeException("ID is required for updating the settings");

        Settings settings = settingsRepo.findById(request.getId()).orElseThrow();

        settings.setMaxTradeLimit(request.getMaxTradesLimit());
        settings.setWarnOnMaxTrade(request.getWarnWhenMaxLimitReached());
        settings.setStartDate(request.getTrackingDate());

        Settings saved = settingsRepo.save(settings);

        return SettingsResponse.mapToSettingsResponse(saved);
    }
}
