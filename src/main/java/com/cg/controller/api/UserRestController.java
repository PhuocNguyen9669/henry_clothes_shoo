package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.dto.UserDTO;
import com.cg.service.role.RoleService;
import com.cg.service.user.IUserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AppUtil appUtil;

    @GetMapping("/{user}")
    public ResponseEntity<?> getUserById(@PathVariable String user) {
        Optional<UserDTO> userDTO = userService.findUserDTOFullByUsername(user);
        if(!userDTO.isPresent()){
            throw new DataInputException("không có user");
        }
        return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
    }
}
