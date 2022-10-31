package com.nds.ssagdaglo.config.jwt;

public interface JwtProperties {
    String SECRET = "ssagdaglo_token"; // 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000 * 10; // 10분(60초 * 10)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
