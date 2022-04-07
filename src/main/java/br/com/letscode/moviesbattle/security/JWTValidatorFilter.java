package br.com.letscode.moviesbattle.security;

import br.com.letscode.moviesbattle.utils.UserSecutiryUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JWTValidatorFilter extends BasicAuthenticationFilter {


    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String PREFIX = "Bearer ";

    public JWTValidatorFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String attribute = request.getHeader(HEADER_ATTRIBUTE);
        if(attribute == null || !attribute.startsWith(PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(attribute);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        String user = UserSecutiryUtils.getUsernameFromToken(token);
        if (user == null) return null;

        return new UsernamePasswordAuthenticationToken(user,null, new ArrayList<>());
    }


}
