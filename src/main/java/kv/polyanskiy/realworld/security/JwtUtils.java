package kv.polyanskiy.realworld.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  @Value("${spring.security.secret-key}")
  private String jwtSignKey;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private boolean isTokenExpired(String token) {
    Date expirationDate = extractClaim(token, Claims::getExpiration);
    return expirationDate.before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(jwtSignKey).parseClaimsJws(token).getBody();
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails);
  }

  private String createToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder().setClaims(claims)
        .setSubject(userDetails.getUsername())
        .claim("authorities", userDetails.getAuthorities())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
        .signWith(SignatureAlgorithm.HS256, jwtSignKey).compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public String getTokenFromAuthorizationHeader(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Token")) {
      return "";
    }
    return authHeader.substring(6);
  }

  public String getTokenSubject(String token) {
    if (token == null || token.isEmpty()) {
      return "";
    }

    return extractUsername(getTokenFromAuthorizationHeader(token));
  }
}
