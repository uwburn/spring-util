package it.mgt.util.spring.auth;

import java.util.Set;

public interface AuthUser {
    
    public String getUsername();

    public String getPassword();
    
    public Set<AuthRole> authRoles();
    
    public Set<String> authOperations();

}
