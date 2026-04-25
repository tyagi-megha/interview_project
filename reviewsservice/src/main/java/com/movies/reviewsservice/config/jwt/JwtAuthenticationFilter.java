package com.movies.reviewsservice.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.reviewsservice.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtils;
//    @Autowired
//    private UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            System.out.println("jwt" + jwt);
            if (StringUtils.hasText(jwt) ) {
                String username = jwtUtils.extractUsername(jwt);
                System.out.println("username : " + username);
                //no longer needed as user service already set userid in jwt claims
//                UsernameRequest usernameRequest = new UsernameRequest();
//                usernameRequest.setUsername(username);
//                UserDto user = userServiceClient.getUserByUsername(usernameRequest);
//                System.out.println("userid outer: " + user.getId());
                if(jwtUtils.validateToken(jwt, username)){
                  //  var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    List<String> roles = jwtUtils.extractRoles(jwt);
                    Long userId = jwtUtils.extractUserId(jwt);
                    var authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                    System.out.println("userid" + userId);
                    var auth = new UsernamePasswordAuthenticationToken(userId, // 👈 store userId as principal
                            jwt,
                            authorities
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }
        } catch (Exception e) {
            sendErrorResponse(response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    public void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse("Invalid access token, please login and try again");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
