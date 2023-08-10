package com.skillupnow.demo.security;

/**
 * This class contains constants related to security configuration, such as JWT token settings.
 * These constants are used throughout the application to maintain consistency and readability.
 */
public class SecurityConstants {

	public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 18_000_00; // 30 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/signup";
    public static final String LOG_IN_URL = "/login";
}
