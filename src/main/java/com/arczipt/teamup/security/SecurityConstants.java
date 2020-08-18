package com.arczipt.teamup.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class SecurityConstants {
    public static final String SECRET = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);
    public static final long EXPIRE_TIME = 60*60*24*7*1000; //7 days in ms
}
