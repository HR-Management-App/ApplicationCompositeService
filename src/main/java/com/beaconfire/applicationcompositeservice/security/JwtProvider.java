package com.beaconfire.applicationcompositeservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("$security.jwt.token.key")
    private String key;


    public Optional<AuthUserDetail> resolveToken(HttpServletRequest request){

        System.out.println("HERE JwtProvider resolveToken");

        String prefixedToken = request.getHeader("Authorization"); // extract token value by key "Authorization"


        try {
            String token = prefixedToken.substring(7); // remove the prefix "Bearer "

            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody(); // decode

            String username = (String) claims.getSubject();
            int user_id = (int) claims.get("user_id");
            String email = (String) claims.get("email");
            List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");

            System.out.println(permissions);

            // convert the permission list to a list of GrantedAuthority
            List<GrantedAuthority> authorities = permissions.stream()
                    .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                    .collect(Collectors.toList());

            //return a userDetail object with the permissions the user has
            return Optional.of(AuthUserDetail.builder()
                    .user_id(user_id)
                    .email(email)
                    .username(username)
                    .authorities(authorities)
                    .build());
        } catch (Exception e) {

            return Optional.empty();
        }


    }
}
