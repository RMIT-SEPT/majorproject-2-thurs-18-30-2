package com.majorproject.backend.security;

public class SecurityConstants {

    public static final String USER_LOG_IN = "/api/user/login";
    public static final String USERNAME_EXISTS = "/api/user/usernameExists/**";
    public static final String CUSTOMER_SIGN_UP = "/api/customer/register";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 86_400_000; //30 seconds
}
