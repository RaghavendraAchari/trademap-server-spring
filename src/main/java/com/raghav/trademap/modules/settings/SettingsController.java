package com.raghav.trademap.modules.settings;

import com.raghav.trademap.modules.settings.dto.SettingsRequest;
import com.raghav.trademap.modules.settings.dto.SettingsResponse;
import com.raghav.trademap.modules.settings.dto.SettingsUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping()
    public ResponseEntity<SettingsResponse> saveSettings(@RequestBody SettingsRequest request){
        SettingsResponse settingsResponse = SettingsResponse.mapToSettingsResponse(settingsService.saveSettings(request));

        return ResponseEntity.ok(settingsResponse);
    }

    @PutMapping
    public ResponseEntity<SettingsResponse> updateSettings(@RequestBody SettingsUpdateRequest request){
        SettingsResponse settingsResponse = SettingsResponse.mapToSettingsResponse(settingsService.updateSettings(request));

        return ResponseEntity.ok(settingsResponse);
    }

    @GetMapping
    public ResponseEntity<SettingsResponse> getAllSettings(){
        Settings settings = settingsService.getSettings();

        SettingsResponse settingsResponse = SettingsResponse.mapToSettingsResponse(settings);

        return ResponseEntity.ok(settingsResponse);
    }
}
