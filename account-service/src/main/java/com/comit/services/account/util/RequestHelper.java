package com.comit.services.account.util;

import com.comit.services.account.model.entity.UserDetailImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RequestHelper {
    public boolean hasRole(String roleName) {
        try {
            UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(roleName));
        } catch (Exception e) {
            return false;
        }
    }
}