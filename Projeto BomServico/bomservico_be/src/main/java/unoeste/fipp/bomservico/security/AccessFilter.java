package unoeste.fipp.bomservico.security;

import java.io.IOException;


import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Authorization");

        if(token!=null && JWTTokenProvider.validateToken(token)) {
            Claims claims = JWTTokenProvider.getClaimsFromToken(token);
            req.setAttribute("username", claims.getSubject());
            req.setAttribute("nivel", claims.get("claim", Integer.class));
            chain.doFilter(request, response);
        }
        else
        {
            ((HttpServletResponse)response).setStatus(401);
            response.getOutputStream().write("{\"status\":\"Não autorizado\"}".getBytes());
        }
    }
}
