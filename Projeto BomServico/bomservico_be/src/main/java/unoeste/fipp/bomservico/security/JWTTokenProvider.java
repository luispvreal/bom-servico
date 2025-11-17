package unoeste.fipp.bomservico.security;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTTokenProvider {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "MINHACHAVESECRETA_MINHACHAVESECRETA".getBytes(StandardCharsets.UTF_8)
    );

    static public String getToken(String username, int level){
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuer("localhost:8080")
                .claim("claim", level)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SECRET_KEY)
                .compact();
        return token;
    }

    static public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    static public Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().
                    parseClaimsJws(token).getBody();
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        return claims;
    }
}
