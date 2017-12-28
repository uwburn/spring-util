package it.mgt.util.spring.auth;

import java.security.Principal;

public class PrincipalWrapper implements Principal {
    
    private final AuthUser authUser;

    public PrincipalWrapper(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public String getName() {
        return authUser == null ? null : authUser.getUsername();
    }
    
}
