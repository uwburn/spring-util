package it.mgt.util.spring.auth;

import java.util.Set;

public interface AuthRole {
    
    String getName();
    
    Set<String> authOperations();
    
}
