package com.raghav.trademap.modules.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepo extends JpaRepository<Settings, Integer> {
    Optional<Settings> findByUserId(String userId);
}
