package com.cg.service.user;

import com.cg.model.User;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    User getByUsername(String username);

    Optional<UserDTO> findUserDTOById(Long id);


    Optional<User> findByUsername(String username);

    Optional<UserDTO> findUserDTOByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<UserDTO> findUserDTOFullByUsername(String username);
}
