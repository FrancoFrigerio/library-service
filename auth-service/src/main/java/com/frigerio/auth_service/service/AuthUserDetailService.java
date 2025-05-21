package com.frigerio.auth_service.service;

import com.frigerio.auth_service.model.User;
import com.frigerio.auth_service.repository.UserRepositoryMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUserDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepositoryMock userRepositoryMock = new UserRepositoryMock();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to get user info by [email]: {}", username);
        User myUser = userRepositoryMock.findUserByEmail(username);
        List<GrantedAuthority> authorityList = myUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(myUser.getEmail(),myUser.getPassword(),authorityList);
    }
}
