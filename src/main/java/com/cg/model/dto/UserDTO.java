package com.cg.model.dto;


import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO {

    private Long id;


    @NotBlank(message = "The username is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "The email address is invalid")
    @Size(max = 50, message = "The length of username must be between 5 and 50 characters")
    private String username;

    @NotBlank(message = "The password is required")
    @Size(max = 30, message = "Maximum password length 30 characters")
    private String password;

//    @NotBlank(message = "The password is required")
//    @Pattern(regexp = "^[0][1-9][0-9]{8}$|^[+84][1-9][0-9]{10}$",message = "The phone is valid!")
//    private String phone;
//
//    private boolean isActive;

//    private String urlImage;

    @Valid
    private RoleDTO role;

//    @Valid
//    private LocationRegionDTO locationRegion;


    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(Long id, String username,String password ,Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role.toRoleDTO();
    }


    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRole());
    }

//    @Override
//    public boolean supports(Class<?> aClass) {
//        return UserDTO.class.isAssignableFrom(aClass);
//    }
//    @Override
//    public void validate(Object target, Errors errors) {
//        UserDTO userDTO = (UserDTO) target;
//
//        String fullNameCheck = userDTO.getFullName();
//        String emailCheck = userDTO.getUserName();
//        String passwordCheck = userDTO.getPassword();
//        String phoneCheck = userDTO.getPhone();

//        if ((emailCheck.trim()).isEmpty()) {
//            errors.rejectValue("email", "email.isEmpty", "Vui Lòng Nhập Email Người Dùng");
//            return;
//        }
//
//        if ((fullNameCheck.trim().isEmpty())){
//            errors.rejectValue("fullName", "fullName.isEmpty", "Vui Lòng Nhập Tên Người Dùng");
//            return;
//        }
//
//        if ((passwordCheck.trim()).isEmpty()) {
//            errors.rejectValue("password", "password.isEmpty", "Vui Lòng Nhập Mật Khẩu Người Dùng");
//            return;
//        }
//
//        if ((phoneCheck.trim()).isEmpty()) {
//            errors.rejectValue("phone", "phone.isEmpty", "Vui Lòng Nhập Số Điện Thoại Người Dùng");
//            return;
//        }
//
//        if ((fullNameCheck.length() < 3 || fullNameCheck.length() > 255)) {
//            errors.rejectValue("fullName", "fullName.length", "Tên Từ 3 Đến 255 Ký Tự");
//            return;
//        }
//
//        if ((emailCheck.length() > 255)) {
//            errors.rejectValue("email", "fullName.length", "Email Tối Đa 255 Ký Tự");
//            return;
//        }
//
//        if (passwordCheck.length() > 50) {
//            errors.rejectValue("password", "password.length", "Mật Khẩu Tối Đa 50 Ký Tự");
//            return;
//        }
//
//        if (!emailCheck.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$")) {
//            errors.rejectValue("email", "email.matches", "Email Nhập Vào Không Hợp Lệ");
//            return;
//        }
//
////        if (!passwordCheck.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*])(?!.*['\"`]).{6,}")) {
////            errors.rejectValue("password", "password.matches", "Mật Khẩu Nhập Vào Không Hợp Lệ");
////            return;
////        }
//
//        if (!phoneCheck.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")){
//            errors.rejectValue("phone", "phone.matches", "Số Điện Thoại Nhập Vào Không Hợp Lệ");
//            return;
//        }

}
