package com.raghav.trademap.modules.user;

import com.raghav.trademap.modules.user.dto.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userName;
    private String userEmail;
    private String userProfileUrl;

    public static User fromUserRequest(UserRequest request) {
        return User.builder()
                .userEmail(request.getUserEmail())
                .userName(request.getUserName())
                .userProfileUrl(request.getUserProfileUrl())
                .userId(request.getUserID())
                .build();
    }
}
