package com.comit.services.account.server.jwt;

import com.comit.services.account.server.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(0)
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    public static String username = null;
    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwt(request);

            if (jwt != null && !jwt.isEmpty() && tokenProvider.validateJwtToken(jwt)) {
                username = tokenProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.printf("Can NOT set user authentication -> Message: %s%n", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        return request.getHeader("token");
    }
}
