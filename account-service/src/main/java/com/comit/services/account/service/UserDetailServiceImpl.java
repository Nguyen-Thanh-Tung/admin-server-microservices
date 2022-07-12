package com.comit.services.account.service;

import com.comit.services.account.constant.Const;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.model.entity.UserDetailImpl;
import com.comit.services.account.repository.UserRepository;
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
