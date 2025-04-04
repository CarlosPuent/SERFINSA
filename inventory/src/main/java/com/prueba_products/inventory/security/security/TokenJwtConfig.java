package com.prueba_products.inventory.security.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class TokenJwtConfig {
    public final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String HEADER_AUTHORIZATION = "Authorization";
}
