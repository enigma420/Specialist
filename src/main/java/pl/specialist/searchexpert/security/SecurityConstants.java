package pl.specialist.searchexpert.security;

public class SecurityConstants {

    static final String SIGN_UP_AND_LOGIN_URLS = "/api/auth/**";
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 3000000; // 3000seconds


}