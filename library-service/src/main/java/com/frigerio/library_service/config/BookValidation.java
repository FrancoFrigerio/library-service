package com.frigerio.library_service.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("bookValidation")
public class BookValidation {

    public boolean hasBookPermission(String category, Authentication authentication){
        Map<String,Object> tokenAttributes = ((JwtAuthenticationToken) authentication).getTokenAttributes();
        Map<String,Object> restrictions = (Map<String,Object>) tokenAttributes.get("restrictions");
        List<String> allowedCategories = (List<String>) restrictions.get("allowedCategories");
        return allowedCategories.stream().anyMatch(cat -> cat.equalsIgnoreCase(category));
    }

}
