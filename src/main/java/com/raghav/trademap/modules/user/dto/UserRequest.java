package com.raghav.trademap.modules.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userEmail;
    private String userProfileUrl;
    private String userName;
    private String userID;
}
