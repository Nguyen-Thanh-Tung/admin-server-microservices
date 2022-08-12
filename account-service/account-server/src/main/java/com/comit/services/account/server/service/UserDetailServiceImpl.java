package com.comit.services.account.server.service;

import com.comit.services.account.server.constant.Const;
import com.comit.services.account.server.model.User;
import com.comit.services.account.server.model.UserDetailImpl;
import com.comit.services.account.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (userRepository.existsByUsername(s)) {
            User user = userRepository.findByUsernameAndStatusNotIn(s, List.of(Const.DELETED));
            return UserDetailImpl.build(user);
        } else {
            throw new UsernameNotFoundException("Unknown user: " + s);
        }
    }
}
