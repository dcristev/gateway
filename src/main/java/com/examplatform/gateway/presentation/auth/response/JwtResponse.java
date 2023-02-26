package com.examplatform.gateway.presentation.auth.response;

import com.examplatform.gateway.persistence.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtResponse {

    private final String jwt;
    private final Long id;
    private final String fullName;
    private final String username;
    private final Set<Role> userRole;
}
