package it.mgt.util.spring.auth;

public interface AuthSession {

    AuthUser authUser();

    String getToken();

    int getExpirySeconds();

}
