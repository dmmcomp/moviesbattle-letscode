package br.com.letscode.moviesbattle.Utils;

import br.com.letscode.moviesbattle.security.JWTAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import static br.com.letscode.moviesbattle.security.JWTValidatorFilter.PREFIX;

public class UserSecutiryUtils {
    public static String getUsernameFromToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.PRIVATE_KEY)).build().verify(token.replace(PREFIX,"")).getSubject();
        if(user == null) return null;
        return user;
    }
}
