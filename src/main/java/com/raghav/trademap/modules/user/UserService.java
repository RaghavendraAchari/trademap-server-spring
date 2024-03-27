package com.raghav.trademap.modules.user;

import com.raghav.trademap.modules.user.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    User createUser(UserRequest request){
        if(userRepo.findByUserEmail(request.getUserEmail()).isPresent())
            return null;
        else {
            User user = User.fromUserRequest(request);

            return userRepo.save(user);
        }
    }
}
