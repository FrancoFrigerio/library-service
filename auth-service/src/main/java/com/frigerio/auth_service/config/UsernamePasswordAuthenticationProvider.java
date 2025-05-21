package com.frigerio.auth_service.config;

import com.frigerio.auth_service.model.dto.UserDetailsDTO;
import com.frigerio.auth_service.repository.UserRepositoryMock;
import com.frigerio.auth_service.service.AuthUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepositoryMock userRepositoryMock = new UserRepositoryMock();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = authUserDetailService.loadUserByUsername(username);

        if(passwordEncoder.matches(password,userDetails.getPassword())){
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
            UserDetailsDTO  userDetailsDTO = new UserDetailsDTO();
            userDetailsDTO.setAllowedCategories(userRepositoryMock.allowedCategoriesByEmail(username));
            userDetailsDTO.setAllowedAuthors(userRepositoryMock.allowedAuthorsByEmail(username));
            user.setDetails( userDetailsDTO);
            return user;
        }else{
            logger.error("Password or username dont match");
            throw  new BadCredentialsException("Invalid data");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
