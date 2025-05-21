package com.frigerio.auth_service.model.dto;

import java.util.Set;

public class UserDetailsDTO {
    private Set<String> allowedCategories;
    private Set<String> allowedAuthors;
    private String detail;

    public UserDetailsDTO() {
    }

    public Set<String> getAllowedCategories() {
        return allowedCategories;
    }

    public void setAllowedCategories(Set<String> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }

    public Set<String> getAllowedAuthors() {
        return allowedAuthors;
    }

    public void setAllowedAuthors(Set<String> allowedAuthors) {
        this.allowedAuthors = allowedAuthors;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
