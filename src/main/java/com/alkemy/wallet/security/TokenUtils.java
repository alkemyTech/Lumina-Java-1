package com.alkemy.wallet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.*;

public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRET="pepe";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS= 2_592_000L;

    //Con este metodo creo un TOKEN que sera enviado al Cliente
    public static String createToken(String nombre, String email){
        long expirationTime=ACCESS_TOKEN_VALIDITY_SECONDS*1_000;
        Date expirationDate =new Date(System.currentTimeMillis()+expirationTime);

        Map<String, Object> extra =new HashMap<>();
        extra.put("nombre", nombre);
                return Jwts.builder().setSubject(email).setExpiration(expirationDate)
                        .addClaims(extra).signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())).compact()          .getBytes()).compact();
    }


    //Con este metodo el usuario puede pasar x el proceso de autenticacion y puede acceder a un endpoint en expecifico.
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
       try {
           Claims claims = Jwts.parserBuilder()
                   .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();

           String email = claims.getSubject();

           return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
       }catch (JwtException e){
           return null;
       }
    }


}
