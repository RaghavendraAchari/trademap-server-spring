package com.raghav.trademap.modules.user;


import com.raghav.trademap.modules.settings.Settings;
import com.raghav.trademap.modules.settings.SettingsRepo;
import com.raghav.trademap.modules.user.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SettingsRepo settingsRepo;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(JwtAuthenticationToken authenticationToken){
        if(authenticationToken.isAuthenticated()){
            UserRequest request = new UserRequest();

            request.setUserName( (String) authenticationToken.getTokenAttributes().get("name"));
            request.setUserEmail( (String) authenticationToken.getTokenAttributes().get("email"));
            request.setUserProfileUrl( (String) authenticationToken.getTokenAttributes().get("picture"));
            request.setUserID( (String) authenticationToken.getTokenAttributes().get("sub"));

            User user = userService.createUser(request);

            if(user == null)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            //set default settings
            Settings settings = Settings.builder()
                    .startDate(LocalDate.now())
                    .maxTradeLimit(2)
                    .warnOnMaxTrade(true)
                    .userId((String) authenticationToken.getTokenAttributes().get("sub"))
                    .build();

            settingsRepo.save(settings);

            return ResponseEntity.ok(user);
        }

        return ResponseEntity.ok(null);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(){
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
