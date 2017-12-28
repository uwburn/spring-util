package it.mgt.util.spring.auth;

public interface AuthSvc {

    AuthUser getAuthUser(String username, String password);

    AuthUser getAuthUser(String username);

    String hashPassword(String password);
    
    String generateToken();
    
    AuthSession getValidAuthSession(String token);

    void invalidateSession(AuthSession authSession);

    void touchSession(AuthSession session);

}
