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
        UsernamePasswordAuthenticationToken usernamePAt = new UsernamePasswordAuthenticationToken(
                authCredential.getEmail(),
                authCredential.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAt);
    }

    //metodo para efectuar la authenticacion
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //creo un UserDetailsImpl lo parceo con authResult
        UserDetailsImplements userDetails= (UserDetailsImplements) authResult.getPrincipal();

        //creo el token utilizando el usuario y apellido de la clase UserDetailsImplement
        String token= TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());

        //envio con un response los datos con un header "authorization" + token
        response.addHeader("Authorization", "Barer"+token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
