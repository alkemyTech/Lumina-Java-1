package com.alkemy.wallet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

//en esta clase Autentico el Usuario
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


   //metood para intentar una autenticacion
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        AuthCredential authCredential= new AuthCredential();

        try {
            authCredential=new ObjectMapper().readValue(request.getReader(), AuthCredential.class);

        } catch (IOException e){
        }
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredential.getEmail(),
                authCredential.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    //metodo para efectuar la authenticacion
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImplements userDetails= (UserDetailsImplements) authResult.getPrincipal();
        String token= TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());
        response.addHeader("Authorization", "Barer"+token);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
