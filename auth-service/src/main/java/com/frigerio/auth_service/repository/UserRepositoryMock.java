package com.frigerio.auth_service.repository;

import com.frigerio.auth_service.model.Role;
import com.frigerio.auth_service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserRepositoryMock {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    List<User> users = Arrays.asList(
            new User(1L,"Pablo","pablo@hotmail.com",
    "{bcrypt}$2a$10$cCHvTL0ohdJ.Lw/18Z8xLuCLVXdy.DmDtMioA5sxg9Dvb9v21qJ1C",Set.of(new Role(1,"USER"))),
            new User(2L,"Gustavo","gustavo@hotmail.com",
    "{bcrypt}$2a$10$cCHvTL0ohdJ.Lw/18Z8xLuCLVXdy.DmDtMioA5sxg9Dvb9v21qJ1C", Set.of(new Role(2,"USER"))),
    new User(2L,"Mauricio","mauricio@hotmail.com",
                     "{bcrypt}$2a$10$cCHvTL0ohdJ.Lw/18Z8xLuCLVXdy.DmDtMioA5sxg9Dvb9v21qJ1C", Set.of(new Role(2,"ADMIN"))));

    public User findUserByEmail(String email){
        return users.stream().
                filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }
    public Set<String> allowedCategoriesByEmail(String username){
        logger.info("Returning allowed categories for user: {}", username);
        if(username.equalsIgnoreCase("pablo@hotmail.com")){
            return Set.of("CIENCIA","FICCION","NOVELA");
        }else{
            return Set.of("CIENCIA","NOVELA","SUSPENSO");
        }
    }
    public Set<String> allowedAuthorsByEmail(String username){
        logger.info("Returning allowed authors for user: {}", username);
        if(username.equalsIgnoreCase("pablo@hotmail.com")){
            return Set.of("Mauricio","Augusto","Dardo");
        }else{
            return Set.of("Nelson","Josefina");
        }
    }
}
