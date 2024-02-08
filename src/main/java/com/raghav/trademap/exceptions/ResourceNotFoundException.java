package com.raghav.trademap.exceptions;

import com.raghav.trademap.common.types.ResourceType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private ResourceType resourceType;

    public ResourceNotFoundException(String message, ResourceType resourceType){
        super(message);
        this.resourceType = resourceType;
    }
}
