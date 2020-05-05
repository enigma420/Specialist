package pl.specialist.searchexpert.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static pl.specialist.searchexpert.security.SecurityConstants.EXPIRATION_TIME;
import static pl.specialist.searchexpert.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    public String generateTokenForSpecialist(Authentication authentication){
        Specialist specialist = (Specialist) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String userId = specialist.getSpecialistId();
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",specialist.getSpecialistId());
        claims.put("mail", specialist.getMail());


        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    public String generateTokenForCustomer(Authentication authentication){
        Customer customer = (Customer)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = customer.getCustomerId();

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",customer.getCustomerId());
        claims.put("mail", customer.getMail());


        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    String getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return (String)claims.get("id");
    }

}
